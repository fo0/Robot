package com.fo0.robot.chain.action;

import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;

import com.fo0.robot.enums.EActionType;
import com.fo0.robot.listener.DispatchListener;
import com.fo0.robot.listener.ValueChangeListener;
import com.fo0.robot.model.ActionItem;
import com.fo0.robot.utils.Logger;
import com.fo0.robot.utils.Parser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionContext {

	@Builder.Default
	private transient int start = 0;

	@Builder.Default
	private transient int end = 0;

	@Builder.Default
	private transient int current = 0;

	@Builder.Default
	private Map<Integer, ActionItem> map = new TreeMap<Integer, ActionItem>();

	/*
	 * pre initialize to dispatch/route the input to output from cli
	 */
	@Builder.Default
	private transient DispatchListener inputListener = e -> {
		return e;
	};

	private transient ValueChangeListener outputListener;

	public ActionItem put(int id, ActionItem item) {
		Logger.info("adding action item: [" + (id + 1) + "/" + map.size() + "] " + item.getType() + ", "
				+ item.getDescription());
		return map.put(id, item);
	}

	public Entry<Integer, ActionItem> put(ActionItem item) {
		Entry<Integer, ActionItem> tmpItem = getActionItem(item);

		if (tmpItem != null) {
			Logger.info("skipping push action item: [" + (tmpItem.getKey() + 1) + "/" + map.size() + "] "
					+ tmpItem.getValue().getType() + ", " + tmpItem.getValue().getDescription());
			return tmpItem;
		}

		int id = determineNextId();
		map.put(id, item);
		Logger.info("pushing action item: [" + (id + 1) + "/" + map.size() + "] " + item.getType() + ", "
				+ item.getDescription());
		return new SimpleEntry<Integer, ActionItem>(id, item);
	}

	public Entry<Integer, ActionItem> getActionItem(ActionItem item) {
		return map.entrySet().stream().filter(e -> e.getValue().equals(item)).findFirst().orElse(null);
	}

	public void remove(ActionItem item) {
		Entry<Integer, ActionItem> foundItem = getActionItem(item);

		if (foundItem == null) {
			return;
		}

		map.remove(foundItem.getKey());
		end = map.size();
	}

	public Entry<Integer, ActionItem> pop() {
		ActionItem latest = map.get(current);
		SimpleEntry<Integer, ActionItem> entry = new SimpleEntry<Integer, ActionItem>(current, latest);
		current++;
		end = map.size();
		return entry;
	}

	public Entry<Integer, ActionItem> peek() {
		ActionItem latest = map.get(current);
		return new SimpleEntry<Integer, ActionItem>(current, latest);
	}

	public void reset() {
		start = 0;
		end = map.size();
		current = 0;
	}

	public void clear() {
		reset();
		try {
			map.clear();
		} catch (Exception e) {
			map = new TreeMap<Integer, ActionItem>();
		}
	}

	public int getCurrent() {
		return current;
	}

	private int determineNextId() {
		if (map.size() == 0) {
			return 0;
		} else {
			int id = map.entrySet().stream().map(e -> e.getKey()).max(Integer::compareTo).orElse(0);
			return id = id + 1;
		}
	}

	public void addOutputListener(ValueChangeListener listener) {
		this.outputListener = listener;
	}

	/**
	 * without line feeed
	 * 
	 * @param type
	 * @param log
	 */
	public void addToLogPlain(EActionType type, String log) {
		String msg = "[Action|" + type + "] " + log;
		Logger.debug(msg);
		if (outputListener != null) {
			outputListener.event(inputListener.event(log));
		}
	}

	public void sortList() {
		// get sorted order of map
		Map<Integer, ActionItem> sortedMap = new TreeMap<Integer, ActionItem>();
		int counter = 0;
		for (Iterator<Entry<Integer, ActionItem>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry<Integer, ActionItem> item = iterator.next();
			sortedMap.put(counter, item.getValue());
			counter++;
		}

		this.map = sortedMap;
	}

	/**
	 * with line feeed
	 * 
	 * @param type
	 * @param log
	 */
	public void addToLog(EActionType type, String log) {
		String msg = "[Action|" + type + "] " + log;
		Logger.debug(msg);
		if (outputListener != null) {
			outputListener.event(inputListener.event(msg + "\n"));
		}
	}

	public void save(String path) {
		Logger.info("saving config-file: " + path);
		if (this.getMap() != null && this.getMap().values().isEmpty()) {
			Logger.debug("could not find any items for save: " + path);
			return;
		}

		Parser.write(getMap().values(), new File(path));
	}

	public void load(String path) {
		List<ActionItem> actionItems = Parser.parseList(new File(path), ActionItem.class);

		if (actionItems == null || actionItems.isEmpty()) {
			Logger.error("failed to load context from file: " + path);
			return;
		}

		//@formatter:off
		ActionContext ctx = ActionContext.builder()
				.map(IntStream.range(0, actionItems.size())
						.mapToObj(e -> Pair.of(e, actionItems.get(e)))
						.collect(Collectors.toMap(Entry::getKey, Entry::getValue)))
				.build();
		//@formatter:on

		if (ctx == null || ctx.getMap() == null || ctx.getMap().isEmpty()) {
			Logger.error("failed to loaded items to context: " + path);
			return;
		}

		clear();

		try {
			setCurrent(ctx.getCurrent());
			setEnd(ctx.getEnd());
			setStart(ctx.getStart());
			getMap().clear();
			getMap().putAll(ctx.getMap());

			Logger.info("loading config-file: " + path + ", Success: " + (ctx != null ? "true" : "false"));
		} catch (Exception e) {
			clear();
			Logger.error("loading config-file failed, clear context");
		}
	}
}
