package com.fo0.robot.connector;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.fo0.robot.model.FileTransferData;
import com.fo0.robot.model.Host;
import com.fo0.robot.utils.Logger;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.xfer.scp.SCPFileTransfer;

public class SCPClient {

	private SSHClient client;
	private Host host = null;

	public SCPClient(Host host) {
		this.host = host;
	}

	public void connect() throws Exception {
		client = new SSHClient();
		try {
			client.addHostKeyVerifier(new PromiscuousVerifier());
			client.loadKnownHosts();
		} catch (Exception e) {
		}

		client.setConnectTimeout(Long.valueOf(TimeUnit.SECONDS.toMillis(5)).intValue());
		client.connect(host.getAddress(), host.getPort());

		if (StringUtils.isEmpty(host.getPassword())) {
			Logger.debug("WATCHDOG: Using Public-Key from current user");
			client.authPublickey(host.getUsername());
		} else {
			Logger.debug("WATCHDOG: Using Username/Password");
			client.authPassword(host.getUsername(), host.getPassword());
		}
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
