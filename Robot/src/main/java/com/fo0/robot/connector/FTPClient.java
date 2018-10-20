package com.fo0.robot.connector;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;

import com.fo0.robot.model.FileTransferData;
import com.fo0.robot.model.Host;
import com.fo0.robot.utils.Logger;

public class FTPClient {

	private org.apache.commons.net.ftp.FTPClient ftpClient = null;
	private Host host;

	public FTPClient(Host host) {
		this.host = host;
	}

	public boolean connect() {
		try {
			// for anonymous logins
			ftpClient = new org.apache.commons.net.ftp.FTPClient();
			ftpClient.connect(host.getAddress(), host.getPort());
			ftpClient.login(host.getUsername(), host.getPassword());
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			return true;
		} catch (Exception e) {
			Logger.error("failed to connect to ftp host: " + host.info());
		}
		return false;
	}

	public FileTransferData download(String localPath, String remotePath) {
		try {
			FileTransferData ftd = FileTransferData.builder().build();
			ftd.setLocalPath(localPath);
			ftd.setRemotePath(remotePath);

			File localFile = new File(localPath);
			ftd.setStarted(System.currentTimeMillis());
			InputStream inputStream = ftpClient.retrieveFileStream(remotePath);
			FileUtils.copyInputStreamToFile(inputStream, localFile);
			ftd.setFinished(System.currentTimeMillis());

			boolean success = ftpClient.completePendingCommand();
			if (!success) {
				return ftd;
			}
			ftd.setSuccess(true);
			ftd.setSize(FileUtils.sizeOf(new File(localPath)));
			return ftd;
		} catch (Exception e) {
			Logger.error("failed to download ftp file: " + remotePath + "," + e);
		}
		return null;
	}
}
