package com.fo0.robot.config;

import com.fo0.robot.chain.ChainStateObserver;
import com.fo0.robot.chain.EState;
import com.fo0.robot.controller.Controller;
import com.fo0.robot.utils.Logger;

public class ShutDownConfigObserver implements ChainStateObserver {

	@Override
	public void finished(EState state) {
		if (Controller.getConfig() != null && Controller.getConfig().isTerminate() && hasTerminState(state)) {
			Logger.info("Detected Configured Terminate after Run... (--terminate)");
			System.exit(0);
		}
	}

	/**
	 * @param state
	 * @return
	 */
	private boolean hasTerminState(EState state) {
		if (state == null)
			return false;

		switch (state) {
		case Stopped:
		case Success:
		case Failed:
			// valid terminate state
			return true;

		default:
			break;
		}

		return false;
	}

}
