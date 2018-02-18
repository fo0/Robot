package com.fo0.robot.config;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

public class Config extends OptionsBase {

	@Option(name = "gui", abbrev = 'g', category = "gui", help = "simple gui", defaultValue = "true")
	public boolean gui;

	@Option(name = "cfg", abbrev = 'c', category = "config", help = "read config", defaultValue = "")
	public String configFile;
}
