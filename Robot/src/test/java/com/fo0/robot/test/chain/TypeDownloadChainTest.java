package com.fo0.robot.test.chain;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fo0.robot.enums.EActionType;
import com.fo0.robot.model.ActionItem;
import com.fo0.robot.model.KeyValue;

public class TypeDownloadChainTest {

	@Test
	public void downloadTestParsing() {
		ActionItem item = ActionItem.builder().type(EActionType.Download)
				.value("$URL(http://fo0.me/ip.php) $PATH(ix.php)").description("description example").build();
		List<KeyValue> list = item.parsedValue();
		KeyValue url = list.stream().filter(e -> e.getKey().equals("URL")).findFirst().orElse(null);
		Assert.assertEquals("URL", url.getKey());
		Assert.assertEquals("http://fo0.me/ip.php", url.getValue());

		KeyValue path = list.stream().filter(e -> e.getKey().equals("PATH")).findFirst().orElse(null);
		Assert.assertEquals("PATH", path.getKey());
		Assert.assertEquals("ip.php", path.getValue());
	}

}
