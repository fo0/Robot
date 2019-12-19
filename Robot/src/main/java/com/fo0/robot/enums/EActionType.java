package com.fo0.robot.enums;

import com.fo0.robot.utils.CONSTANTS_PATTERN;

public enum EActionType {

	Simple_Commandline("Console Command"),

	Commandline(CONSTANTS_PATTERN.WAIT + "([OPT] true|false)\n" + CONSTANTS_PATTERN.HOME + "([OPT] HomeDir)\n"
			+ CONSTANTS_PATTERN.CMDS + "(Cmd1,Cmd2,...,CmdN)"),

	Sleep("In milliseconds"),

	COPY(CONSTANTS_PATTERN.SOURCE + "(/copy/this/file) \n" + CONSTANTS_PATTERN.DESTINATION + "(/path/to/destination)"),

	MOVE(CONSTANTS_PATTERN.SOURCE + "(/move/this/file) \n" + CONSTANTS_PATTERN.DESTINATION + "(/path/to/destination) \n"
			+ CONSTANTS_PATTERN.FORCE + "([OPT] overwrite: true|false))"),

	Download(CONSTANTS_PATTERN.SOURCE + "(http(s)://myURL/myFileDownload) \n" + CONSTANTS_PATTERN.DESTINATION
			+ "([OPT] /my/path/to/folder/or/file)\n" + CONSTANTS_PATTERN.TIMEOUT + "([OPT] connection timeout in ms)"),

	Unzip(CONSTANTS_PATTERN.SOURCE + "(/my/path/to/folder/or/file) \n" + CONSTANTS_PATTERN.DESTINATION
			+ "(/extract/to)"),

	Zip(CONSTANTS_PATTERN.SOURCE + "(/compress/this/file) \n" + CONSTANTS_PATTERN.DESTINATION
			+ "(/path/to/destination)"),

	TAR(CONSTANTS_PATTERN.SOURCE + "(/compress/this/file) \n" + CONSTANTS_PATTERN.DESTINATION
			+ "([OPT] /path/to/destination)"),

	UNTAR(CONSTANTS_PATTERN.SOURCE + "(/compress/this/file) \n" + CONSTANTS_PATTERN.DESTINATION
			+ "(/path/to/destination)"),

	TARGZ(CONSTANTS_PATTERN.SOURCE + "(/compress/this/file) \n" + CONSTANTS_PATTERN.DESTINATION
			+ "(/path/to/destination)"),

	UNTARGZ(CONSTANTS_PATTERN.SOURCE + "(/compress/this/file) \n" + CONSTANTS_PATTERN.DESTINATION
			+ "(/path/to/destination)"),

	SEVEN_ZIP(CONSTANTS_PATTERN.SOURCE + "(/compress/this/file) \n" + CONSTANTS_PATTERN.DESTINATION
			+ "(/path/to/destination)"),

	UNSEVEN_ZIP(CONSTANTS_PATTERN.SOURCE + "(/compress/this/file) \n" + CONSTANTS_PATTERN.DESTINATION
			+ "(/path/to/destination)"),

	SSH(CONSTANTS_PATTERN.HOST + "(yourHost)\n" + CONSTANTS_PATTERN.PORT + "([OPT] port)\n" + CONSTANTS_PATTERN.USER
			+ "(user)\n" + CONSTANTS_PATTERN.PASSWORD + "(Password)\n" + CONSTANTS_PATTERN.CMD + "(CMD)"),

	SCP_Upload(CONSTANTS_PATTERN.HOST + "(yourHost)\n" + CONSTANTS_PATTERN.PORT + "([OPT] port)\n"
			+ CONSTANTS_PATTERN.USER + "(user)\n" + CONSTANTS_PATTERN.PASSWORD + "(Password)\n"
			+ CONSTANTS_PATTERN.SOURCE + "(localPath) \n" + CONSTANTS_PATTERN.DESTINATION + "(remotePath)"),

	SCP_Download(CONSTANTS_PATTERN.HOST + "(yourHost)\n" + CONSTANTS_PATTERN.PORT + "([OPT] port)\n"
			+ CONSTANTS_PATTERN.USER + "(user)\n" + CONSTANTS_PATTERN.PASSWORD + "(Password)\n"
			+ CONSTANTS_PATTERN.SOURCE + "(remotePath) \n" + CONSTANTS_PATTERN.DESTINATION + "(localPath)"),

	FTP_Download(CONSTANTS_PATTERN.HOST + "(yourHost)\n" + CONSTANTS_PATTERN.PORT + "([OPT] port)\n"
			+ CONSTANTS_PATTERN.USER + "([OPT] user)\n" + CONSTANTS_PATTERN.PASSWORD + "(Password)\n"
			+ CONSTANTS_PATTERN.SOURCE + "(remoteFileName) \n" + CONSTANTS_PATTERN.DESTINATION + "(localPath)");

	private String hint;

	private EActionType(String hint) {
		this.hint = hint;
	}

	public String getHint() {
		return hint;
	}
}
