package com.fo0.robot.gui.sub;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.fo0.robot.chain.action.ActionContext;
import com.fo0.robot.controller.Controller;
import com.fo0.robot.controller.ControllerChain;
import com.fo0.robot.gui.main.MainGUI;
import com.fo0.robot.main.Main;
import com.fo0.robot.utils.CONSTANTS;
import com.fo0.robot.utils.Logger;
import com.fo0.robot.utils.Parser;
import com.fo0.robot.utils.UpdateUtils;
import com.fo0.robot.utils.Utils;
import com.google.gson.Gson;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class UpdateWindow {

	private JFrame frame;
	private JTextArea console;

	private String latestVersion = "";
	public boolean isAvailable;

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
		latestVersion = UpdateUtils.getVersion();
		isAvailable = UpdateUtils.isAvailable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Update");
		frame.setBounds(100, 100, 257, 156);

		// center frame on screen
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Your Version");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(12, 28, 106, 15);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblYourVersion = new JLabel(CONSTANTS.VERSION);
		lblYourVersion.setBounds(141, 28, 102, 15);
		frame.getContentPane().add(lblYourVersion);

		JButton btnUpdate = new JButton("Update Now!");
		btnUpdate.setEnabled(isAvailable);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateUtils.doUpdate();
				Utils.restartApplication(Main.class, Controller.arg);
			}
		});
		btnUpdate.setBounds(12, 92, 231, 25);
		frame.getContentPane().add(btnUpdate);

		JLabel lbllatest = new JLabel("Latest Version");
		lbllatest.setHorizontalAlignment(SwingConstants.RIGHT);
		lbllatest.setBounds(12, 55, 106, 15);
		frame.getContentPane().add(lbllatest);

		JLabel lblLatestVersion = new JLabel(latestVersion);
		lblLatestVersion.setBounds(141, 55, 102, 15);
		frame.getContentPane().add(lblLatestVersion);

	}
}
