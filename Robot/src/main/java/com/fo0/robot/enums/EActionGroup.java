package com.fo0.robot.enums;

public enum EActionGroup {

	CLI(EActionCLI.class.getName()),

	SSH(EActionSSH.class.getName()),

	Transfer(EActionTransfer.class.getName()),

	Archiver(EActionArchive.class.getName());

	private String hint;

	private EActionGroup(String hint) {

		this.hint = hint;
	}

	public String getHint() {
		return hint;
	}
}
