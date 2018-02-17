package com.fo0.robot.gui.main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.fo0.robot.gui.sub.AddChainItemWindow;
import com.fo0.robot.model.ActionItem;
import com.fo0.robot.model.BeanTableModel;

public class MainGUI {

	private static MainGUI window = null;
	private static JFrame frame;

	private static BeanTableModel<ActionItem> tableModel;
	private static JTable actionTable;

	/**
	 * Launch the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public static void bootstrap() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MainGUI();
					initialize();
					MainGUI.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		// create main frame
		frame = new JFrame();
		frame.setTitle("TheRobot");
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 497, 318);

		// center frame on screen
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setResizable(false);

		JPanel panelTop = new JPanel();
		panelTop.setBounds(0, 0, 495, 25);
		frame.getContentPane().add(panelTop);
		panelTop.setLayout(null);

		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddChainItemWindow window = new AddChainItemWindow();
			}
		});

		btnAdd.setBounds(0, 0, 73, 24);
		panelTop.add(btnAdd);

		JButton btnDel = new JButton("DEL");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int col = actionTable.getSelectedRow();
				tableModel.removeRow(tableModel.getRow(col));
			}
		});
		btnDel.setBounds(422, 0, 73, 24);
		panelTop.add(btnDel);

		JButton btnUp = new JButton("UP");
		btnUp.setBounds(85, 0, 73, 24);
		panelTop.add(btnUp);

		JButton btnDown = new JButton("DOWN");
		btnDown.setBounds(158, 0, 79, 24);
		panelTop.add(btnDown);

		JPanel panelTable = new JPanel();
		panelTable.setBounds(0, 26, 495, 265);
		frame.getContentPane().add(panelTable);

		panelTable.setLayout(new CardLayout(0, 0));

		actionTable = new JTable();
		tableModel = createTableModel();
		actionTable.setModel(tableModel);
		JScrollPane scrollPane = new JScrollPane(actionTable);
		panelTable.add(scrollPane, "name_7985051461163");
	}

	private static BeanTableModel<ActionItem> createTableModel() {
		BeanTableModel<ActionItem> tableModel = new BeanTableModel<ActionItem>(ActionItem.class);
		tableModel.addColumn("Type", "type");
		tableModel.addColumn("Value", "value");
		return tableModel;
	}

	public static void addItem(ActionItem action) {
		tableModel.addRow(action);
	}

	public static void refreshTable() {
		tableModel.fireTableDataChanged();
	}

	public static void deleteItem(ActionItem action) {
		tableModel.removeRow(action);
	}

}
