package com.fo0.robot.controller.chain;

import java.util.AbstractMap.SimpleEntry;

import com.fo0.robot.chain.Chain;
import com.fo0.robot.chain.ChainItem;
import com.fo0.robot.model.ActionItem;

public class ChainActions extends Chain<ActionContext> {

	public ChainActions() {
		super("Chain Actions", ActionContext.builder().build());

	}

	public SimpleEntry<Integer, ActionItem> addActionItem(ActionItem item) {
		return getContext().push(item);
	}

	public void removeActionItem(ActionItem item) {
		getContext().remove(item);
	}

	public void createChains() {
		getContext().getMap().entrySet().stream().forEach(e -> {
			addToChain(e.getValue().getType().name(),
					ChainItem.<ActionContext>builder().command(ChainActionItem.builder().build()).build());
		});
	}

	@Override
	public void start() {
		createChains();
		super.start();
	}

}
