package com.fo0.robot.enums;

import com.fo0.robot.utils.CONSTANTS;

public enum EActionType {

	Commandline("Console Command"),

	COPY(CONSTANTS.SOURCE + "(/copy/this/file) \n" + CONSTANTS.DESTINATION + "(/path/to/destination)"),

	MOVE(CONSTANTS.SOURCE + "(/move/this/file) \n" + CONSTANTS.DESTINATION + "(/path/to/destination) \n"
			+ CONSTANTS.FORCE + "([OPT] overwrite: true|false))"),

	Download(CONSTANTS.SOURCE + "(http(s)://myURL/myFileDownload) \n" + CONSTANTS.DESTINATION
			+ "([OPT] /my/path/to/folder/or/file)\n" + CONSTANTS.TIMEOUT + "([OPT] connection timeout in ms)"),

	Unzip(CONSTANTS.SOURCE + "(/my/path/to/folder/or/file) \n" + CONSTANTS.DESTINATION + "(/extract/to)"),

	Zip(CONSTANTS.SOURCE + "(/compress/this/file) \n" + CONSTANTS.DESTINATION + "(/path/to/destination)"),

	TAR(CONSTANTS.SOURCE + "(/compress/this/file) \n" + CONSTANTS.DESTINATION + "([OPT] /path/to/destination)"),

	UNTAR(CONSTANTS.SOURCE + "(/compress/this/file) \n" + CONSTANTS.DESTINATION + "(/path/to/destination)"),

	TARGZ(CONSTANTS.SOURCE + "(/compress/this/file) \n" + CONSTANTS.DESTINATION + "(/path/to/destination)"),

	UNTARGZ(CONSTANTS.SOURCE + "(/compress/this/file) \n" + CONSTANTS.DESTINATION + "(/path/to/destination)"),

	SEVEN_ZIP(CONSTANTS.SOURCE + "(/compress/this/file) \n" + CONSTANTS.DESTINATION + "(/path/to/destination)"),

	UNSEVEN_ZIP(CONSTANTS.SOURCE + "(/compress/this/file) \n" + CONSTANTS.DESTINATION + "(/path/to/destination)"),

	SSH(CONSTANTS.HOST + "(yourHost)\n" + CONSTANTS.PORT + "([OPT] port)\n" + CONSTANTS.USER + "(user)\n"
			+ CONSTANTS.PASSWORD + "(Password)\n" + CONSTANTS.CMD + "(CMD)"),

	SCP_Upload(CONSTANTS.HOST + "(yourHost)\n" + CONSTANTS.PORT + "([OPT] port)\n" + CONSTANTS.USER + "(user)\n"
			+ CONSTANTS.PASSWORD + "(Password)\n" + CONSTANTS.SOURCE + "(localPath) \n" + CONSTANTS.DESTINATION
			+ "(remotePath)"),

	SCP_Download(CONSTANTS.HOST + "(yourHost)\n" + CONSTANTS.PORT + "([OPT] port)\n" + CONSTANTS.USER + "(user)\n"
			+ CONSTANTS.PASSWORD + "(Password)\n" + CONSTANTS.SOURCE + "(remotePath) \n" + CONSTANTS.DESTINATION
			+ "(localPath)"),

	FTP_Download(CONSTANTS.HOST + "(yourHost)\n" + CONSTANTS.PORT + "([OPT] port)\n" + CONSTANTS.USER + "([OPT] user)\n"
			+ CONSTANTS.PASSWORD + "(Password)\n" + CONSTANTS.SOURCE + "(remoteFileName) \n" + CONSTANTS.DESTINATION
			+ "(localPath)");

	private String hint;

	private EActionType(String hint) {
		this.hint = hint;
	}

	public String getHint() {
		return hint;
	}
}
