package com.fo0.robot.controller;

import java.io.File;

import com.fo0.robot.config.Config;
import com.fo0.robot.config.ConfigParser;
import com.fo0.robot.controller.chain.ActionContext;
import com.fo0.robot.gui.main.MainGUI;
import com.fo0.robot.utils.CONSTANTS;
import com.fo0.robot.utils.Logger;
import com.fo0.robot.utils.Parser;

public class Controller {

	private static Config config = null;
	private static MainGUI gui = null;

	public static void bootstrap(String[] args) {
		// parse args for config
		config = ConfigParser.parseConfig(args);

		// apply the config options
		modules();

		applyConfig();
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
		if (config.configFile != null && !config.configFile.isEmpty()) {
			Logger.info("loading configfile: " + config.configFile);
			ControllerChain.getChain().setContext(Parser.parse(new File(config.configFile), ActionContext.class));
		}
		if (config.gui == true) {
			MainGUI.bootstrap();
		}

	}

}
