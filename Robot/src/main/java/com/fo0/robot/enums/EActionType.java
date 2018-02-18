package com.fo0.robot.enums;

import com.fo0.robot.utils.CONSTANTS;

public enum EActionType {

	Commandline("Console Command"),

	Download(CONSTANTS.SOURCE + "(http://myURL.myDomain/myFileDownload.exe) \n" + CONSTANTS.DESTINATION
			+ "(/my/path/to/folder/or/file)"),

	Unzip(CONSTANTS.SOURCE + "(/my/path/to/folder/or/file) \n" + CONSTANTS.DESTINATION + "(/extract/to)"),

	Zip(CONSTANTS.SOURCE + "(/compress/this/file) \n" + CONSTANTS.DESTINATION + "(/path/to/destination)");

	private String hint;

	private EActionType(String hint) {
		this.hint = hint;
	}

	public String getHint() {
		return hint;
	}
}
