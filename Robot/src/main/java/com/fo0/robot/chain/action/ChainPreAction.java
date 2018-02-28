package com.fo0.robot.chain.action;

import com.fo0.robot.chain.ChainPreCommand;
import com.fo0.robot.chain.EChainResponse;
import com.fo0.robot.model.ActionItem;

import lombok.Builder;

@Builder
public class ChainPreAction implements ChainPreCommand<ActionContext> {

	@Override
	public EChainResponse preCommand(ActionContext ctx) throws Exception {
		ActionItem item = ctx.peek().getValue();
		if (!item.isActive()) {
			ctx.addToLog(item.getType(), "is inactive");
			// to remove the item
			ctx.pop();
			return EChainResponse.Skip;
		}
		return EChainResponse.Continue;
	}

}
