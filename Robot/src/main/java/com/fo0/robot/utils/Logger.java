package com.fo0.robot.utils;

import com.fo0.robot.controller.Controller;

/**
 * TODO: Add java.utils.logging or log4j as logger
 * 
 * @author max
 *
 */
public class Logger {

	public static void info(String message) {
		System.out.println("[INFO] " + message);
	}

	public static void error(String message) {
		System.err.println("[ERROR] " + message);
	}

	public static void debug(String message) {
		if (Controller.getConfig() != null && Controller.getConfig().debug)
			System.out.println("[DEBUG] " + message);
	}
}
