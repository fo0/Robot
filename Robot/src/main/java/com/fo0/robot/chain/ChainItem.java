package com.fo0.robot.chain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChainItem<T> {

	private ChainPreCommand<T> preCommand;

	private ChainCommand<T> command;

	private ChainPostCommand<T> postCommand;

	private ChainError<T> error;

	@Builder.Default
	private ChainData data = ChainData.builder().build();

}
