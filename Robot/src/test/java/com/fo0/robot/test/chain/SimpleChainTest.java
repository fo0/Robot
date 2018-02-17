package com.fo0.robot.test.chain;

import org.junit.Test;

import com.fo0.robot.controller.chain.ChainActions;
import com.fo0.robot.model.ActionItem;
import com.google.gson.GsonBuilder;

public class SimpleChainTest {

	@Test
	public void defaults() {
		ChainActions actions = new ChainActions();
		actions.addActionItem(ActionItem.builder().value("echo lol").build());
		actions.start();
		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(actions.getChainInfo()));
	}

}
