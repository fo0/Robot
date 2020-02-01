package com.fo0.robot.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fo0.robot.utils.Logger;

public class ConfigDetector {

	public static Path searchRobotConfig(String path) {
		if (!Paths.get(path).toFile().exists()) {
			Logger.error("Could not find path: " + path);
			return null;
		}

		try {
			//@formatter:off
			return Files.walk(Paths.get(path))
	                .filter(s -> s.toString().endsWith(".robot"))
	                .map(Path::getFileName)
	                .findFirst().orElse(null);
			//@formatter:on
		} catch (IOException e) {
			Logger.error("cannot walk thru the directory: " + path);
		}

		return null;
	}

}
