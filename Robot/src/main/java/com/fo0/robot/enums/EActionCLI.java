package com.fo0.robot.enums;

public enum EActionCLI {

	Commandline("Console Command");

	private String hint;

	private EActionCLI(String hint) {

		this.hint = hint;
	}

	public String getHint() {
		return hint;
	}
}
