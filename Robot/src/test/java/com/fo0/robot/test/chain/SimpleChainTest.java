package com.fo0.robot.test.chain;

import org.junit.Test;

import com.fo0.robot.backend.chain.ActionContext;
import com.fo0.robot.backend.chain.ChainActions;
import com.fo0.robot.model.ActionItem;
import com.google.gson.GsonBuilder;

public class SimpleChainTest {

	@Test
	public void defaults() {
		ActionContext ctx = ActionContext.builder().build();
		ctx.push(ActionItem.builder().value("echo lol").build());

		ChainActions actions = new ChainActions(ctx);
		actions.start();

		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(actions.getChainInfo()));
	}

}
