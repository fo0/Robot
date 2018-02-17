package com.fo0.robot.chain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChainState {

	@Builder.Default
	private EState state = EState.Pending;

	@Builder.Default
	private EChainResponse pre = EChainResponse.Unset;

	@Builder.Default
	private EChainResponse post = EChainResponse.Unset;

	@Builder.Default
	private EChainResponse cmd = EChainResponse.Unset;
}
