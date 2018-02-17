package com.fo0.robot.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Commander {

	public static List<String> execute(boolean waitForResult, boolean shell, String homedir, List<String> cmds) {

		// first argument for the script or executable

		if (cmds.isEmpty()) {
			Logger.info("stopped cmd command is empty");
			return null;
		}

		List<String> listOutput = new ArrayList<String>();

		List<String> cmd = new ArrayList<String>();
		ProcessBuilder processBuilder = null;

		String operatingSystem = System.getProperty("os.name");

		if (operatingSystem.toLowerCase().contains("window")) {
			if (shell) {
				cmd.add("cmd");
				cmd.add("/c");
			}

			cmd.addAll(cmds);
			processBuilder = new ProcessBuilder(cmd);

		} else {
			if (shell) {
				cmd.add("/bin/bash");
				cmd.add("-c");
			}

			cmd.addAll(cmds);
			processBuilder = new ProcessBuilder(cmd);
		}

		Logger.info("HomeDir: " + homedir + " => " + cmds);

		processBuilder.directory(new File(homedir));

		processBuilder.redirectErrorStream(true);

		try {
			Process process = processBuilder.start();

			// stdout
			if (waitForResult) {
				InputStream stdout = process.getInputStream();
				listOutput = Commander.getStringListFromStream(process.getInputStream());
				stdout.close();
			}

			if (listOutput != null && !listOutput.isEmpty())
				return listOutput;
			else {
				if (listOutput == null)
					listOutput = new ArrayList<String>();
				listOutput.add("ERROR");
				return listOutput;
			}

		} catch (Exception e) {
			Logger.error("failed commander in Cmd: " + cmd + " | " + e);
		}
		return null;

	}

	private static List<String> getStringListFromStream(InputStream stream) throws IOException {
		if (stream != null) {
			List<String> list = new ArrayList<String>();

			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
				String line;
				while ((line = reader.readLine()) != null) {
					Logger.debug(line);
					list.add(line);
				}

			} finally {
				stream.close();
			}
			return list;
		} else
			return null;
	}

}
