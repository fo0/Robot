package com.fo0.robot.enums;

import com.fo0.robot.utils.CONSTANTS_PATTERN;

public enum EActionTransfer {

	SCP_Upload(CONSTANTS_PATTERN.HOST + "(yourHost)\n" + CONSTANTS_PATTERN.PORT + "(port)\n" + CONSTANTS_PATTERN.USER + "(user)\n"
			+ CONSTANTS_PATTERN.PASSWORD + "(Password)\n" + CONSTANTS_PATTERN.SOURCE + "(localPath) \n" + CONSTANTS_PATTERN.DESTINATION
			+ "(remotePath)"),

	SCP_Download(CONSTANTS_PATTERN.HOST + "(yourHost)\n" + CONSTANTS_PATTERN.PORT + "(port)\n" + CONSTANTS_PATTERN.USER + "(user)\n"
			+ CONSTANTS_PATTERN.PASSWORD + "(Password)\n" + CONSTANTS_PATTERN.SOURCE + "(remotePath) \n" + CONSTANTS_PATTERN.DESTINATION
			+ "(localPath)"),

	FTP_Download(CONSTANTS_PATTERN.HOST + "(yourHost)\n" + CONSTANTS_PATTERN.PORT + "(port)\n" + CONSTANTS_PATTERN.USER + "(user)\n"
			+ CONSTANTS_PATTERN.PASSWORD + "(Password)\n" + CONSTANTS_PATTERN.SOURCE + "(remoteFileName) \n" + CONSTANTS_PATTERN.DESTINATION
			+ "(localPath)"),

	Download(CONSTANTS_PATTERN.SOURCE + "(http(s)://myURL/myFileDownload) \n" + CONSTANTS_PATTERN.DESTINATION
			+ "(/my/path/to/folder/or/file)");

	private String hint;

	private EActionTransfer(String hint) {

		this.hint = hint;
	}

	public String getHint() {
		return hint;
	}

}
