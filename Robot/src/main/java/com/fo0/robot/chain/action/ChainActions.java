package com.fo0.robot.chain.action;

import java.util.Map.Entry;

import com.fo0.robot.chain.Chain;
import com.fo0.robot.chain.ChainItem;
import com.fo0.robot.controller.Controller;
import com.fo0.robot.model.ActionItem;

public class ChainActions extends Chain<ActionContext> {

	public ChainActions() {
		super("Chain Actions", ActionContext.builder().build());
	}

	public Entry<Integer, ActionItem> addActionItem(ActionItem item) {
		return getContext().put(item);
	}

	public Entry<Integer, ActionItem> getActionItem(ActionItem item) {
		return getContext().getMap().entrySet().stream().filter(e -> e.getValue().equals(item)).findFirst()
				.orElse(null);
	}

	public void removeActionItem(ActionItem item) {
		getContext().remove(item);
	}

	public void createChains() {
		getChains().clear();
		getContext().sortList();
		getContext().getMap().entrySet().stream().forEach(e -> {
			addToChain(e.getValue().getType().name(), e.getValue().getDescription(), ChainItem.<ActionContext>builder()
					.preCommand(ChainPreAction.builder().build()).command(ChainActionItem.builder().build()).build());
		});
	}

	@Override
	public void start() {
		try {
			setFailOnError(!Controller.getConfig().ignoreErrors);
		} catch (Exception e) {
		}
		createChains();
		super.start();
		getContext().reset();
	}

}
