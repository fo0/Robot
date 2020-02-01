package com.fo0.robot.controller;

import org.apache.commons.lang3.StringUtils;

import com.fo0.robot.client.gui.main.MainGUI;
import com.fo0.robot.config.Config;
import com.fo0.robot.config.ConfigManager;
import com.fo0.robot.config.ConfigParser;
import com.fo0.robot.utils.CONSTANTS;
import com.fo0.robot.utils.Logger;

import lombok.Getter;

public class Controller {

	public static String[] arg;
	private static MainGUI gui = null;

	@Getter
	public static Config config = null;

	public static void bootstrap(String[] args) {
		arg = args;

		// parse args for config
		config = ConfigParser.parseConfig(args);
		ConfigManager.applyConfigDebugOption();
		
		ConfigManager.applyConfigLoggingOption();

		// startup message
		startUpMessage();

		// change default variable
		ConfigManager.applyVariablePattern();

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

	private static void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			Logger.info("Shutting down Robot...");
			if (Logger.log != null) {
				Logger.log.flush();
			}
		}));
	}

	private static void modules() {
		addShutdownHook();
		ControllerChain.bootstrap();
	}

}
