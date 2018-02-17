package com.fo0.robot.controller.chain;

import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fo0.robot.chain.ChainCommand;
import com.fo0.robot.chain.EChainResponse;
import com.fo0.robot.model.ActionItem;
import com.fo0.robot.utils.Commander;
import com.fo0.robot.utils.Logger;
import com.fo0.robot.utils.Random;

import lombok.Builder;

@Builder
public class ChainActionItem implements ChainCommand<ActionContext> {

	private ActionItem item;

	@Override
	public EChainResponse command(ActionContext ctx) throws Exception {
		// get latest action
		SimpleEntry<Integer, ActionItem> item = ctx.pop();

		// info
		Logger.info("popped action: " + item.getKey() + ", " + item.getValue());

		Commander.execute(true, true, System.getProperty("user.dir"),
				Stream.of(item.getValue().getValue()).collect(Collectors.toList()));

		if (Random.numeric(1) % 2 == 0)
			return EChainResponse.Break;

		// return all ok
		return EChainResponse.Continue;
	}

}
