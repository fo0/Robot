package com.fo0.robot.controller;

import com.fo0.robot.config.Config;
import com.fo0.robot.config.ConfigParser;
import com.fo0.robot.gui.main.MainGUI;
import com.fo0.robot.utils.CONSTANTS;
import com.fo0.robot.utils.Logger;

public class Controller {

	private static Config config = null;
	private static MainGUI gui = null;

	public static void bootstrap(String[] args) {
		// parse args for config
		config = ConfigParser.parseConfig(args);

		// apply the config options
		applyConfig();

		modules();
	}

	private void startUpMessage() {
		// startup messages here
		Logger.info("Starting Robot");
		Logger.info("Version: " + CONSTANTS.VERSION);
	}

	private static void modules() {
		ControllerChain.bootstrap();
	}

	private static void applyConfig() {
		if (config.gui == true) {
			MainGUI.bootstrap();
		}
	}

}
