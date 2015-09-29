package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
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
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;

import constanst.Action;
import constanst.Algorithm;
import constanst.Mode;
import constanst.Status;
import model.FileInfo;
import model.FolderInfo;
import model.component.MyTableFiles;
import model.component.MyTableFolders;
import utils.Factory;


public class MainFrame {
	
	/****************************************
	 *                                       *
	 *        --- FUNCTIONAL COMPONENTS --- 
	 *                                       *
	 ****************************************/
	@SuppressWarnings("unused")
	private Mode mode;
	private Status status;
	int percent=0;
	
	/****************************************
	 *                                       *
	 *        --- INTERFACE COMPONENTS --- 
	 *                                       *
	 ****************************************/
	private JFrame frame;
	private JRadioButtonMenuItem rdbtnmntmEncryption;
	private JRadioButtonMenuItem rdbtnmntmDecryption;
	private MyTableFiles tableFile;
	private MyTableFolders tableFolder;
	JButton btnStartProcess;
	JButton btnStopProcess;
	
	JMenu mnMode;
	JMenu mnOpenFile;
	private JTabbedPane tabbedPane;
	private JLayeredPane layeredBasic;
	private JLayeredPane layeredAdvanced;
	private JScrollPane scrollPaneFiles;
	Timer timer; // Test
	
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
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
							File files[] = fileChooser.getSelectedFiles();
						}
						else {
							System.out.println("File access cancelled by user.");
						}
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
				changeMode(Mode.ENCRYPTION);
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
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 884, 409);
		frame.getContentPane().add(tabbedPane);
		
		layeredBasic = new JLayeredPane();
		tabbedPane.addTab("Basic", null, layeredBasic, null);
		layeredBasic.setLayout(null);
		
		scrollPaneFiles = new JScrollPane();
		scrollPaneFiles.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneFiles.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneFiles.setBounds(200, 48, 690, 333);
		layeredBasic.add(scrollPaneFiles);
		
		// TABLE
		List<FileInfo> files = new ArrayList<FileInfo>();
		files.add(new FileInfo("C:/abc.txt", 400, 0, true));
		files.add(new FileInfo("D:/das/dsa/abc.txt", 40000, 0, true));
		files.add(new FileInfo("C:/abc.txt", 400, 56, true));
		files.add(new FileInfo("D:/das/dsa/abc.txt", 40000, 0, true));
		files.add(new FileInfo("C:/abc.txt", 400, 56, true));
		files.add(new FileInfo("D:/das/dsa/abc.txt", 40000, 0, true));
		files.add(new FileInfo("C:/abc.txt", 400, 56, true));
		files.add(new FileInfo("D:/das/dsa/abc.txt", 40000, 0, true));
		files.add(new FileInfo("C:/abc.txt", 400, 56, true));
		files.add(new FileInfo("D:/das/dsa/abc.txt", 40000, 0, true));
		files.add(new FileInfo("C:/abc.txt", 400, 56, true));
		files.add(new FileInfo("D:/das/dsa/abc.txt", 40000, 86, true));
		
		tableFile = new MyTableFiles(files);
	    scrollPaneFiles.setViewportView(tableFile.getTable());
	    
	    
	    
	    JToolBar toolBar = new JToolBar();
	    toolBar.setBounds(200, 0, 679, 59);
	    layeredBasic.add(toolBar);
	    
	    btnStartProcess = new JButton("Start");
	    btnStartProcess.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 30));
	    btnStartProcess.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		if (status == Status.STOP)
	    			changeStatus(Action.START);
	    		else if (status == Status.RUNNING)
	    			changeStatus(Action.PAUSE);
	    		else if (status == Status.PAUSE)
	    			changeStatus(Action.RESUME);
	    	}
	    });
	    btnStartProcess.setIcon(Factory.getImageIcon("toolbar/start_process"));
	    toolBar.add(btnStartProcess);
	    
	    btnStopProcess = new JButton("Stop");
	    btnStopProcess.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		changeStatus(Action.STOP);
	    	}
	    });
	    btnStopProcess.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 30));
	    btnStopProcess.setIcon(Factory.getImageIcon("toolbar/stop_process"));
	    toolBar.add(btnStopProcess);
	    
	    JRadioButton rdbtnRsa = new JRadioButton("RSA");
	    rdbtnRsa.setBounds(20, 59, 109, 23);
	    layeredBasic.add(rdbtnRsa);
	    
	    JRadioButton rdbtnSdes = new JRadioButton("S-DES");
	    rdbtnSdes.setBounds(20, 86, 109, 23);
	    layeredBasic.add(rdbtnSdes);
	    
	    JRadioButton rdbtnUnkown = new JRadioButton("Unkown");
	    rdbtnUnkown.setBounds(20, 112, 109, 23);
	    layeredBasic.add(rdbtnUnkown);
	    
	    layeredAdvanced = new JLayeredPane();
	    tabbedPane.addTab("Advanced", null, layeredAdvanced, null);
	    
	    JScrollPane scrollPaneFolders = new JScrollPane();
	    scrollPaneFolders.setBounds(10, 25, 859, 333);
	    layeredAdvanced.add(scrollPaneFolders);
	    
	    List<FolderInfo> folders = new ArrayList<FolderInfo>();
	    folders.add(new FolderInfo("C:/abc.txt", 400, Algorithm.DSA));
	    folders.add(new FolderInfo("C:/abc.txt", 400, Algorithm.DSA));
	    folders.add(new FolderInfo("C:/abc.txt", 400, Algorithm.DSA));
	    folders.add(new FolderInfo("C:/abc.txt", 400, Algorithm.DSA));
	    folders.add(new FolderInfo("C:/abc.txt", 400, Algorithm.DSA));
	    
		tableFolder = new MyTableFolders(folders);
		scrollPaneFolders.setViewportView(tableFolder.getTable());
	}
	
	/**
	 * Init all functional components.
	 */
	public void initNonInterface() {
		this.mode = Mode.ENCRYPTION;
		rdbtnmntmEncryption.setSelected(true);
		rdbtnmntmDecryption.setSelected(false);
		
		this.status = Status.STOP;
		this.btnStopProcess.setEnabled(false);
	}
	
	private void changeMode(Mode mode) {
		if (mode == Mode.ENCRYPTION) {
			mode = Mode.ENCRYPTION;
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

	private void changeStatus(Action action) {
		if (action == Action.STOP) {
			btnStartProcess.setIcon(Factory.getImageIcon("toolbar/start_process"));
    		btnStartProcess.setText("Start");
    		btnStopProcess.setEnabled(false);
    		this.status = Status.STOP;
		}
		else if (action == Action.PAUSE) {
    		btnStartProcess.setIcon(Factory.getImageIcon("toolbar/start_process"));
    		btnStartProcess.setText("Resume");
    		this.status = Status.PAUSE;
    		timer.cancel();
		}
		else if (action == Action.RESUME) {
    		btnStartProcess.setIcon(Factory.getImageIcon("toolbar/pause_process"));
    		btnStartProcess.setText("Pause");
    		status = Status.RUNNING;
			
		}
		else if (action == Action.START) {
			btnStartProcess.setIcon(Factory.getImageIcon("toolbar/pause_process"));
    		btnStartProcess.setText("Pause");
    		status = Status.RUNNING;
    		btnStopProcess.setEnabled(true);
    		timer = new Timer();
    		timer.schedule(new TimerTask() {
    			
    			@Override
    			public void run() {
    				percent++;
    				tableFile.updateProgress(0, percent);
    				
    			}
    		}, 500, 100);
		}
	}
}
