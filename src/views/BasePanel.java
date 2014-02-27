package views;

import javax.swing.JPanel;

import utilities.SettingsInstance;

public abstract class BasePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected MainFrame main;
	protected SettingsInstance settings;
	
	public BasePanel(MainFrame main) {
		this.main = main;
		this.settings = main.getSettings();
	}
	
	public void notifyCurrentStepFileChanged() {
		
	}
	
	public void notifyCurrentDifficultyChanged() {
		
	}
	
	public void notifyPageDimensionsChanged() {
		
	}
}
