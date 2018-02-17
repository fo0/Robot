package com.fo0.robot.backend.chain;

import com.fo0.robot.chain.Chain;
import com.fo0.robot.chain.ChainItem;

public class ChainActions extends Chain<ActionContext> {

	public ChainActions(ActionContext ctx) {
		super("Chain Actions", ctx);

		ctx.getMap().entrySet().stream().forEach(e -> {
			addToChain(ChainItem.<ActionContext>builder().command(ChainActionItem.builder().build()).build());
		});
	}

}
