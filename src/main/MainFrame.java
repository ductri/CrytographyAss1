package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JViewport;

import model.Mode;
import utils.Factory;


public class MainFrame {
	
	/****************************************
	 *                                       *
	 *        --- FUNCTIONAL COMPONENTS --- 
	 *                                       *
	 ****************************************/
	@SuppressWarnings("unused")
	private Mode mode;
	private String[][] data = {{"1", "Nguyen Duc Tri", "done", "100%", "OK"}, {"2", "Nguyen Thi Thuy Tien", "done", "100%", "OK"}};
	private String[] headers = {"id", "File name","status", "progress", "MD5"};
	/****************************************
	 *                                       *
	 *        --- INTERFACE COMPONENTS --- 
	 *                                       *
	 ****************************************/
	private JFrame frame;
	private JRadioButtonMenuItem rdbtnmntmEncryption;
	private JRadioButtonMenuItem rdbtnmntmDecryption;
	private JTable table;
	
	
	JMenu mnMode;
	JMenu mnOpenFile;
	private JTabbedPane tabbedPane;
	private JLayeredPane layeredBasic;
	private JLayeredPane layeredAdvanced;
	private JButton btnCheckMd;
	private JButton btnStart;
	private JScrollPane scrollPaneFiles;
	
	public MainFrame() {
		initInterface();
		initNonInterface();
	}

	/**
	 * Init all components relates interface.
	 */
	private void initInterface() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 900, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/****************************************
		 *                                       *
		 *        --- MENU --- 
		 *                                       *
		 ****************************************/
		// Menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.DARK_GRAY);
		frame.setJMenuBar(menuBar);
		
		mnOpenFile = new JMenu();
		ImageIcon mnOpenFileIcon = Factory.getImageIcon("menu/menu_item/open_file_normal");
		mnOpenFile.setIcon(mnOpenFileIcon);
		mnOpenFile.setBackground(Color.DARK_GRAY);
		mnOpenFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				mnOpenFile.setIcon(Factory.getImageIcon("menu/menu_item/open_file_hover"));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mnOpenFile.setIcon(Factory.getImageIcon("menu/menu_item/open_file_normal"));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						System.out.println(fileChooser.showOpenDialog(frame));
					}
				});
				t.start();
			}
		});
		menuBar.add(mnOpenFile);
		
		Component box = Box.createHorizontalGlue();
		box.setPreferredSize(new Dimension(100, 50));
		menuBar.add(box);
		
		// Menu item
		mnMode = new JMenu("<html><p style='margin-bottom:20px></html>");
		mnMode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				mnMode.setIcon(Factory.getImageIcon("menu/menu_item/mode_hover"));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mnMode.setIcon(Factory.getImageIcon("menu/menu_item/mode_normal"));
			}
		});
		mnMode.setBackground(Color.DARK_GRAY);
		ImageIcon mnModeIcon = Factory.getImageIcon("menu/menu_item/mode_normal");
		mnMode.setIcon(mnModeIcon);
		menuBar.add(mnMode);
	
		rdbtnmntmEncryption = new JRadioButtonMenuItem("Encrytion");
		rdbtnmntmEncryption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeMode(Mode.ENCRYTION);
			}
		});
		mnMode.add(rdbtnmntmEncryption);
		
		rdbtnmntmDecryption = new JRadioButtonMenuItem("Decryption");
		rdbtnmntmDecryption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeMode(Mode.DECRYPTION);
			}
		});
		mnMode.add(rdbtnmntmDecryption);
		frame.getContentPane().setLayout(null);
		
		JRadioButton rdbtnRsa = new JRadioButton("RSA");
		rdbtnRsa.setBounds(0, 85, 109, 23);
		frame.getContentPane().add(rdbtnRsa);
		
		JRadioButton rdbtnSdes = new JRadioButton("S-DES");
		rdbtnSdes.setBounds(0, 112, 109, 23);
		frame.getContentPane().add(rdbtnSdes);
		
		JRadioButton rdbtnUnkown = new JRadioButton("Unkown");
		rdbtnUnkown.setBounds(0, 138, 109, 23);
		frame.getContentPane().add(rdbtnUnkown);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 884, 409);
		frame.getContentPane().add(tabbedPane);
		
		layeredBasic = new JLayeredPane();
		tabbedPane.addTab("Basic", null, layeredBasic, null);
		layeredBasic.setLayout(null);
		
		scrollPaneFiles = new JScrollPane();
		scrollPaneFiles.setBounds(199, 0, 690, 381);
		layeredBasic.add(scrollPaneFiles);
		table=new JTable(data,headers);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(40);
		
	    scrollPaneFiles.setViewportView(table);
	    
	    btnStart = new JButton("Start");
	    btnStart.setBounds(0, 324, 200, 23);
	    layeredBasic.add(btnStart);
	    
	    btnCheckMd = new JButton("Check MD5");
	    btnCheckMd.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    	}
	    });
	    btnCheckMd.setBounds(0, 275, 200, 23);
	    layeredBasic.add(btnCheckMd);
	    
	    layeredAdvanced = new JLayeredPane();
	    tabbedPane.addTab("Advanced", null, layeredAdvanced, null);
		
	}
	
	/**
	 * Init all functional components.
	 */
	public void initNonInterface() {
		this.mode = Mode.ENCRYTION;
		rdbtnmntmEncryption.setSelected(true);
		rdbtnmntmDecryption.setSelected(false);
	}
	
	private void changeMode(Mode mode) {
		if (mode == Mode.ENCRYTION) {
			mode = Mode.ENCRYTION;
			rdbtnmntmEncryption.setSelected(true);
			rdbtnmntmDecryption.setSelected(false);
			this.mode = mode;
		}
		else {
			mode = Mode.DECRYPTION;
			rdbtnmntmEncryption.setSelected(false);
			rdbtnmntmDecryption.setSelected(true);
			this.mode = mode;
		}
	}
}
