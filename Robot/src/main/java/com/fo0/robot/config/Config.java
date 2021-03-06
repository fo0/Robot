package com.fo0.robot.config;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

import lombok.Getter;

@Getter
public class Config extends OptionsBase {

	@Option(name = "nogui", abbrev = 'n', category = "GUI", help = "Graphical User Interface", defaultValue = "false")
	public boolean nogui;

	@Option(name = "config", abbrev = 'c', category = "Config", help = "Read Plain-Config", defaultValue = "")
	public String config;
	
	@Option(name = "configFile", abbrev = 'f', category = "Config", help = "Read Config-File by Path", defaultValue = "")
	public String configFile;

	@Option(name = "config-detect", abbrev = 'd', category = "Config", help = "Auto detect and load config", defaultValue = "true")
	public boolean configDetect;
	
	@Option(name = "verbose", abbrev = 'v', category = "Verbose", help = "Debug Log Level", defaultValue = "false")
	public boolean debug;

	@Option(name = "logfile", abbrev = 'l', category = "Verbose", help = "Logging to File 'robot.log'", defaultValue = "false")
	public boolean logFile;

	@Option(name = "ignoreErrors", abbrev = 'i', category = "Errors", help = "Ignore Errors in Chain", defaultValue = "false")
	public boolean ignoreErrors;

	@Option(name = "update", abbrev = 'u', category = "Update", help = "Download Updates if needed", defaultValue = "false")
	public boolean update;
	
	/**
	 * PATTERN VARIABLE
	 */
	@Option(name = "variable-pattern", abbrev = 'p', category = "Variable", help = "Variable Pattern", defaultValue = "$")
	public String variable;

	@Option(name = "auto-terminate", abbrev = 't', category = "System", help = "Automatically terminate after run", defaultValue = "false")
	public boolean terminate;
	
}
