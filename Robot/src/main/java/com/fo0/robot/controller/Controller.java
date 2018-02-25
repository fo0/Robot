package com.fo0.robot.controller;

import org.apache.commons.lang3.StringUtils;

import com.fo0.robot.config.Config;
import com.fo0.robot.config.ConfigParser;
import com.fo0.robot.gui.main.MainGUI;
import com.fo0.robot.utils.CONSTANTS;
import com.fo0.robot.utils.Logger;
import com.fo0.robot.utils.UpdateUtils;

public class Controller {

	public static String[] arg;
	private static Config config = null;
	private static MainGUI gui = null;

	public static void bootstrap(String[] args) {
		arg = args;

		// parse args for config
		config = ConfigParser.parseConfig(args);

		// startup message
		startUpMessage();

		// apply the config options
		modules();

		applyConfig();
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

	private static void applyConfig() {
		if (config.configFile != null && !config.configFile.isEmpty()) {
			ControllerChain.getChain().getContext().load(config.configFile);
		}

		if (!config.nogui) {
			MainGUI.bootstrap();
		} else {
			// update function, take care of loop
			if (config.update) {
				Logger.info("detected update mode, starting updater");
				if (UpdateUtils.isAvailable()) {
					Logger.info("new update available, performing update now");
					UpdateUtils.doUpdate();
				}
			}
		}

		// detect if cli and path set, automatically execute chain
		if (config.nogui && config.configFile != null && !config.configFile.isEmpty()) {
			Logger.info("detected cli mode - automatically started chain");
			ControllerChain.getChain().start();
		}

		// exiting if nogui and no cfg
		if (config.nogui && (config.configFile == null || config.configFile.isEmpty())) {
			Logger.info("detected cli mode, but no config - exiting now");
			System.exit(0);
		}

	}

	public static Config getConfig() {
		return config;
	}

}
