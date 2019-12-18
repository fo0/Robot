package com.fo0.robot.test.chain;

import static org.junit.Assert.assertEquals;

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
		assertEquals(EState.Success, actions.getState());
	}

	@Test
	public void stopTest() {
		ChainActions actions = new ChainActions();
		actions.start();
		assertEquals(EState.Stopped, actions.getState());
	}

	@Test
	public void failTest() {
		ChainActions actions = new ChainActions();
		actions.addActionItem(ActionItem.builder().value("no-command").build());
		actions.start();
		assertEquals(EState.Failed, actions.getState());
	}
}
