package com.fo0.robot.utils;

import java.util.Locale;

public final class OSCheck {

	/**
	 * Operating Systems
	 */
	public enum OSType {
		Windows, MacOS, Linux, Other, Unknown
	};

	// cached result of OS detection
	protected static OSType detectedOS;

	/**
	 * detect the operating system from the os.name System property and cache
	 * the result
	 *
	 * @returns - the operating system detected
	 */
	public static OSType getOperatingSystemType() {
		if (OSCheck.detectedOS == null) {
			String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
			if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0))
				OSCheck.detectedOS = OSType.MacOS;
			else if (OS.indexOf("win") >= 0)
				OSCheck.detectedOS = OSType.Windows;
			else if (OS.indexOf("nux") >= 0)
				OSCheck.detectedOS = OSType.Linux;
			else
				OSCheck.detectedOS = OSType.Other;
		}
		return OSCheck.detectedOS;
	}
}