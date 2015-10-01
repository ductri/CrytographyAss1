package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.crypto.NoSuchPaddingException;
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
import java.awt.Font;


public class MainFrame {
	
	/****************************************
	 *                                       *
	 *        --- FUNCTIONAL COMPONENTS --- 
	 *                                       *
	 ****************************************/
	@SuppressWarnings("unused")
	private Mode mode;
	private Status status;
	private Algorithm algorithm;
	int percent=0;
	List<FileInfo> files = new ArrayList<FileInfo>();
	String outputURL = "C:\\Output";
	
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
	JRadioButton rdbtnAES;
	JRadioButton rdbtnDes;
	JRadioButton rdbtnDESede;
	
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
		frame.setBounds(100, 100, 1112, 501);
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
		tabbedPane.setBounds(0, 0, 1096, 409);
		frame.getContentPane().add(tabbedPane);
		
		layeredBasic = new JLayeredPane();
		tabbedPane.addTab("Basic", null, layeredBasic, null);
		layeredBasic.setLayout(null);
		
		scrollPaneFiles = new JScrollPane();
		scrollPaneFiles.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneFiles.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneFiles.setBounds(200, 48, 891, 333);
		layeredBasic.add(scrollPaneFiles);
		
		// TABLE
		
		tableFile = new MyTableFiles(files);
	    scrollPaneFiles.setViewportView(tableFile.getTable());
	    
	    
	    
	    JToolBar toolBar = new JToolBar();
	    toolBar.setBounds(200, 0, 891, 59);
	    layeredBasic.add(toolBar);
	    
	    btnStartProcess = new JButton("Start");
	    btnStartProcess.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 30));
	    
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
	    
	    rdbtnAES = new JRadioButton("AES");
	    rdbtnAES.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent arg0) {
	    		chooseAlgorithm(Algorithm.AES);
	    	}
	    });
	    rdbtnAES.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    rdbtnAES.setBounds(14, 63, 161, 43);
	    layeredBasic.add(rdbtnAES);
	    
	    rdbtnDes = new JRadioButton("DES");
	    rdbtnDes.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent arg0) {
	    		chooseAlgorithm(Algorithm.DES);
	    	}
	    });
	    rdbtnDes.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    rdbtnDes.setBounds(13, 127, 162, 43);
	    layeredBasic.add(rdbtnDes);
	    
	    rdbtnDESede = new JRadioButton("DESede");
	    rdbtnDESede.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent arg0) {
	    		chooseAlgorithm(Algorithm.DESede);
	    	}
	    });
	    rdbtnDESede.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    rdbtnDESede.setBounds(14, 188, 166, 43);
	    layeredBasic.add(rdbtnDESede);
	    
	    layeredAdvanced = new JLayeredPane();
	    tabbedPane.addTab("Advanced", null, layeredAdvanced, null);
	    
	    JScrollPane scrollPaneFolders = new JScrollPane();
	    scrollPaneFolders.setBounds(10, 25, 859, 333);
	    layeredAdvanced.add(scrollPaneFolders);
	    
	    List<FolderInfo> folders = new ArrayList<FolderInfo>();
	    folders.add(new FolderInfo("C:/abc.txt", 400, Algorithm.DES));
	    folders.add(new FolderInfo("C:/abc.txt", 400, Algorithm.DES));
	    folders.add(new FolderInfo("C:/abc.txt", 400, Algorithm.DES));
	    folders.add(new FolderInfo("C:/abc.txt", 400, Algorithm.DES));
	    folders.add(new FolderInfo("C:/abc.txt", 400, Algorithm.DES));
	    
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
		chooseAlgorithm(Algorithm.AES);
		initFunctionalEvent();
	}
	
	private void initFunctionalEvent() {
		mnOpenFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setMultiSelectionEnabled(true);
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
							File fileSelections[] = fileChooser.getSelectedFiles();
							for (File f:fileSelections) {
								FileInfo fileInfo = new FileInfo(f.getAbsolutePath(), f.length(), 
										true, 0, 0, true);
								files.add(fileInfo);
							}
							tableFile.update();
							
						}	
						else {
							System.out.println("File access cancelled by user.");
						}
					}
				});
				t.start();
			}
		});
		
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
	int rowIndex=0;
	private void changeStatus(Action action) {
		if (files.isEmpty())
			return;
		
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
    		rowIndex = 0;
    		
    		new Thread(new Runnable() {
				
				@Override
				public void run() {
					// De tranh truong hop dang encryption/decryption mot list file ma 
					// nguoi dung la doi mode
					long start = System.currentTimeMillis();
					Mode modeClone = mode;
					int threadNo=0;
					for (FileInfo fileInfo:files) {
						ProgressFunction compressProgressFunc = new ProgressFunction(rowIndex) {
							
							@Override
							public void updateProgress(int percent) {
								tableFile.updateCompressProgress(rowIndex, percent);
								
							}
						};
						ProgressFunction cryptoProgressFunc = new ProgressFunction(rowIndex) {
							
							@Override
							public void updateProgress(int percent) {
								tableFile.updateCryptoProgress(rowIndex, percent);
								
							}
						};
						try {
							Cryptography cry = new Cryptography(algorithm, modeClone,
									fileInfo.getFileURL(), outputURL, true, compressProgressFunc, 
									cryptoProgressFunc, true);
							new Thread(cry).start();
							threadNo++;
							if (threadNo>1) {
								cry.waitDone();
								threadNo-=2;
							}
							
							rowIndex ++;
							
						} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
								| InvalidAlgorithmParameterException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					long end = System.currentTimeMillis();
					
					System.out.println("Time consuming: "+ (end - start)/1000.0 +" s");
				}
			}).start();
    		
    		
		}
	}

	private void chooseAlgorithm(Algorithm al) 
	{
		if (al == Algorithm.AES) {
			this.algorithm = Algorithm.AES;
			
			rdbtnAES.setSelected(true);
			rdbtnDes.setSelected(false);
			rdbtnDESede.setSelected(false);
		}
		else if (al == Algorithm.DES) {
			this.algorithm = Algorithm.DES;
			
			rdbtnAES.setSelected(false);
			rdbtnDes.setSelected(true);
			rdbtnDESede.setSelected(false);
		}
		else if (al == Algorithm.DESede) {
			this.algorithm = Algorithm.DESede;
			
			rdbtnAES.setSelected(false);
			rdbtnDes.setSelected(false);
			rdbtnDESede.setSelected(true);
		}
	}
}
