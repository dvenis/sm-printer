package views;

import javax.swing.JPanel;

public abstract class BasePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected MainFrame main;
	
	public BasePanel(MainFrame main) {
		this.main = main;
	}
	
	public void notifyCurrentStepFileChanged() {
		
	}
	
	public void notifyCurrentDifficultyChanged() {
		
	}
}
