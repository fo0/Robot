package com.fo0.robot.chain.action;

import java.util.List;

import com.fo0.robot.model.KeyValue;

public class ActionUtils {

	/**
	 * @param cmdList
	 * @return
	 */
	public static KeyValue parseAction(List<KeyValue> cmdList, String variable, String defaultValue) {
		//@formatter:off
		return cmdList.stream()
				.filter(e -> e.getKey().equals(variable))
				.findFirst()
				// return user.dir as default
				.orElseGet(() -> (defaultValue != null ? KeyValue.builder().value(defaultValue).build() : null));
		//@formatter:on
	}

}
