package com.fo0.robot.gui.sub;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.fo0.robot.enums.EActionType;
import com.fo0.robot.gui.main.MainGUI;
import com.fo0.robot.model.ActionItem;
import javax.swing.JPanel;
import java.awt.CardLayout;

public class AddChainItemWindow {

	private JFrame frame;
	private JTextArea txtAction;

	private ActionItem item;
	private JLabel lblIDValue;
	private JComboBox<EActionType> cbType;
	private JButton btnSave;
	private JButton btnDiscard;
	private JLabel lblDescription;
	private JTextField txtDescription;

	/**
	 * Create the application.
	 */
	public AddChainItemWindow(ActionItem item) {
		if (item == null)
			item = ActionItem.builder().build();
		else
			this.item = item;

		initialize();
		frame.setVisible(true);
	}

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
		frame.setBounds(100, 100, 538, 309);

		// center frame on screen
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().setLayout(null);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setResizable(false);

		JLabel lblType = new JLabel("Type");
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblType.setFont(new Font("Dialog", Font.BOLD, 16));
		lblType.setBounds(1, 55, 113, 29);
		frame.getContentPane().add(lblType);

		JLabel lblAction = new JLabel("Action");
		lblAction.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAction.setFont(new Font("Dialog", Font.BOLD, 16));
		lblAction.setBounds(1, 133, 113, 29);
		frame.getContentPane().add(lblAction);

		cbType = new JComboBox<EActionType>();
		cbType.setModel(new DefaultComboBoxModel(EActionType.values()));
		cbType.setBounds(126, 57, 192, 29);
		cbType.addItem(item.getType());
		frame.getContentPane().add(cbType);

		btnSave = new JButton("Save");
		btnSave.setIcon(
				new ImageIcon(AddChainItemWindow.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.addItem(
						ActionItem.builder().id(lblIDValue.getText()).type((EActionType) cbType.getSelectedItem())
								.value(txtAction.getText()).description(txtDescription.getText()).build());
				frame.setVisible(false);
				frame.dispose();
			}
		});

		btnSave.setBounds(126, 241, 105, 29);
		frame.getContentPane().add(btnSave);

		btnDiscard = new JButton("Discard");
		btnDiscard.setIcon(null);
		btnDiscard.setBounds(237, 241, 105, 29);
		frame.getContentPane().add(btnDiscard);

		JLabel lblID = new JLabel("ID");
		lblID.setHorizontalAlignment(SwingConstants.RIGHT);
		lblID.setFont(new Font("Dialog", Font.BOLD, 16));
		lblID.setBounds(1, 13, 113, 29);
		frame.getContentPane().add(lblID);

		lblIDValue = new JLabel("");
		lblIDValue.setText(item.getId());
		lblIDValue.setHorizontalAlignment(SwingConstants.LEFT);
		lblIDValue.setFont(new Font("Dialog", Font.BOLD, 16));
		lblIDValue.setBounds(126, 12, 366, 29);

		frame.getContentPane().add(lblIDValue);

		lblDescription = new JLabel("Description");
		lblDescription.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescription.setFont(new Font("Dialog", Font.BOLD, 16));
		lblDescription.setBounds(1, 96, 113, 29);
		frame.getContentPane().add(lblDescription);

		txtDescription = new JTextField();
		txtDescription.setText(item.getDescription());
		txtDescription.setColumns(10);
		txtDescription.setBounds(126, 96, 394, 29);
		frame.getContentPane().add(txtDescription);

		JPanel panelTxtArea = new JPanel();
		panelTxtArea.setBounds(126, 133, 394, 96);
		frame.getContentPane().add(panelTxtArea);
		panelTxtArea.setLayout(new CardLayout(0, 0));

		txtAction = new JTextArea();
		txtAction.setWrapStyleWord(true);
		txtAction.setLineWrap(true);
		txtAction.setText(item.getValue());
		txtAction.setColumns(10);
		JScrollPane scrollPane = new JScrollPane(txtAction);
		panelTxtArea.add(scrollPane, "name_7623088305357");

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				txtAction.requestFocus();
			}
		});
	}
}
