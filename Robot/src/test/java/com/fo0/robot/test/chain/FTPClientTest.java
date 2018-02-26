package com.fo0.robot.test.chain;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.fo0.robot.connector.FTPClient;
import com.fo0.robot.model.FileTransferData;
import com.fo0.robot.model.Host;

public class FTPClientTest {

	public static String FTP_ADDRESS = "speedtest.tele2.net";
	public static String FTP_FILE_NAME = "1MB.zip";

	@Test
	public void downloadFTPFile() {
		FTPClient client = new FTPClient(
				Host.builder().address(FTP_ADDRESS).port(21).username("anonymous").password("").build());

		boolean connected = client.connect();
		System.out.println("connected: " + connected);
		Assert.assertEquals(true, connected);

		FileTransferData f = client.download("test/1MB.zip", FTP_FILE_NAME);

		Assert.assertNotNull(f);
		Assert.assertEquals(FTP_FILE_NAME, new File(f.getLocalPath()).getName());
		Assert.assertEquals(1048576, f.getSize());

		new File(f.getLocalPath()).deleteOnExit();
	}

}
