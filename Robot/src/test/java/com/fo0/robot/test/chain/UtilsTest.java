package com.fo0.robot.test.chain;

import org.junit.Assert;
import org.junit.Test;

import com.fo0.robot.utils.Utils;

public class UtilsTest {

	@Test
	public void downloadSpeedReadable() {
		long size = 159148919;
		long duration = 6497;

		String result = Utils.humanReadableBandwith(duration, size);
		Assert.assertEquals("202.36832 Mbit/s", result);
	}

}
