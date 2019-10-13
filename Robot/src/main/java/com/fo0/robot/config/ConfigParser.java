package com.fo0.robot.config;

import java.util.Collections;

import com.google.devtools.common.options.OptionsParser;

public class ConfigParser {

	public static Config parseConfig(String[] args) {
		OptionsParser parser = OptionsParser.newOptionsParser(Config.class);
		// printUsage(parser);
		parser.parseAndExitUponError(args);
		return parser.getOptions(Config.class);
	}

	private static void printUsage(OptionsParser parser) {
		System.out.println("Usage: java -jar robot.jar OPTIONS");
		System.out.println(parser.describeOptions(Collections.<String, String>emptyMap(), OptionsParser.HelpVerbosity.LONG));
	}

}
