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

	@Builder.Default
	private ChainPreCommand<T> preCommand;

	@Builder.Default
	private ChainCommand<T> command;

	@Builder.Default
	private ChainPostCommand<T> postCommand;

	@Builder.Default
	private ChainError<T> error;

	@Builder.Default
	private ChainData data = ChainData.builder().build();

}
