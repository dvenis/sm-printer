package views;

import javax.swing.JPanel;

import utilities.Settings;

public abstract class BasePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected MainFrame main;
	protected Settings settings;
	
	public BasePanel(MainFrame main) {
		this.main = main;
		this.settings = main.getSettings();
	}
	
	public void notifyCurrentSimFileChanged() {
		
	}
	
	public void notifyCurrentDifficultyChanged() {
		
	}
	
	public void notifyPageDimensionsChanged() {
		
	}
}
