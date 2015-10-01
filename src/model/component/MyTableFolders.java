package model.component;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.FolderInfo;

public class MyTableFolders {
	private JTable table;
	public MyTableFolders(List<FolderInfo> folders) {
		DefaultTableModel model = new DefaultTableModel();
		String[] headers = {"Index", "Folder", "Size", "Algorithm"};
		String[][] data = new String[folders.size()][];
		int indexRow = 0;
	
		for (int i=0;i<data.length;i++) {
			FolderInfo folderInfo = folders.get(indexRow); 
			data[i] = new String[headers.length];
			data[i][0] = Integer.toString(indexRow + 1);
			data[i][1] = folderInfo.getUrl();
			data[i][2] = folderInfo.getSize();
			data[i][3] = folderInfo.getAl();
			
			indexRow ++;
		}
		
		model.setDataVector(data, headers);
		table = new JTable(model);
		
		// Make beauty
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(40); // Index
		table.getColumnModel().getColumn(1).setPreferredWidth(300);	// File
		table.getColumnModel().getColumn(2).setPreferredWidth(70); // Size
		table.getColumnModel().getColumn(3).setPreferredWidth(150); // Algorithm
	}
	
	public JTable getTable() {
		return table;
	}
	

}
