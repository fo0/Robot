package com.fo0.robot.enums;

import com.fo0.robot.utils.CONSTANTS;

public enum EActionArchive {

	Unzip(CONSTANTS.SOURCE + "(/my/path/to/folder/or/file) \n" + CONSTANTS.DESTINATION + "(/extract/to)"),

	Zip(CONSTANTS.SOURCE + "(/compress/this/file) \n" + CONSTANTS.DESTINATION + "(/path/to/destination)"),

	TAR(CONSTANTS.SOURCE + "(/compress/this/file) \n" + CONSTANTS.DESTINATION + "(/path/to/destination)"),

	UNTAR(CONSTANTS.SOURCE + "(/compress/this/file) \n" + CONSTANTS.DESTINATION + "(/path/to/destination)"),

	TARGZ(CONSTANTS.SOURCE + "(/compress/this/file) \n" + CONSTANTS.DESTINATION + "(/path/to/destination)"),

	UNTARGZ(CONSTANTS.SOURCE + "(/compress/this/file) \n" + CONSTANTS.DESTINATION + "(/path/to/destination)"),

	SEVEN_ZIP(CONSTANTS.SOURCE + "(/compress/this/file) \n" + CONSTANTS.DESTINATION + "(/path/to/destination)"),

	UNSEVEN_ZIP(CONSTANTS.SOURCE + "(/compress/this/file) \n" + CONSTANTS.DESTINATION + "(/path/to/destination)");

	private String hint;

	private EActionArchive(String hint) {

		this.hint = hint;
	}

	public String getHint() {
		return hint;
	}
}
