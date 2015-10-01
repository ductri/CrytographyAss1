package model.component;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ProgressRender extends JProgressBar implements TableCellRenderer{
	private static final long serialVersionUID = 1L;

	public ProgressRender(Color foreColor) {
		setOpaque(true);
		setBackground(Color.WHITE);
		setForeground(foreColor);
		setStringPainted(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
			setValue(Integer.parseInt(value.toString()));
		    return this;
	}
	
}
