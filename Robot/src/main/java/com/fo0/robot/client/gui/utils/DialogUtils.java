package com.fo0.robot.client.gui.utils;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DialogUtils {

	public static JDialog info(String caption, String text, boolean modal, int width, int height) {
		JDialog dialog = new JDialog();
		dialog.setTitle(caption);
		dialog.setSize(width, height);
		dialog.setModal(modal);

		dialog.setLocationRelativeTo(null);

		JTextArea area = new JTextArea(text);
		JScrollPane scrollPaneChain = new JScrollPane(area);
		area.setEditable(false);
		dialog.getContentPane().add(scrollPaneChain);
		dialog.setVisible(true);
		return dialog;
	}

}
