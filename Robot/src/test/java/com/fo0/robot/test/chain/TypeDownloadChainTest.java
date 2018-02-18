package com.fo0.robot.test.chain;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fo0.robot.enums.EActionType;
import com.fo0.robot.model.ActionItem;
import com.fo0.robot.model.KeyValue;
import com.fo0.robot.utils.CONSTANTS;

public class TypeDownloadChainTest {

	@Test
	public void downloadTestParsing() {
		ActionItem item = ActionItem.builder().type(EActionType.Download)
				.value("$SRC(http://fo0.me/ip.php) $DST(ip.php)").description("description example").build();
		List<KeyValue> list = item.parsedValue();
		KeyValue url = list.stream().filter(e -> e.getKey().equals(CONSTANTS.SOURCE)).findFirst().orElse(null);
		Assert.assertEquals(CONSTANTS.SOURCE, url.getKey());
		Assert.assertEquals("http://fo0.me/ip.php", url.getValue());

		KeyValue path = list.stream().filter(e -> e.getKey().equals(CONSTANTS.DESTINATION)).findFirst().orElse(null);
		Assert.assertEquals(CONSTANTS.DESTINATION, path.getKey());
		Assert.assertEquals("ip.php", path.getValue());
	}

}
