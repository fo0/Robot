package com.fo0.robot.test.chain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import com.fo0.robot.model.KeyValue;
import com.fo0.robot.utils.CONSTANTS;

public class SSHPatternCheck {

	@Test
	public void parsingSSHInputText() {
		String inputText = "$HOST(github.com) $PORT(22) $CMD(echo test)";

		Pattern pattern = CONSTANTS.BASIC_PATTERN;
		Matcher m = pattern.matcher(inputText);

		List<KeyValue> list = new ArrayList<KeyValue>();

		while (m.find()) {
			list.add(KeyValue.builder().key(m.group(1)).value(m.group(2)).build());
		}

		KeyValue host = list.stream().filter(e -> e.getKey().equals(CONSTANTS.HOST)).findFirst().orElse(null);
		KeyValue port = list.stream().filter(e -> e.getKey().equals(CONSTANTS.PORT)).findFirst().orElse(null);
		KeyValue cmd = list.stream().filter(e -> e.getKey().equals(CONSTANTS.CMD)).findFirst().orElse(null);

		Assert.assertEquals(host.getValue(), "github.com");
		Assert.assertEquals(port.getValue(), "22");
		Assert.assertEquals(cmd.getValue(), "echo test");
	}

	// $HOST(fo0systems.net) $PORT(4444) $USER(root) $PASSWORD(max123Max123)
	// $SRC(/root/test) $DST(/home/max/git/Robot/Robot/test/test)

	@Test
	public void parsingSCPInputText() {
		String inputText = "$HOST(github.com) " + "$PORT(21) " + "$USER(root) " + "$PASSWORD(example) "
				+ "$SRC(/root/test) " + "$DST(/home/max/git/Robot/Robot/test/test)";

		Pattern pattern = CONSTANTS.BASIC_PATTERN;
		Matcher m = pattern.matcher(inputText);

		List<KeyValue> list = new ArrayList<KeyValue>();

		while (m.find()) {
			list.add(KeyValue.builder().key(m.group(1)).value(m.group(2)).build());
		}

		KeyValue host = list.stream().filter(e -> e.getKey().equals(CONSTANTS.HOST)).findFirst().orElse(null);
		KeyValue port = list.stream().filter(e -> e.getKey().equals(CONSTANTS.PORT)).findFirst().orElse(null);
		KeyValue user = list.stream().filter(e -> e.getKey().equals(CONSTANTS.USER)).findFirst().orElse(null);
		KeyValue password = list.stream().filter(e -> e.getKey().equals(CONSTANTS.PASSWORD)).findFirst().orElse(null);
		KeyValue src = list.stream().filter(e -> e.getKey().equals(CONSTANTS.SOURCE)).findFirst().orElse(null);
		KeyValue dst = list.stream().filter(e -> e.getKey().equals(CONSTANTS.DESTINATION)).findFirst().orElse(null);

		Assert.assertEquals(host.getValue(), "github.com");
		Assert.assertEquals(port.getValue(), "21");
		Assert.assertEquals(user.getValue(), "root");
		Assert.assertEquals(password.getValue(), "example");
		Assert.assertEquals(src.getValue(), "/root/test");
		Assert.assertEquals(dst.getValue(), "/home/max/git/Robot/Robot/test/test");
	}
}
