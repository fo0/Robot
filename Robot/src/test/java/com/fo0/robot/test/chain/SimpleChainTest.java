package com.fo0.robot.test.chain;

import org.junit.Assert;
import org.junit.Test;

import com.fo0.robot.chain.EState;
import com.fo0.robot.chain.action.ChainActions;
import com.fo0.robot.model.ActionItem;

public class SimpleChainTest {

	@Test
	public void successTest() {
		ChainActions actions = new ChainActions();
		actions.addActionItem(ActionItem.builder().value("echo lol").description("description example").build());
		actions.start();
		Assert.assertEquals(EState.Success, actions.getState());
	}

	@Test
	public void stopTest() {
		ChainActions actions = new ChainActions();
		actions.start();
		Assert.assertEquals(EState.Stopped, actions.getState());
	}

	@Test
	public void failTest() {
		ChainActions actions = new ChainActions();
		actions.addActionItem(ActionItem.builder().value("lol").build());
		actions.start();
		Assert.assertEquals(EState.Failed, actions.getState());
	}
}
