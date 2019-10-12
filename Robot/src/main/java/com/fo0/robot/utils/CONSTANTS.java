package com.fo0.robot.utils;

import java.util.regex.Pattern;

public class CONSTANTS {

	public static final boolean DEBUG = false;

	public static final String GITHUB_URI = "fo0/Robot";

	public static final String VERSION = "1.0.2";

	/**
	 * Basics
	 */
	public static final Pattern BASIC_PATTERN = Pattern
			.compile("(\\$\\w+)" + Pattern.quote("(") + "(.+?)" + Pattern.quote(")"));
	public static final String SOURCE = "$SRC";
	public static final String DESTINATION = "$DST";
	public static final String FORCE = "$FORCE";

	/**
	 * Timings
	 */
	public static final String TIMEOUT = "$TIMEOUT";

	/**
	 * SSH, SCP only
	 */
	public static final Pattern SSH_PATTERN = BASIC_PATTERN;
	public static final String HOST = "$HOST";
	public static final String USER = "$USER";
	public static final String PASSWORD = "$PASSWORD";
	public static final String PORT = "$PORT";
	public static final String CMD = "$CMD";
	// file: using basic $SRC and $DST
}
