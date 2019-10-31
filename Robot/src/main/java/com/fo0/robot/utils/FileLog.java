package com.fo0.robot.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLog {

	private static final String FILENAME = "robot.log";
	private static final String DATE_PATTERN = "yyyy-mm-dd HH:mm:ss.SSS";
	private BufferedWriter buffer;
	private long flushing = 10;
	private long flushCounter = 0;

	public FileLog() {
		this(FILENAME);
	}

	public FileLog(String filename) {
		try {
			buffer = new BufferedWriter(new FileWriter(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void add(String message) {
		try {
			buffer.append(new SimpleDateFormat(DATE_PATTERN).format(new Date()) + "> " + message + "\n");
			flushToDisc();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private void flushToDisc() {
		flushCounter++;
		if (flushCounter % flushing == 0) {
			flushCounter = 0;
			flush();
		}
	}

	public void flush() {
		try {
			buffer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
