package com.fo0.robot.controller;

import com.fo0.robot.chain.EState;
import com.fo0.robot.chain.action.ChainActions;
import com.fo0.robot.utils.Logger;

public class ControllerChain {

	private static ChainActions chain = new ChainActions();
	private static Thread thread = null;

	public static void bootstrap() {
		Logger.info("started controller: controllerchain");
	}

	public static ChainActions getChain() {
		return chain;
	}

	public static void start() {
		thread = new Thread(() -> {
			chain.start();
		});

		thread.start();
	}

	public static void stop() {
		thread.stop();
		chain.notifyObservers(EState.Stopped);
	}
}
