package com.fo0.robot.model;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fo0.robot.enums.EActionType;
import com.fo0.robot.utils.CONSTANTS;
import com.fo0.robot.utils.Random;
import com.google.common.collect.Lists;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(exclude = { "type", "value" })
@EqualsAndHashCode(of = { "id" })
@Builder
public class ActionItem {

	@Builder.Default
	private String id = Random.alphanumeric(10);

	@Builder.Default
	private EActionType type = EActionType.Commandline;

	private String description;

	private String value;

	@Builder.Default
	private boolean active = true;

	public List<KeyValue> parsedValue() {
		List<KeyValue> list = Lists.newArrayList();
		switch (type) {
		case Commandline:
			// detected cmd ... do safety skip
			list.add(KeyValue.builder().key(type.name()).value(value).build());
			break;

		case Sleep:
			list.add(KeyValue.builder().key(type.name()).value(value).build());
			break;
			
		default:
			// doing basic parsing
			Pattern p = CONSTANTS.BASIC_PATTERN;
			Matcher m = p.matcher(value);

			while (m.find()) {
				list.add(KeyValue.builder().key(m.group(1)).value(m.group(2)).build());
			}
			break;
		}

		return list;
	}
}
