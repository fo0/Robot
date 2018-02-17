package com.fo0.robot.chain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChainExeptions {

	@Builder.Default
	private Exception cmd;

	@Builder.Default
	private Exception pre;

	@Builder.Default
	private Exception post;

}
