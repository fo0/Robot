package com.fo0.robot.enums;

public enum EActionType {

	Commandline("Console Command"),

	Download("$URL(http://myURL.myDomain/myFileDownload.exe)" + "\n" + "$PATH(/my/path/to/folder/or/file)");

	private String hint;

	private EActionType(String hint) {
		this.hint = hint;
	}

	public String getHint() {
		return hint;
	}
}
