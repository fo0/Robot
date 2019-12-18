package com.fo0.robot.utils;

import java.util.Date;

import com.fo0.robot.controller.Controller;

/**
 * TODO: Add java.utils.logging or log4j as logger
 * 
 * @author max
 *
 */
public class Logger {

	public static FileLog log = null;

	public static void info(String message) {
		String msg = "[INFO] " + message;
		System.out.println(msg);
		addToLog(msg);
	}

	public static void error(String message) {
		String msg = "[ERROR] " + message;
		System.err.println(msg);
		addToLog(msg);
	}

	public static void debug(String message) {
		String msg = "[DEBUG] " + message;
		if (CONSTANTS.DEBUG || (Controller.getConfig() != null && Controller.getConfig().isDebug()))
			System.out.println(msg);

		addToLog(msg);
	}

	private static void addToLog(String message) {
		if (log != null) {
			log.add(message);
		}
	}

	public static void activateFileLogging() {
		log = new FileLog();
		info("Activated File-Logging to: 'robot.log'");
	}
}
