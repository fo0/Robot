package com.fo0.robot.chain;

public interface ChainError<T> {

	void error(T ctx, ChainExeptions e) throws Exception;

}
