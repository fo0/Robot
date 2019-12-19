package com.fo0.robot.commander;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.lang3.StringUtils;

import com.fo0.robot.listener.ValueChangeListener;
import com.fo0.robot.utils.Logger;
import com.fo0.robot.utils.OSCheck;
import com.google.common.collect.Lists;

public class Commander {

	private boolean error = false;
	private ValueChangeListener listener;
	private StringBuffer buffer = new StringBuffer();

	public Commander(ValueChangeListener listener) {
		this.listener = listener;
	}

	public Commander() {
	}

	public String execute(boolean wait, boolean shell, String homedir, String cmds) {
		return execute(wait, shell, homedir, false, Lists.newArrayList(cmds));
	}

	public String execute(boolean wait, boolean shell, String homedir, boolean quoting, List<String> cmds) {
		if (cmds == null || cmds.isEmpty()) {
			Logger.info("stopped cmd command is empty");
			return null;
		}

		CommandLine cmdLine = null;

		if (shell) {
			switch (OSCheck.getOperatingSystemType()) {
			case Windows:
				cmdLine = new CommandLine("cmd");
				if (shell) {
					cmdLine.addArgument("/c");
				}
				break;

			case Linux:
				cmdLine = new CommandLine("/bin/bash");
				if (shell) {
					cmdLine.addArgument("-c");
				}
				break;
			}

			cmdLine.addArguments(cmds.stream().toArray(String[]::new), quoting);
		} else {
			cmdLine = new CommandLine(cmds.get(0));
			cmdLine.addArguments(cmds.stream().skip(1).toArray(String[]::new), quoting);
		}

		Logger.debug("HomeDir: '" + Paths.get(homedir).toAbsolutePath() + "' => " + cmdLine.getExecutable() + ", "
				+ StringUtils.join(cmdLine.getArguments(), ","));

		try {
			Executor executor = createDefaultExecutor();
			executor.setStreamHandler(new PumpStreamHandler(new OutputStream() {

				@Override
				public void write(int b) throws IOException {
					try {
						String str = String.valueOf((char) b);
						if (listener != null)
							listener.event(str);
						buffer.append(str);
					} catch (Exception e) {
						Logger.debug(e.getMessage());
					}

				}
			}, new OutputStream() {

				@Override
				public void write(int b) throws IOException {
					error = true;
					try {
						String str = String.valueOf((char) b);
						if (listener != null)
							listener.event(str);
						buffer.append(str);
					} catch (Exception e) {
						Logger.debug(e.getMessage());
					}
				}
			}));

			// configure timeout
			// executor.setWatchdog(new ExecuteWatchdog(-1));

			executor.setWorkingDirectory(new File(homedir));

			if (wait) {
				executor.execute(cmdLine);
			} else {
				DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
				executor.execute(cmdLine, resultHandler);
			}
		} catch (Exception e) {
			error = true;
			Logger.error("failed commander in Cmd: " + cmdLine + " | " + e);
			Logger.debug(e.getMessage());
		}

		return buffer.toString();
	}

	/**
	 * @return
	 */
	private DefaultExecutor createDefaultExecutor() {
		return new DefaultExecutor();
	}

	public boolean isError() {
		return error;
	}

	public String getOutput() {
		return buffer.toString();
	}

	public void setListener(ValueChangeListener listener) {
		this.listener = listener;
	}

}
