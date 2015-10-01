package model.component;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CheckboxRender extends JCheckBox implements TableCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CheckboxRender() {
		setOpaque(true);
		setBackground(Color.WHITE);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		setSelected((value.toString().equals("true")));//
		return this;
	}
	
	
}
