package com.fo0.robot.test.chain;

import java.io.File;

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
	
	@Test
	public void parseFolderPathFromFilePath() {
		File f = new File("files/demo-cli.gif");
		System.out.println(f.getPath());
		System.out.println(f.getParentFile().getPath());
		Assert.assertEquals("files", f.getParentFile().getPath());
	}

}
