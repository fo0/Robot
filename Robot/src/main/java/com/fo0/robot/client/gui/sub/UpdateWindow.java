package com.fo0.robot.client.gui.sub;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.fo0.robot.client.gui.utils.DialogUtils;
import com.fo0.robot.controller.Controller;
import com.fo0.robot.main.Main;
import com.fo0.robot.update.GitHubReleaseInfo;
import com.fo0.robot.update.UpdateUtils;
import com.fo0.robot.utils.CONSTANTS;
import com.fo0.robot.utils.Utils;

public class UpdateWindow {

	private JFrame frame;

	private GitHubReleaseInfo info;

	/**
	 * Create the application.
	 */
	public UpdateWindow() {
		fetchData();
		initialize();
		frame.setVisible(true);
	}

	public void close() {
		frame.setVisible(false);
		frame.dispose();
	}

	public void fetchData() {
		info = UpdateUtils.getInfo();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Update");
		frame.setBounds(100, 100, 304, 156);

//		center frame on screen
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Your Version");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(12, 28, 106, 15);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblYourVersion = new JLabel(CONSTANTS.VERSION);
		lblYourVersion.setBounds(141, 28, 61, 15);
		frame.getContentPane().add(lblYourVersion);

		JButton btnUpdate = new JButton("Update Now!");
		btnUpdate.setBounds(12, 92, 278, 25);
		btnUpdate.setEnabled(info.isAvailable());
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateUtils.doUpdate();
				Utils.restartApplication(Main.class, Controller.arg);
			}
		});
		frame.getContentPane().add(btnUpdate);

		JLabel lbllatest = new JLabel("Latest Version");
		lbllatest.setHorizontalAlignment(SwingConstants.RIGHT);
		lbllatest.setBounds(12, 55, 106, 15);
		frame.getContentPane().add(lbllatest);

		JLabel lblLatestVersion = new JLabel(info.getVersion());
		lblLatestVersion.setBounds(141, 55, 61, 15);
		frame.getContentPane().add(lblLatestVersion);

		JButton btnNewButton = new JButton("Info");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogUtils.info("Update Notes", info.getMessage(), false, 400, 200);
			}
		});
		btnNewButton.setBounds(229, 50, 61, 25);
		frame.getContentPane().add(btnNewButton);

	}
}
