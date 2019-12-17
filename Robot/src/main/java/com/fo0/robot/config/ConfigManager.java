package com.fo0.robot.config;

import org.apache.commons.lang3.StringUtils;

import com.fo0.robot.client.gui.main.MainGUI;
import com.fo0.robot.controller.Controller;
import com.fo0.robot.controller.ControllerChain;
import com.fo0.robot.update.UpdateUtils;
import com.fo0.robot.utils.CONSTANTS_PATTERN_CONF;
import com.fo0.robot.utils.Logger;

public class ConfigManager {

	public static void applyConfigLoggingOption() {
		if (Controller.config.logFile) {
			Logger.activateFileLogging();
		}
	}

	public static void applyVariablePattern() {
		if (StringUtils.isNotBlank(Controller.config.variable)
				&& !StringUtils.equals(CONSTANTS_PATTERN_CONF.VARIABLE_SIGN, Controller.config.variable)) {
			Logger.info("Set Variable Pattern: '" + Controller.config.variable + "'");
			CONSTANTS_PATTERN_CONF.VARIABLE_SIGN = Controller.config.variable;
		}
	}

	public static void applyConfig() {
		if (Controller.config.configFile != null && !Controller.config.configFile.isEmpty()) {
			ControllerChain.getChain().getContext().loadFromFile(Controller.config.configFile);
		}

		// "[{\"id\":\"7HB23fC8dI\",\"type\":\"COPY\",\"description\":\"COPY
		// file\",\"value\":\"$SRC(robot.jar) $DST(robot2.jar)\",\"active\":true}]"

		if (StringUtils.isNotBlank(Controller.config.config)) {
			// using config via direct load in
			ControllerChain.getChain().getContext().load(Controller.config.config);
		}

		if (!Controller.config.nogui) {
			startWithGUI();
		} else {
			startCLI();
		}
	}

	/**
	 * 
	 */
	private static void startCLI() {
		// update function, take care of loop
		Logger.info("detected cli mode - automatically started chain");

		if (Controller.config.update) {
			Logger.info("detected update mode, starting updater");
			if (UpdateUtils.isAvailable()) {
				Logger.info("new update available, performing update now");
				UpdateUtils.doUpdate();
			}
		}

		// exiting if nogui and no cfg
		if (StringUtils.isAllBlank(Controller.config.configFile, Controller.config.config)) {
			Logger.info("detected cli mode, but could not find a config - exiting now");
			System.exit(0);
		}

		// detect if cli and path set, automatically execute chain
		if (StringUtils.isNotBlank(Controller.config.configFile)) {
			Logger.info("read config-file: " + Controller.config.configFile);
			ControllerChain.getChain().start();
		}

		// detect if cli and path set, automatically execute chain
		if (StringUtils.isNotBlank(Controller.config.config)) {
			Logger.info("read config: " + Controller.config.config);
			ControllerChain.getChain().start();
		}

	}

	private static void startWithGUI() {
		MainGUI.bootstrap();
	}

}
