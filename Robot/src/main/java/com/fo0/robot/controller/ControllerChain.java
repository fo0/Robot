package com.fo0.robot.controller;

import com.fo0.robot.chain.action.ChainActions;
import com.fo0.robot.utils.Logger;

public class ControllerChain {

	private static ChainActions chain = new ChainActions();

	public static void bootstrap() {
		Logger.info("started controller chain");
	}

	public static ChainActions getChain() {
		return chain;
	}
}
