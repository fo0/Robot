package com.fo0.robot.chain;

public interface ChainPreCommand<T> {

	EChainResponse preCommand(T ctx) throws Exception;

}
