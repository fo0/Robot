package com.fo0.robot.chain;

import java.util.Map.Entry;

public interface ChainCmdListener<T> {

	void event(T ctx, Entry<ChainID, ChainItem<T>> e);

}
