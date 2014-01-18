package views;

import javax.swing.JPanel;

public abstract class BasePanel extends JPanel {
	protected MainFrame main;
	
	public BasePanel(MainFrame main) {
		this.main = main;
	}
}
