package com.fo0.robot.chain;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.fo0.robot.utils.Logger;
import com.fo0.robot.utils.Random;

public class Chain<T> {

	private String id = Random.alphanumeric(10);
	private EState state = EState.Stopped;
	private T context;

	private boolean failOnError = true;

	private Map<ChainID, ChainItem<T>> chains = new TreeMap<ChainID, ChainItem<T>>();

	private ChainCmdListener<T> cmdListener = null;

	public Chain() {
	}

	public void addCmdListener(ChainCmdListener<T> cmdListener) {
		this.cmdListener = cmdListener;
	}

	public Chain(String chainId, T context) {
		this.id = chainId;
		this.context = context;
	}

	public void setContext(T context) {
		this.context = context;
	}

	public Map<ChainID, ChainItem<T>> getChains() {
		return chains;
	}

	public T getContext() {
		return this.context;
	}

	private long determineNextId() {
		if (chains.size() == 0) {
			return 0;
		} else {
			long id = chains.entrySet().stream().map(e -> e.getKey().getId()).max(Long::compareTo).orElse(0L);
			return id = id + 1;
		}
	}

	public boolean addToChain(ChainItem<T> item) {
		return addToChain("", item);
	}

	public boolean addToChain(String name, ChainItem<T> item) {
		chains.put(ChainID.builder().name(name).id(determineNextId()).build(), item);
		return true;
	}

	public boolean addToChain(String name, String description, ChainItem<T> item) {
		chains.put(ChainID.builder().name(name).description(description).id(determineNextId()).build(), item);
		return true;
	}

	public boolean addToChain(ChainID id, ChainItem<T> item) {
		if (chains.containsKey(id)) {
			return false;
		} else {
			chains.put(id, item);
			return true;
		}
	}

	public boolean addToChain(ChainID id, ChainCommand<T> cmd) {
		return addToChain(id, ChainItem.<T>builder().command(cmd).build());
	}

	public boolean addToChain(ChainID id, ChainCommand<T> cmd, ChainError<T> error) {
		return addToChain(id, ChainItem.<T>builder().command(cmd).error(error).build());
	}

	public boolean removeFromChain(ChainID id) {
		if (chains.containsKey(id)) {
			chains.remove(id);
			return true;
		} else {
			return false;
		}
	}

