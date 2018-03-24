package com.fo0.robot.connector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.fo0.robot.listener.DataListener;
import com.fo0.robot.listener.InputListener;
import com.fo0.robot.model.Host;
import com.fo0.robot.utils.Logger;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

public class SimpleSSHClient {

	private Host host = null;

	private SSHClient client = null;
	private Session shell = null;

	private InputListener<Character> inputListener;
	private DataListener<String> outputListener;
	private DataListener<String> errorListener;

	public SimpleSSHClient(Host host) {
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

		shell = client.startSession();
	}

	public Session getShell() {
		return shell;
	}

	public void command(String cmd, DataListener<String> outputListener, DataListener<String> errorListener) {
		if (shell == null) {
			Logger.error("failed connection not found");
			return;
		}

		try {
			Command command = shell.exec(cmd);

			if (command == null) {
				throw new NullPointerException();
			}

			IOUtils.copy(command.getInputStream(), new OutputStream() {
				@Override
				public void write(int b) throws IOException {
					char c = (char) b;
					outputListener.event(String.valueOf(c));
				}
			});

			IOUtils.copy(command.getErrorStream(), new OutputStream() {
				@Override
				public void write(int b) throws IOException {
					char c = (char) b;
					errorListener.event(String.valueOf(c));
				}
			});

			// shell.exec(cmd, new InputStream() {
			//
			// @Override
			// public int read() throws IOException {
			// if (inputListener != null)
			// return inputListener.event();
			// else
			// return 0;
			// }
			// }, new OutputStream() {
			//
			// @Override
			// public void write(int b) throws IOException {
			// char c = (char) b;
			// // System.out.println(c);
			// outputListener.event(String.valueOf(c));
			// }
			// }, new OutputStream() {
			//
			// @Override
			// public void write(int b) throws IOException {
			// char c = (char) b;
			// // System.out.println(c);
			// errorListener.event(String.valueOf(c));
			// }
			// });
		} catch (Exception e) {
			if (outputListener != null)
				errorListener.event(e.getMessage());
		}
	}

	public void close() {
		if (shell != null) {
			try {
				shell = null;
			} catch (Exception e) {
			}
		}
	}

	public boolean test() {
		if (shell == null) {
			return false;
		}

		return true;
	}

	public InputStream getInputListener() {
		return shell.getInputStream();
	}

	public OutputStream getOutputListener() {
		return shell.getOutputStream();
	}

}
