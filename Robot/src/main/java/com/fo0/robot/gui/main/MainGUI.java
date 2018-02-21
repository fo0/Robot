package com.fo0.robot.gui.main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.fo0.robot.controller.ControllerChain;
import com.fo0.robot.gui.sub.AddChainItemWindow;
import com.fo0.robot.gui.sub.UpdateWindow;
import com.fo0.robot.model.ActionItem;
import com.fo0.robot.model.BeanTableModelAction;
import com.fo0.robot.utils.CONSTANTS;
import com.fo0.robot.utils.Logger;

public class MainGUI {

	private static MainGUI window = null;
	private static JFrame frame;

	private static BeanTableModelAction tableModel;
	private static JTable actionTable;
	private static EMode currentMode = EMode.Normal;
	private static JTextArea areaChain;
	private static JTextArea areaConsole;

	/**
	 * Launch the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public static void bootstrap() {
		window = new MainGUI();
		initialize();
		MainGUI.frame.setVisible(true);
	}

	public static void toggleConsole(EMode mode) {
		currentMode = mode;

		switch (mode) {
		case Normal:
			frame.setSize(655, 339);
			break;

		case Console:
			frame.setSize(655, 528);
			break;
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		// create main frame
		frame = new JFrame();
		frame.setTitle("Robot v" + CONSTANTS.VERSION);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// center frame on screen
		frame.setSize(655, 528);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setResizable(false);

		toggleConsole(currentMode);

		JPanel panelTop = new JPanel();
		panelTop.setBounds(0, 0, 653, 25);
		frame.getContentPane().add(panelTop);
		panelTop.setLayout(null);

		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddChainItemWindow();
			}
		});

		btnAdd.setBounds(97, 0, 73, 24);
		panelTop.add(btnAdd);

		JButton btnDel = new JButton("DEL");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int col = actionTable.getSelectedRow();
				tableModel.removeRow(tableModel.getRow(col));
			}
		});
		btnDel.setBounds(169, 0, 73, 24);
		panelTop.add(btnDel);

		JButton btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleConsole(EMode.Console);
				ControllerChain.getChain().start();
			}
		});
		btnStart.setBounds(0, 0, 98, 24);
		panelTop.add(btnStart);

		JButton buttonToggleConsole = new JButton(">_");
		buttonToggleConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentMode == EMode.Console) {
					toggleConsole(EMode.Normal);
				} else {
					toggleConsole(EMode.Console);
				}
			}
		});
		buttonToggleConsole.setBounds(580, 0, 73, 24);
		panelTop.add(buttonToggleConsole);

		JPanel panelTable = new JPanel();
		panelTable.setBounds(0, 26, 653, 265);
		frame.getContentPane().add(panelTable);

		panelTable.setLayout(new CardLayout(0, 0));

		actionTable = new JTable();
		actionTable.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable table = (JTable) e.getSource();
					Point point = e.getPoint();
					int row = table.rowAtPoint(point);
					ActionItem item = tableModel.getRow(row);
					new AddChainItemWindow(item);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		tableModel = new BeanTableModelAction();
		actionTable.setModel(tableModel);
		JScrollPane scrollPaneTable = new JScrollPane(actionTable);
		panelTable.add(scrollPaneTable, "name_7985051461163");

		JPanel panelChain = new JPanel();
		panelChain.setBounds(0, 292, 248, 188);
		frame.getContentPane().add(panelChain);
		panelChain.setLayout(new CardLayout(0, 0));

		areaChain = new JTextArea();
		areaChain.setEditable(false);
		JScrollPane scrollPaneChain = new JScrollPane(areaChain);
		panelChain.add(scrollPaneChain, "name_1466312782685");

		JPanel panelConsole = new JPanel();
		panelConsole.setBounds(248, 292, 405, 188);
		frame.getContentPane().add(panelConsole);
		panelConsole.setLayout(new CardLayout(0, 0));

		JScrollPane scrollPaneConsole = new JScrollPane((Component) null);
		panelConsole.add(scrollPaneConsole, "name_2349855873542");

		areaConsole = new JTextArea();
		areaConsole.setEditable(false);
		scrollPaneConsole.setViewportView(areaConsole);

		// adding listener to receive events from backend
		ControllerChain.getChain().addCmdListener((ctx, e) -> {
			appendToChain(String.valueOf(e.getKey().getId()), e.getKey().getName(), e.getKey().getDescription(),
					e.getValue().getData().getState().getCmd().name());
		});

		// add listener for: Console output log
		ControllerChain.getChain().getContext().addOutputListener(cli -> {
			appendToConsole(cli);
		});

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnConfig = new JMenu("Config");
		menuBar.add(mnConfig);

		JMenuItem mntmConfigLoad = new JMenuItem("Load");
		mntmConfigLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Robot", "robot");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();

					// load config from disk -> file
					ControllerChain.getChain().getContext().load(file.getAbsolutePath());
					MainGUI.getTableModel().loadActionContextFromController();
				}

			}
		});
		mnConfig.add(mntmConfigLoad);

		JMenuItem mntmConfigSave = new JMenuItem("Save");
		mntmConfigSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Robot", "robot");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					if (!file.getName().endsWith(".robot")) {
						Logger.info("detected missing file extension, appending .robot to file");
						File tmpFile = new File(file.getAbsolutePath() + ".robot");
						file.renameTo(tmpFile);
						file = tmpFile;
					}

					// save config to disk -> file
					ControllerChain.getChain().getContext().save(file.getAbsolutePath());
				}
			}
		});
		mnConfig.add(mntmConfigSave);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmHelpAbout = new JMenuItem("About");
		mntmHelpAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnHelp.add(mntmHelpAbout);

		JMenuItem mntmHelpUpdate = new JMenuItem("Update");
		mntmHelpUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UpdateWindow();
			}
		});
		mnHelp.add(mntmHelpUpdate);
		MainGUI.refreshTable();
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

	public static BeanTableModelAction getTableModel() {
		return tableModel;
	}

	public void refreshGUI() {
		refreshTable();
	}

	enum EMode {
		Normal, Console
	}

	public static void appendToConsole(String id, String name, String description, String state) {
		appendToConsole(String.format("ID: %s [%s]\n   Type: %s\n   Description: %s\n", id, state, name, description));
	}

	public static void appendToConsole(String text) {
		areaConsole.setEditable(true);
		areaConsole.append(text);
		areaConsole.setEditable(false);
		areaConsole.validate();
	}

	public static void appendToChain(String id, String name, String description, String state) {
		appendToChain(String.format("ID: %s [%s]\n   Type: %s\n   Description: %s\n", id, state, name, description));
	}

	public static void appendToChain(String text) {
		areaChain.setEditable(true);
		areaChain.append(text + "\n");
		areaChain.setEditable(false);
		areaChain.validate();
	}
}
