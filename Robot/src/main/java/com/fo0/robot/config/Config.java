package com.fo0.robot.config;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

public class Config extends OptionsBase {

	@Option(name = "nogui", abbrev = 'n', category = "GUI", help = "Graphical User Interface", defaultValue = "false")
	public boolean nogui;

	@Option(name = "config", abbrev = 'c', category = "Config", help = "Read config by Path", defaultValue = "")
	public String configFile;
}
