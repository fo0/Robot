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

		// execute commands
		Commander commander = new Commander();
		commander.execute(true, System.getProperty("user.dir"), item.getValue().getValue());

		if (commander == null || commander.isError()) {
			Logger.error("found errors in commander: " + item.getKey());
			return EChainResponse.Failed;
		}

		// return all ok
		return EChainResponse.Continue;
	}

}
