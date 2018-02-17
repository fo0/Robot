package com.fo0.robot.chain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChainData {

	@Builder.Default
	private ChainState state = ChainState.builder().build();

	@Builder.Default
	private ChainExeptions exception = ChainExeptions.builder().build();

}
