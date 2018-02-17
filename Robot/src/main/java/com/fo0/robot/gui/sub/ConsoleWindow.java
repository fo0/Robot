package com.fo0.robot.gui.sub;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.fo0.robot.controller.ControllerChain;
import com.fo0.robot.enums.EActionType;
import com.fo0.robot.gui.main.MainGUI;
import com.fo0.robot.model.ActionItem;
import java.awt.CardLayout;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class ConsoleWindow {

	private JFrame frame;
	private JTextArea console;

	/**
	 * Create the application.
	 */
	public ConsoleWindow() {
		initialize();
		frame.setVisible(false);
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Console");
		frame.setBounds(100, 100, 541, 410);

		// center frame on screen
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().setLayout(new CardLayout(0, 0));

		console = new JTextArea();
		console.setEditable(false);
		console.setFont(new Font("Dialog", Font.BOLD, 14));
		frame.getContentPane().add(console, "name_44863924045065");

		new Thread(() -> {
			ControllerChain.getChain().addCmdListener((ctx, e) -> {
				appendToConsole(String.format("ID: %s :: Action: %s - State: %s ", e.getKey().getId(),
						e.getKey().getName(), e.getValue().getData().getState().getCmd()));
			});
		}).start();
	}

	public void appendToConsole(String text) {
		console.setEditable(true);
		console.append(text + "\n");
		console.setEditable(false);
		console.validate();
		// console.repaint();
	}
}
