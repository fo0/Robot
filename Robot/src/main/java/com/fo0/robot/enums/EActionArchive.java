package com.fo0.robot.enums;

import com.fo0.robot.utils.CONSTANTS_PATTERN;

public enum EActionArchive {

	Unzip(CONSTANTS_PATTERN.SOURCE + "(/my/path/to/folder/or/file) \n" + CONSTANTS_PATTERN.DESTINATION + "(/extract/to)"),

	Zip(CONSTANTS_PATTERN.SOURCE + "(/compress/this/file) \n" + CONSTANTS_PATTERN.DESTINATION + "(/path/to/destination)"),

	TAR(CONSTANTS_PATTERN.SOURCE + "(/compress/this/file) \n" + CONSTANTS_PATTERN.DESTINATION + "(/path/to/destination)"),

	UNTAR(CONSTANTS_PATTERN.SOURCE + "(/compress/this/file) \n" + CONSTANTS_PATTERN.DESTINATION + "(/path/to/destination)"),

	TARGZ(CONSTANTS_PATTERN.SOURCE + "(/compress/this/file) \n" + CONSTANTS_PATTERN.DESTINATION + "(/path/to/destination)"),

	UNTARGZ(CONSTANTS_PATTERN.SOURCE + "(/compress/this/file) \n" + CONSTANTS_PATTERN.DESTINATION + "(/path/to/destination)"),

	SEVEN_ZIP(CONSTANTS_PATTERN.SOURCE + "(/compress/this/file) \n" + CONSTANTS_PATTERN.DESTINATION + "(/path/to/destination)"),

	UNSEVEN_ZIP(CONSTANTS_PATTERN.SOURCE + "(/compress/this/file) \n" + CONSTANTS_PATTERN.DESTINATION + "(/path/to/destination)");

	private String hint;

	private EActionArchive(String hint) {

		this.hint = hint;
	}

	public String getHint() {
		return hint;
	}
}
