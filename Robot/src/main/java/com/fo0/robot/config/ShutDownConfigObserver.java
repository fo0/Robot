package com.fo0.robot.config;

import com.fo0.robot.chain.ChainStateObserver;
import com.fo0.robot.chain.EState;
import com.fo0.robot.controller.Controller;
import com.fo0.robot.utils.Logger;

public class ShutDownConfigObserver implements ChainStateObserver {

	@Override
	public void finished(EState state) {
		if (Controller.getConfig() != null && Controller.getConfig().isTerminate()) {
			Logger.info("Detected Configured Terminate after Run... (--terminate)");
			System.exit(0);
		}
	}

}
