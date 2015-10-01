package model.component;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.FileInfo;

public class MyTableFiles {
	private JTable table;
	private List<FileInfo> files;
	String[] headers = {"Index", "Files", "Size", "Compress", "Compress progress","Encryption progress", "MD5"};
	
	boolean select= true;
	
	public MyTableFiles(List<FileInfo> files) {
		this.files = files;
		
		DefaultTableModel model = new MyTableModel();
		
		String[][] data = new String[files.size()][];
		int indexRow = 0;
	
		for (int i=0;i<data.length;i++) {
			FileInfo fileInfo = files.get(indexRow); 
			data[i] = new String[headers.length];
			data[i][0] = Integer.toString(indexRow + 1);
			data[i][1] = fileInfo.getFileURL();
			data[i][2] = fileInfo.getFileSize();
			data[i][3] = fileInfo.isCompress();
			data[i][4] = fileInfo.getCompressProgress();
			data[i][5] = fileInfo.getProgress();
			data[i][6] = fileInfo.getMd5();
			indexRow ++;
		}
		
		model.setDataVector(data, headers);
		
		table = new JTable(model);
		table.getColumn("Compress").setCellRenderer(new CheckboxRender());
		
		table.getColumn("Encryption progress").setCellRenderer(new ProgressRender(Color.BLACK));
		table.getColumn("Compress progress").setCellRenderer(new ProgressRender(Color.RED));
		// Make beauty
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(40); // Index
		table.getColumnModel().getColumn(1).setPreferredWidth(300);	// File
		table.getColumnModel().getColumn(2).setPreferredWidth(70); // Size
		table.getColumnModel().getColumn(3).setPreferredWidth(100); // Compress boolean
		table.getColumnModel().getColumn(4).setPreferredWidth(150); // Compress progress
		table.getColumnModel().getColumn(5).setPreferredWidth(150); // Encryption progress
		table.getColumnModel().getColumn(6).setPreferredWidth(70); // MD5
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter() {
			@Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    
                }
            }
			
			@Override
			public void mouseClicked(MouseEvent e) {
			    if (e.getClickCount() == 1) {
			      JTable target = (JTable)e.getSource();
			      int row = target.getSelectedRow();
			      int column = target.getSelectedColumn();
			      select = !select;
			      table.getModel().setValueAt(select, row, column);
			    }
			  }
		});
	}
	
	public JTable getTable() {
		return table;
	}
	
	public void updateCryptoProgress(int rowIndex, int percent) {
		System.out.println("rowINdex="+rowIndex);
		table.getModel().setValueAt(percent, rowIndex, 5);		
	}
	
	public void updateCompressProgress(int rowIndex, int percent) {
		System.out.println("rowINdex="+rowIndex);
		table.getModel().setValueAt(percent, rowIndex, 4);		
	}
	
	public void update() {
		DefaultTableModel model = new MyTableModel();
		
		String[][] data = new String[files.size()][];
		int indexRow = 0;
	
		for (int i=0;i<data.length;i++) {
			FileInfo fileInfo = files.get(indexRow); 
			data[i] = new String[headers.length];
			data[i][0] = Integer.toString(indexRow + 1);
			data[i][1] = fileInfo.getFileURL();
			data[i][2] = fileInfo.getFileSize();
			data[i][3] = fileInfo.isCompress();
			data[i][4] = fileInfo.getCompressProgress();
			data[i][5] = fileInfo.getProgress();
			data[i][6] = fileInfo.getMd5();
			indexRow ++;
		}
		
		model.setDataVector(data, headers);
		table.setModel(model);
		table.getColumn("Compress").setCellRenderer(new CheckboxRender());
		
		
		table.getColumn("Encryption progress").setCellRenderer(new ProgressRender(Color.BLACK));
		table.getColumn("Compress progress").setCellRenderer(new ProgressRender(Color.RED));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(40); // Index
		table.getColumnModel().getColumn(0).setResizable(false);
		
		table.getColumnModel().getColumn(1).setPreferredWidth(300);	// File
		
		table.getColumnModel().getColumn(2).setPreferredWidth(70); // Size
		table.getColumnModel().getColumn(2).setResizable(false);
		
		table.getColumnModel().getColumn(3).setPreferredWidth(100); // Compress boolean
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setPreferredWidth(150); // Compress progress
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(5).setPreferredWidth(150); // Encryption progress
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(6).setPreferredWidth(70); // MD5
		table.getColumnModel().getColumn(6).setResizable(false);
		
		table.getTableHeader().setReorderingAllowed(false);
		table.updateUI();
		
	}
}
