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

import com.fo0.robot.enums.EActionType;
import com.fo0.robot.gui.main.MainGUI;
import com.fo0.robot.model.ActionItem;

public class AddChainItemWindow {

	private JFrame frame;
	private JTextField txtAction;

	private ActionItem item;
	private JLabel lblIDValue;
	private JComboBox<EActionType> cbType;
	private JButton btnSave;
	private JButton btnDiscard;

	// /**
	// * Create the application.
	// */
	// public AddChainItemWindow(ActionItem item) {
	// if (item == null)
	// item = ActionItem.builder().build();
	// else
	// this.item = item;
	//
	// initialize();
	// frame.setVisible(true);
	// }

	/**
	 * Create the application.
	 */
	public AddChainItemWindow() {
		item = ActionItem.builder().build();

		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("ChainItem");
		frame.setBounds(100, 100, 502, 202);

		// center frame on screen
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().setLayout(null);

		JLabel lblType = new JLabel("Type");
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblType.setFont(new Font("Dialog", Font.BOLD, 16));
		lblType.setBounds(12, 53, 75, 29);
		frame.getContentPane().add(lblType);

		txtAction = new JTextField();
		txtAction.setText(item.getValue());
		txtAction.setColumns(10);
		txtAction.setBounds(105, 96, 366, 29);

		frame.getContentPane().add(txtAction);

		JLabel lblAction = new JLabel("Action");
		lblAction.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAction.setFont(new Font("Dialog", Font.BOLD, 16));
		lblAction.setBounds(12, 95, 75, 29);
		frame.getContentPane().add(lblAction);

		cbType = new JComboBox<EActionType>();
		cbType.setModel(new DefaultComboBoxModel(EActionType.values()));
		cbType.setBounds(105, 56, 192, 29);
		cbType.addItem(item.getType());
		frame.getContentPane().add(cbType);

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.addItem(ActionItem.builder().id(lblIDValue.getText())
						.type((EActionType) cbType.getSelectedItem()).value(txtAction.getText()).build());
				frame.setVisible(false); // you can't see me!
				frame.dispose(); // Destroy the JFrame object
			}
		});
		btnSave.setBounds(105, 134, 105, 29);
		frame.getContentPane().add(btnSave);

		btnDiscard = new JButton("Discard");
		btnDiscard.setBounds(216, 134, 105, 29);
		frame.getContentPane().add(btnDiscard);

		JLabel lblID = new JLabel("ID");
		lblID.setHorizontalAlignment(SwingConstants.RIGHT);
		lblID.setFont(new Font("Dialog", Font.BOLD, 16));
		lblID.setBounds(12, 11, 75, 29);
		frame.getContentPane().add(lblID);

		lblIDValue = new JLabel("");
		lblIDValue.setText(item.getId());
		lblIDValue.setHorizontalAlignment(SwingConstants.LEFT);
		lblIDValue.setFont(new Font("Dialog", Font.BOLD, 16));
		lblIDValue.setBounds(105, 11, 366, 29);
		frame.getContentPane().add(lblIDValue);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setResizable(false);
	}
}
