package com.fo0.robot.chain;

public enum EChainResponse {

	Continue(1),

	Retry(2),

	Set(255),

	Unset(256),
	// stop from here

	Break(-1),

	Failed(-10);

	private int code;

	private EChainResponse(int code) {
		this.code = code;
	}

	public int code() {
		return code;
	}
}
