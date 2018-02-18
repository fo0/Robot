package com.fo0.robot.test.chain;

import org.junit.Assert;
import org.junit.Test;

import com.fo0.robot.controller.chain.ChainActions;
import com.fo0.robot.model.ActionItem;

public class SimpleChainTest {

	@Test
	public void defaults() {
		ChainActions actions = new ChainActions();
		actions.addActionItem(ActionItem.builder().value("echo lol").description("description example").build());
		actions.start();
		Assert.assertNotNull(actions.getChainInfo());
	}

}
