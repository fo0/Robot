package com.fo0.robot.utils;

import java.util.regex.Pattern;

public class CONSTANTS {

	public static final String GITHUB_URI = "fo0/Robot";

	public static final String VERSION = "0.9.52";

	/**
	 * Basics
	 */
	public static final Pattern BASIC_PATTERN = Pattern
			.compile("(\\$\\w+)" + Pattern.quote("(") + "(.+?)" + Pattern.quote(")"));
	public static final String SOURCE = "$SRC";
	public static final String DESTINATION = "$DST";

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
