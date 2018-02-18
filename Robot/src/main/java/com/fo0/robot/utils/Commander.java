package com.fo0.robot.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.StreamHandler;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.lang3.StringUtils;

public class Commander {

	public static List<String> execute(boolean shell, String homedir, String cmds) {

		if (cmds == null || cmds.isEmpty()) {
			Logger.info("stopped cmd command is empty");
			return null;
		}

		List<String> listOutput = new ArrayList<String>();
		CommandLine cli = null;
		DefaultExecutor executor = null;

		switch (OSCheck.getOperatingSystemType()) {
		case Windows:
			cli = new CommandLine("cmd");
			if (shell) {
				cli.addArgument("/c ");
			}
			break;

		case Linux:
			cli = new CommandLine("/bin/bash");
			if (shell) {
				cli.addArgument("-c");
			}
			break;

		}

		cli.addArgument(cmds, false);

		Logger.info("HomeDir: " + homedir + " => " + StringUtils.join(cli.getArguments(), ","));

		try {
			executor = new DefaultExecutor();
			executor.execute(cli);
			executor.setWorkingDirectory(new File(homedir));
			executor.setStreamHandler(new PumpStreamHandler(new OutputStream() {

				@Override
				public void write(int b) throws IOException {
					System.out.println((char) b);
				}
			}, new OutputStream() {

				@Override
				public void write(int b) throws IOException {
					System.out.println((char) b);
				}
			}));

		} catch (Exception e) {
			Logger.error("failed commander in Cmd: " + cli + " | " + e);
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
