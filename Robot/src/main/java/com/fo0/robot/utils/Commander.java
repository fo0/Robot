package com.fo0.robot.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.lang3.StringUtils;

public class Commander {

	private boolean error = false;
	private String output;

	public String execute(boolean shell, String homedir, String cmds) {
		if (cmds == null || cmds.isEmpty()) {
			Logger.info("stopped cmd command is empty");
			return null;
		}

		StringBuffer buffer = new StringBuffer();
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
					try {
						buffer.append((char) b);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}, new OutputStream() {

				@Override
				public void write(int b) throws IOException {
					error = true;
					try {
						buffer.append((char) b);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}));

			try {
				executor.wait(30 * 1000);
			} catch (Exception e) {
				// TODO: handle exception
			}

		} catch (Exception e) {
			error = true;
			Logger.error("failed commander in Cmd: " + cli + " | " + e);
			e.printStackTrace();
		}
		output = buffer.toString();
		return output;
	}

	public boolean isError() {
		return error;
	}

	public String getOutput() {
		return output;
	}

}
