package com.fo0.robot.model;

import com.fo0.robot.enums.EActionType;
import com.fo0.robot.utils.Random;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionItem {

	@Builder.Default
	private String id = Random.alphanumeric(10);

	@Builder.Default
	private EActionType type = EActionType.Commandline;

	@Builder.Default
	private String description;

	@Builder.Default
	private String value;

}
