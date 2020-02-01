package com.fo0.robot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

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
		addToLog("INFO", message);
	}

	public static void error(String message) {
		print("ERROR", message);
		addToLog("ERROR", message);
	}

	public static void debug(String message) {
		if (CONSTANTS.DEBUG) {
			print("DEBUG", message);
		}

		addToLog("DEBUG", message);
	}

	private static void print(String prefix, String message) {
		//@formatter:off
		String msg = "";
		if(CONSTANTS.DEBUG) {
			msg = String.format("%s %s [%s] - %s", new SimpleDateFormat(DATE_PATTERN).format(new Date()), prefix, StackTraceUtils.methodCaller(2), message);
		} else {
			msg = String.format("%s [%s] %s", new SimpleDateFormat(DATE_PATTERN).format(new Date()), prefix, message);
		}
		
		if (prefix.equals("ERROR")) {
			System.err.println(msg);
		} else {
			System.out.println(msg);
		}
		//@formatter:on
	}

	private static void addToLog(String level, String message) {
		if (log != null) {
			log.add(level, message);
		}
	}

	public static void activateFileLogging() {
		log = new FileLog();
		info("Activated File-Logging to: 'robot.log'");
	}
}
