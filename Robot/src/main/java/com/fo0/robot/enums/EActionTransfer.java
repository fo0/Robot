package com.fo0.robot.enums;

import com.fo0.robot.utils.CONSTANTS;

public enum EActionTransfer {

	SCP_Upload(CONSTANTS.HOST + "(yourHost)\n" + CONSTANTS.PORT + "(port)\n" + CONSTANTS.USER + "(user)\n"
			+ CONSTANTS.PASSWORD + "(Password)\n" + CONSTANTS.SOURCE + "(localPath) \n" + CONSTANTS.DESTINATION
			+ "(remotePath)"),

	SCP_Download(CONSTANTS.HOST + "(yourHost)\n" + CONSTANTS.PORT + "(port)\n" + CONSTANTS.USER + "(user)\n"
			+ CONSTANTS.PASSWORD + "(Password)\n" + CONSTANTS.SOURCE + "(remotePath) \n" + CONSTANTS.DESTINATION
			+ "(localPath)"),

	FTP_Download(CONSTANTS.HOST + "(yourHost)\n" + CONSTANTS.PORT + "(port)\n" + CONSTANTS.USER + "(user)\n"
			+ CONSTANTS.PASSWORD + "(Password)\n" + CONSTANTS.SOURCE + "(remoteFileName) \n" + CONSTANTS.DESTINATION
			+ "(localPath)"),

	Download(CONSTANTS.SOURCE + "(http(s)://myURL/myFileDownload) \n" + CONSTANTS.DESTINATION
			+ "(/my/path/to/folder/or/file)");

	private String hint;

	private EActionTransfer(String hint) {

		this.hint = hint;
	}

	public String getHint() {
		return hint;
	}

}
