package com.fo0.robot.utils;

import java.util.regex.Pattern;

public class CONSTANTS_PATTERN {

	public static final Pattern BASIC_PATTERN = Pattern
	.compile("(" + Pattern.quote(CONSTANTS_PATTERN_CONF.VARIABLE_SIGN) + "\\w+)" + Pattern.quote("(") + "(.+?)" + Pattern.quote(")"));
	public static final String SOURCE = CONSTANTS_PATTERN_CONF.VARIABLE_SIGN + "SRC";
	public static final String DESTINATION = CONSTANTS_PATTERN_CONF.VARIABLE_SIGN + "DST";
	public static final String FORCE = CONSTANTS_PATTERN_CONF.VARIABLE_SIGN + "FORCE";
	
	/**
	 * Timings
	 */
	public static final String TIMEOUT = CONSTANTS_PATTERN_CONF.VARIABLE_SIGN + "TIMEOUT";
	
	/**
	 * SSH, SCP only
	 */
	public static final Pattern SSH_PATTERN = BASIC_PATTERN;
	public static final String HOST = CONSTANTS_PATTERN_CONF.VARIABLE_SIGN + "HOST";
	public static final String USER = CONSTANTS_PATTERN_CONF.VARIABLE_SIGN + "USER";
	public static final String PASSWORD = CONSTANTS_PATTERN_CONF.VARIABLE_SIGN + "PASSWORD";
	public static final String PORT = CONSTANTS_PATTERN_CONF.VARIABLE_SIGN + "PORT";
	public static final String CMD = CONSTANTS_PATTERN_CONF.VARIABLE_SIGN + "CMD";
	public static final String CMDS = CONSTANTS_PATTERN_CONF.VARIABLE_SIGN + "CMDS";
	public static final String HOME = CONSTANTS_PATTERN_CONF.VARIABLE_SIGN + "HOME";
	public static final String WAIT = CONSTANTS_PATTERN_CONF.VARIABLE_SIGN + "WAIT";
	
}
