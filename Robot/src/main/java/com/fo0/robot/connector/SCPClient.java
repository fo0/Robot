package com.fo0.robot.connector;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.fo0.robot.model.Host;
import com.fo0.robot.model.FileTransferData;
import com.fo0.robot.utils.Logger;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.xfer.scp.SCPFileTransfer;

public class SCPClient {

	private SSHClient client;
	private Host host = null;

	public SCPClient(Host host) {
		this.host = host;
	}

	public void connect() throws Exception {
		client = new SSHClient();
		client.loadKnownHosts();
		client.connect(host.getAddress(), host.getPort());
		client.authPassword(host.getUsername(), host.getPassword());
	}

	public FileTransferData download(String localPath, String remotePath) {
		if (client == null) {
			return null;
		}

		FileTransferData data = FileTransferData.builder().build();
		data.setLocalPath(localPath);
		data.setRemotePath(remotePath);
		data.setStarted(System.currentTimeMillis());
		SCPFileTransfer dlClient = client.newSCPFileTransfer();
		try {
			dlClient.download(remotePath, localPath);
			data.setSuccess(true);
			data.setSize(FileUtils.sizeOf(new File(localPath)));
		} catch (Exception e) {
			Logger.error("failed to download file " + e);
		} finally {
			data.setFinished(System.currentTimeMillis());
		}

		return data;
	}

	public FileTransferData upload(String remotePath, String localPath) {
		if (client == null) {
			return null;
		}

		FileTransferData data = FileTransferData.builder().build();
		data.setLocalPath(localPath);
		data.setRemotePath(remotePath);
		data.setStarted(System.currentTimeMillis());
		SCPFileTransfer dlClient = client.newSCPFileTransfer();
		try {
			dlClient.upload(localPath, remotePath);
			data.setSuccess(true);
			data.setSize(FileUtils.sizeOf(new File(localPath)));
		} catch (Exception e) {
			Logger.error("failed to upload file " + e);
		} finally {
			data.setFinished(System.currentTimeMillis());
		}

		return data;
	}
}
