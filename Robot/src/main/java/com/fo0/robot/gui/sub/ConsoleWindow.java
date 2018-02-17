package com.fo0.robot.gui.sub;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.fo0.robot.controller.ControllerChain;

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
		frame.setBounds(100, 100, 675, 413);

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
				appendToConsole(String.valueOf(e.getKey().getId()), e.getKey().getName(), e.getKey().getDescription(),
						e.getValue().getData().getState().getCmd().name());
			});
		}).start();
	}

	public void appendToConsole(String id, String name, String description, String state) {
		console.setEditable(true);
		console.append(
				String.format("ID: %s [%s]\n   Type: %s\n   Description: %s\n", id, state, name, description) + "\n");
		console.setEditable(false);
		console.validate();
	}

	public void appendToConsole(String text) {
		console.setEditable(true);
		console.append(text + "\n");
		console.setEditable(false);
		console.validate();
	}
}
