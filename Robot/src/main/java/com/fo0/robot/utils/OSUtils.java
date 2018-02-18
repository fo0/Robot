package com.fo0.robot.utils;

import java.io.File;

public class OSUtils {

	public static final String WIN_BASH_CMD = "start";
	public static final String WIN_BASH_CMD_QUIET = "start /b";

	public static final String JAVA_USER_DIR = "user.dir";

	public static final String LINUX_BIN_BASH = "/bin/bash";
	public static final String LIUNX_CMD = "/bin/bash";
	public static final String LINUX_REDIRECT_DEV_NULL = "> /dev/null 2>&1 &";

	public static final char FILE_SEPARATOR = File.separatorChar;
	public static final String TAR_GZ = ".tar.gz";
	public static final String ZIP = ".zip";

	// windows
	private static final String WINDOWS_SCRIPT_VARIABLE_DECLARE = "%";

	// linux
	private static final String LINUX_SCRIPT_VARIABLE_DECLARE = "$";

	public static String declareVariable(String var) {
		switch (OSCheck.getOperatingSystemType()) {
		case Windows:
			var = OSUtils.WINDOWS_SCRIPT_VARIABLE_DECLARE + var;
			break;

		case Linux:
			var = OSUtils.LINUX_SCRIPT_VARIABLE_DECLARE + var;
			break;

		default:
			break;
		}

		return var;
	}

	public static String useVariable(String var) {
		switch (OSCheck.getOperatingSystemType()) {
		case Windows:
			var = OSUtils.WINDOWS_SCRIPT_VARIABLE_DECLARE + var + OSUtils.WINDOWS_SCRIPT_VARIABLE_DECLARE;
			break;

		case Linux:
			var = OSUtils.LINUX_SCRIPT_VARIABLE_DECLARE + var;
			break;

		default:
			break;
		}

		return var;
	}

}
