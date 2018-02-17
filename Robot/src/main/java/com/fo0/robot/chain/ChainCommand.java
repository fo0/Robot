package com.fo0.robot.chain;

public interface ChainCommand<T> {

	EChainResponse command(T ctx) throws Exception;

}
