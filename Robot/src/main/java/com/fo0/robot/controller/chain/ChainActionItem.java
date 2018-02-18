package com.fo0.robot.controller.chain;

import java.util.AbstractMap.SimpleEntry;

import com.fo0.robot.chain.ChainCommand;
import com.fo0.robot.chain.EChainResponse;
import com.fo0.robot.model.ActionItem;
import com.fo0.robot.utils.Commander;
import com.fo0.robot.utils.Logger;

import lombok.Builder;

@Builder
public class ChainActionItem implements ChainCommand<ActionContext> {

	@Override
	public EChainResponse command(ActionContext ctx) throws Exception {
		// get latest action
		SimpleEntry<Integer, ActionItem> item = ctx.pop();

		// info
		Logger.info("popped action: " + item.getKey() + ", " + item.getValue());

		Commander.execute(true, System.getProperty("user.dir"), item.getValue().getValue());

		// return all ok
		return EChainResponse.Continue;
	}

}
