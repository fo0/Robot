package com.fo0.robot.enums;

import com.fo0.robot.utils.CONSTANTS;

public enum EActionSSH {

	SSH(CONSTANTS.HOST + "(yourHost)\n" + CONSTANTS.PORT + "(port)\n" + CONSTANTS.USER + "(user)\n" + CONSTANTS.PASSWORD
			+ "(Password)\n" + CONSTANTS.CMD + "(CMD)");

	private String hint;

	private EActionSSH(String hint) {

		this.hint = hint;
	}

	public String getHint() {
		return hint;
	}
}
