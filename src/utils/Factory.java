package utils;

import javax.swing.ImageIcon;

public class Factory {

	public static ImageIcon getImageIcon(String keyword) {
		
		if (keyword.equals("menu/menu_item/mode_normal"))
			return new ImageIcon("resources/mode.png");
		else if (keyword.equals("menu/menu_item/mode_hover"))
			return new ImageIcon("resources/mode_hover.png");
		else if (keyword.equals("menu/menu_item/open_file_normal"))
			return new ImageIcon("resources/open_icon.png");
		else if (keyword.equals("menu/menu_item/open_file_hover"))
			return new ImageIcon("resources/open_icon_hover.png");
		
		// Toolbar icons
		else if (keyword.equals("toolbar/start_process"))
			return new ImageIcon("resources/start_process.png");
		else if (keyword.equals("toolbar/stop_process"))
			return new ImageIcon("resources/stop_process.png");
		else if (keyword.equals("toolbar/pause_process"))
			return new ImageIcon("resources/pause_process.png");
		return null;
	}
	
	

}	
