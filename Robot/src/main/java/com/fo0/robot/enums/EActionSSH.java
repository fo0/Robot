package com.fo0.robot.enums;

import com.fo0.robot.utils.CONSTANTS_PATTERN;

public enum EActionSSH {

	SSH(CONSTANTS_PATTERN.HOST + "(yourHost)\n" + CONSTANTS_PATTERN.PORT + "(port)\n" + CONSTANTS_PATTERN.USER + "(user)\n" + CONSTANTS_PATTERN.PASSWORD
			+ "(Password)\n" + CONSTANTS_PATTERN.CMD + "(CMD)");

	private String hint;

	private EActionSSH(String hint) {

		this.hint = hint;
	}

	public String getHint() {
		return hint;
	}
}
