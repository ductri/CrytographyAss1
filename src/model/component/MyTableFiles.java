package model.component;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.FileInfo;

public class MyTableFiles {
	private JTable table;
	public MyTableFiles(List<FileInfo> files) {
		DefaultTableModel model = new DefaultTableModel();
		String[] headers = {"Index", "Files", "Size", "Progress", "MD5"};
		String[][] data = new String[files.size()][];
		int indexRow = 0;
	
		for (int i=0;i<data.length;i++) {
			FileInfo fileInfo = files.get(indexRow); 
			data[i] = new String[headers.length];
			data[i][0] = Integer.toString(indexRow + 1);
			data[i][1] = fileInfo.getFileURL();
			data[i][2] = fileInfo.getFileSize();
			data[i][3] = fileInfo.getProgress();
			data[i][4] = fileInfo.getMd5();
			indexRow ++;
		}
		
		model.setDataVector(data, headers);
		table = new JTable(model);
		table.getColumn("Progress").setCellRenderer(new ProgressRender());
		
		// Make beauty
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(40); // Index
		table.getColumnModel().getColumn(1).setPreferredWidth(300);	// File
		table.getColumnModel().getColumn(2).setPreferredWidth(70); // Size
		table.getColumnModel().getColumn(3).setPreferredWidth(150); // Progress
		table.getColumnModel().getColumn(4).setPreferredWidth(70); // MD5 check
	}
	
	public JTable getTable() {
		return table;
	}
	
	public void updateProgress(int rowIndex, int percent) {
		table.getModel().setValueAt(percent, rowIndex, 3);
		
	}
}