	public void start() {
		if (chains == null || chains.isEmpty()) {
			Logger.info("starting skipping chain is empty");
			return;
		}

		Logger.info("starting chain: " + id);
		state = EState.Processing;

		for (Entry<ChainID, ChainItem<T>> e : chains.entrySet()) {
			Logger.debug("Execute Chain: " + e.getKey().info());
			e.getValue().getData().getState().setState(EState.Processing);

			if (e.getValue().getPreCommand() != null) {
				// before,pre
				e.getValue().getData().getState().setPre(EChainResponse.Set);
				try {
					e.getValue().getData().getState().setPre(e.getValue().getPreCommand().preCommand(context));

					if (e.getValue().getData().getState().getPre().code() > 0) {
						e.getValue().getData().getState().setState(EState.Success);
					} else {
						if (e.getValue().getData().getState().getPre() == EChainResponse.Skip) {
							e.getValue().getData().getState().setPre(EChainResponse.Skip);
							e.getValue().getData().getState().setCmd(EChainResponse.Skip);
							e.getValue().getData().getState().setPost(EChainResponse.Skip);
							if (cmdListener != null)
								cmdListener.event(getContext(), e);
							continue;
						} else {
							e.getValue().getData().getState().setState(EState.Failed);
						}
					}

				} catch (Exception e2) {
					Logger.debug("failed to exec pre chain of: " + id + ", " + e2);
					e.getValue().getData().getState().setState(EState.Failed);
					e.getValue().getData().getState().setPre(EChainResponse.Failed);
					e.getValue().getData().getException().setPre(e2);
				}

				Logger.debug("finished Chain: " + id + ", Item-ID: " + e.getKey().getId() + " [" + e.getKey().getName()
						+ "]" + " Pre: " + e.getValue().getData().getState().getPre());
			}

			if (!failOnError | (e.getValue().getPostCommand() != null
					&& e.getValue().getData().getState().getState().code() > 0)) {
				// after,post
				e.getValue().getData().getState().setPost(EChainResponse.Set);
				try {
					e.getValue().getData().getState().setPost(e.getValue().getPostCommand().postCommand(context));

					if (e.getValue().getData().getState().getPost().code() > 0) {
						e.getValue().getData().getState().setState(EState.Success);
					} else {
						if (e.getValue().getData().getState().getPost() == EChainResponse.Skip) {
							e.getValue().getData().getState().setPre(EChainResponse.Skip);
							e.getValue().getData().getState().setCmd(EChainResponse.Skip);
							e.getValue().getData().getState().setPost(EChainResponse.Skip);
							if (cmdListener != null)
								cmdListener.event(getContext(), e);
							continue;
						} else {
							e.getValue().getData().getState().setState(EState.Failed);
						}

					}

				} catch (Exception e2) {
					Logger.debug("failed to exec post chain of: " + id + ", " + e2);
					e.getValue().getData().getState().setState(EState.Failed);
					e.getValue().getData().getState().setPost(EChainResponse.Failed);
					state = EState.Failed;
					e.getValue().getData().getException().setPost(e2);
				}

				Logger.debug("finished Chain: " + id + ", Item-ID: " + e.getKey().getId() + " [" + e.getKey().getName()
						+ "]" + " Post: " + e.getValue().getData().getState().getPost());
			}

			if (!failOnError
					| (e.getValue().getCommand() != null && e.getValue().getData().getState().getState().code() > 0)) {
				e.getValue().getData().getState().setCmd(EChainResponse.Set);
				try {
					e.getValue().getData().getState().setCmd(e.getValue().getCommand().command(context));

					if (e.getValue().getData().getState().getCmd().code() > 0) {
						e.getValue().getData().getState().setState(EState.Success);
					} else {
						if (e.getValue().getData().getState().getCmd() == EChainResponse.Skip) {
							e.getValue().getData().getState().setPre(EChainResponse.Skip);
							e.getValue().getData().getState().setCmd(EChainResponse.Skip);
							e.getValue().getData().getState().setPost(EChainResponse.Skip);
							if (cmdListener != null)
								cmdListener.event(getContext(), e);
							continue;
						} else {
							e.getValue().getData().getState().setState(EState.Failed);
						}

					}

				} catch (Exception e2) {
					Logger.debug("failed to exec main exec chain of: " + id + ", " + e2);
					e.getValue().getData().getState().setState(EState.Failed);
					e.getValue().getData().getState().setCmd(EChainResponse.Failed);
					state = EState.Failed;
					e.getValue().getData().getException().setCmd(e2);
				} finally {
					if (cmdListener != null)
						cmdListener.event(getContext(), e);
				}

				Logger.debug("finished Chain: " + id + ", Item-ID: " + e.getKey().getId() + " [" + e.getKey().getName()
						+ "]" + " Post: " + e.getValue().getData().getState().getCmd());
			}

			if (failOnError)
				if (e.getValue().getData().getState().getState() == EState.Failed) {
					Logger.error("failed to execute chain chunk, stopping chain: " + id + ", state: "
							+ e.getValue().getData().getState() + ", Exception: "
							+ e.getValue().getData().getException());
					e.getValue().getData().getState().setState(EState.Failed);
					state = EState.Failed;
					setAllStatesAfterChunkTo(e.getKey(), EState.Stopped);
					try {
						e.getValue().getError().error(context, e.getValue().getData().getException());
					} catch (Exception e3) {
						Logger.debug("failed to exec main exec chain of: " + id + ", " + e3);
					}

					break;
				}

			if (failOnError)
				if (e.getValue().getData().getState().getPre().code() < 0
						| e.getValue().getData().getState().getPost().code() < 0
						| e.getValue().getData().getState().getCmd().code() < 0) {
					Logger.info("stopped chain: " + e.getValue().getData().getState());
					e.getValue().getData().getState().setState(EState.Stopped);
					setAllStatesAfterChunkTo(e.getKey(), EState.Stopped);
					break;
				}

			e.getValue().getData().getState().setState(EState.Success);
		}

		if (state != EState.Failed)
			state = EState.Success;
	}

	public String getID() {
		return id;
	}

	public EState getState() {
		return state;
	}

	public void setFailOnError(boolean failOnError) {
		this.failOnError = failOnError;
	}

	private void setAllStatesAfterChunkTo(ChainID chunk, EState state) {
		chains.entrySet().stream().filter(e -> e.getKey().getId() > chunk.getId())
				.forEach(e -> e.getValue().getData().getState().setState(state));
	}

	public Map<ChainID, ChainData> getChainInfo() {
		return chains.entrySet().stream()
				.map(e -> new AbstractMap.SimpleImmutableEntry<ChainID, ChainData>(e.getKey(), e.getValue().getData()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

}
