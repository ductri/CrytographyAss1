package model.component;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	
	
}
