package com.fo0.robot.utils;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

public class Utils {

	public static void sleep(TimeUnit unit, long sleep) {
		try {
			unit.sleep(sleep);
		} catch (Exception e) {
		}
	}

	public static void sleep(long sleepMs) {
		try {
			TimeUnit.MILLISECONDS.sleep(sleepMs);
		} catch (Exception e) {
		}
	}

	public static boolean writeBytesToFile(byte[] bytes, File file) {
		try {
			FileUtils.writeByteArrayToFile(file, bytes);
		} catch (Exception e) {
			Logger.info("failed to override bytes from file" + e);
			return false;
		}
		return true;
	}

	public static String humanReadableBandwith(long msecs, long bytes) {
		long secs = msecs / 1000;
		long bits = bytes * 8;
		float speed = bits / secs;

		long Kbit = 1024;
		long Mbit = Kbit * 1024;
		long Gbit = Mbit * 1024;

		if (speed < Kbit)
			return String.valueOf(speed) + " bit/s";
		if (speed > Kbit && speed < Mbit)
			return String.valueOf(speed / Kbit) + " Kbit/s";
		if (speed > Mbit && speed < Gbit)
			return String.valueOf(speed / Mbit) + " Mbit/s";
		if (speed > Gbit)
			return String.valueOf(speed / Gbit) + " Gbit/s";
		return "???";
	}

	public static void restartApplication(Class<?> CLAZZ, String[] MAIN_CLASS_ARGS) {
		Logger.info("Performing a restart");
		StringBuilder cmd = new StringBuilder();

		cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
		for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments())
			cmd.append(jvmArg + " ");
		cmd.append(" -cp ").append(CLAZZ.getName()).append(" ");
		cmd.append(" -jar ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
		for (String arg : MAIN_CLASS_ARGS)
			cmd.append(arg).append(" ");

		Logger.info("#####################################");
		Logger.info("#          Restarting now           #");
		Logger.info("# Restarting CMD: " + cmd.toString() + " #");
		Logger.info("#####################################");
		try {
			Runtime.getRuntime().exec(cmd.toString());
			System.exit(0);
		} catch (IOException e) {
			Logger.info("failed to restart application " + e);
		}

	}

}
