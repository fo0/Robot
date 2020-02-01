package com.fo0.robot.config;

import java.nio.file.Path;

import org.apache.commons.lang3.StringUtils;

import com.fo0.robot.client.gui.main.MainGUI;
import com.fo0.robot.controller.Controller;
import com.fo0.robot.controller.ControllerChain;
import com.fo0.robot.update.UpdateUtils;
import com.fo0.robot.utils.CONSTANTS;
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
		// direct-config > configFile > auto-detect-config
		if (StringUtils.isNotBlank(Controller.config.config)) {
			// using config via direct load in
			ControllerChain.getChain().getContext().load(Controller.config.config);

		} else if (StringUtils.isNotBlank(Controller.config.configFile)) {
			ControllerChain.getChain().getContext().loadFromFile(Controller.config.configFile);

		} else if (Controller.config.isConfigDetect()) {
			Path detectedConfig = ConfigDetector.searchRobotConfig(System.getProperty("user.dir"));
			if (detectedConfig != null && detectedConfig.toFile().exists()) {
				Logger.info("[ConfigAutoDetect] load config file from path: " + detectedConfig.toAbsolutePath());
				ControllerChain.getChain().getContext().loadFromFile(detectedConfig.toAbsolutePath().toString());
			} else {
				Logger.info("[ConfigAutoDetect] could not find config file in: " + System.getProperty("user.dir"));
			}
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
			ControllerChain.start();
		}

		// detect if cli and path set, automatically execute chain
		if (StringUtils.isNotBlank(Controller.config.config)) {
			Logger.info("read config: " + Controller.config.config);
			ControllerChain.start();
		}

	}

	private static void startWithGUI() {
		MainGUI.bootstrap();
	}

	public static void applyConfigDebugOption() {
		if (Controller.config.isDebug()) {
			CONSTANTS.DEBUG = true;
		}
	}

}
