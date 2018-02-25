package com.fo0.robot.test.chain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fo0.robot.model.KeyValue;
import com.fo0.robot.utils.CONSTANTS;

public class SSHPatternCheck {

	@Test
	public void parsingSSHInputText() {
		String inputText = "$HOST(github.com) $PORT(22) $CMD(echo test)";

		Pattern pattern = CONSTANTS.SSH_PATTERN;
		Matcher m = pattern.matcher(inputText);

		while (m.find()) {
			System.out.println(KeyValue.builder().key(m.group(1)).value(m.group(2)).build());
		}

		Assert.assertEquals("$(HOST)", "github.com");
		Assert.assertEquals("$(PORT)", "22");
		Assert.assertEquals("$(CMD)", "echo test");
	}

	// $HOST(fo0systems.net) $PORT(4444) $USER(root) $PASSWORD(max123Max123)
	// $SRC(/root/test) $DST(/home/max/git/Robot/Robot/test/test)

	@Test
	public void parsingSCPInputText() {
		String inputText = "$HOST(fo0systems.net) " + "$PORT(4444) " + "$USER(root) " + "$PASSWORD(max123Max123) "
				+ "$SRC(/root/test) " + "$DST(/home/max/git/Robot/Robot/test/test)";

		Pattern pattern = CONSTANTS.SSH_PATTERN;
		Matcher m = pattern.matcher(inputText);

		while (m.find()) {
			System.out.println(KeyValue.builder().key(m.group(1)).value(m.group(2)).build());
		}

		Assert.assertEquals("$(HOST)", "github.com");
		Assert.assertEquals("$(PORT)", "22");
		Assert.assertEquals("$(CMD)", "echo test");
	}
}
