package com.fo0.robot.chain;

public enum EState {

	Success(1),

	Pending(2),

	Processing(3),

	// stop from here

	Stopped(-1),

	Failed(-255);

	private int code;

	private EState(int code) {
		this.code = code;
	}

	public int code() {
		return code;
	}

}
