package com.fo0.robot.chain;

public interface ChainPostCommand<T> {

	EChainResponse postCommand(T ctx) throws Exception;

}
