package com.fo0.robot.controller;

import org.apache.commons.lang3.StringUtils;

import com.fo0.robot.client.gui.main.MainGUI;
import com.fo0.robot.config.Config;
import com.fo0.robot.config.ConfigManager;
import com.fo0.robot.config.ConfigParser;
import com.fo0.robot.utils.CONSTANTS;
import com.fo0.robot.utils.Logger;

public class Controller {

	public static String[] arg;
	public static Config config = null;
	private static MainGUI gui = null;

	public static void bootstrap(String[] args) {
		arg = args;

		// parse args for config
		config = ConfigParser.parseConfig(args);

		// startup message
		startUpMessage();

		// apply the config options
		modules();

		ConfigManager.applyConfig();
	}

	private static void startUpMessage() {
		// startup messages here
		Logger.info("##############################################");
		Logger.info("	Starting Robot");
		Logger.info("	Version: " + CONSTANTS.VERSION);
		Logger.info("	Author: fo0");
		Logger.info("	GitHub: https://github.com/fo0/Robot");
		Logger.info("	Options: " + StringUtils.join(arg, ", "));
		Logger.info("##############################################");
	}

	private static void modules() {
		ControllerChain.bootstrap();
	}

	public static Config getConfig() {
		return config;
	}

}
