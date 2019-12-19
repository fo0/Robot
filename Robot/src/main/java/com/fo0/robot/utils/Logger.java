package com.fo0.robot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fo0.robot.controller.Controller;

/**
 * TODO: Add java.utils.logging or log4j as logger
 * 
 * @author max
 *
 */
public class Logger {

	private static final String DATE_PATTERN = "yyyy-mm-dd HH:mm:ss.SSS";
	public static FileLog log = null;

	public static void info(String message) {
		print("INFO", message);
		addToLog(message);
	}

	public static void error(String message) {
		print("ERROR", message);
		addToLog(message);
	}

	public static void debug(String message) {
		if (CONSTANTS.DEBUG || (Controller.getConfig() != null && Controller.getConfig().isDebug())) {
			print("DEBUG", message);
		}

		addToLog(message);
	}

	private static void print(String prefix, String message) {
		//@formatter:off
		if (prefix.equals("ERROR")) {
			System.err.println(String.format("%s [%s] %s", new SimpleDateFormat(DATE_PATTERN).format(new Date()), prefix, message));
		} else {
			System.out.println(String.format("%s [%s] %s", new SimpleDateFormat(DATE_PATTERN).format(new Date()), prefix, message));
		}
		//@formatter:on
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
