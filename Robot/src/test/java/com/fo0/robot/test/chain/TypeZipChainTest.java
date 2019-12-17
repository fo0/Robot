package com.fo0.robot.test.chain;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fo0.robot.enums.EActionType;
import com.fo0.robot.model.ActionItem;
import com.fo0.robot.model.KeyValue;
import com.fo0.robot.utils.CONSTANTS_PATTERN;

public class TypeZipChainTest {

	@Test
	public void unzip() {
		ActionItem item = ActionItem.builder().type(EActionType.Unzip)
				.value("$SRC(/home/max/Schreibtisch/Robot/test/agent.zip) $DST(/home/max/Schreibtisch/Robot/test/x)")
				.description("description example").build();

		List<KeyValue> list = item.parsedValue();
		KeyValue zipFile = list.stream().filter(e -> e.getKey().equals(CONSTANTS_PATTERN.SOURCE)).findFirst().orElse(null);
		Assert.assertEquals(CONSTANTS_PATTERN.SOURCE, zipFile.getKey());
		Assert.assertEquals("/home/max/Schreibtisch/Robot/test/agent.zip", zipFile.getValue());

		KeyValue zipDstFolder = list.stream().filter(e -> e.getKey().equals(CONSTANTS_PATTERN.DESTINATION)).findFirst()
				.orElse(null);
		Assert.assertEquals(CONSTANTS_PATTERN.DESTINATION, zipDstFolder.getKey());
		Assert.assertEquals("/home/max/Schreibtisch/Robot/test/x", zipDstFolder.getValue());
	}

	@Test
	public void zip() {
		ActionItem item = ActionItem.builder().type(EActionType.Zip)
				.value("$SRC(/home/max/Schreibtisch/Robot/test/agent.zip) $DST(/home/max/Schreibtisch/Robot/test/x)")
				.description("description example").build();

		List<KeyValue> list = item.parsedValue();
		KeyValue zipFile = list.stream().filter(e -> e.getKey().equals(CONSTANTS_PATTERN.SOURCE)).findFirst().orElse(null);
		Assert.assertEquals(CONSTANTS_PATTERN.SOURCE, zipFile.getKey());
		Assert.assertEquals("/home/max/Schreibtisch/Robot/test/agent.zip", zipFile.getValue());

		KeyValue zipDstFolder = list.stream().filter(e -> e.getKey().equals(CONSTANTS_PATTERN.DESTINATION)).findFirst()
				.orElse(null);
		Assert.assertEquals(CONSTANTS_PATTERN.DESTINATION, zipDstFolder.getKey());
		Assert.assertEquals("/home/max/Schreibtisch/Robot/test/x", zipDstFolder.getValue());
	}

}
