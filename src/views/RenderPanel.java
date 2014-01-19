package views;

import java.awt.Dimension;
import java.awt.Graphics;

import utilities.Settings;
import utilities.StepFileRenderer;

public class RenderPanel extends BasePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private StepFileRenderer renderer;
	
	public RenderPanel(MainFrame main) {
		super(main);
		renderer = new StepFileRenderer();
		
		setVisible(true);
		setPreferredSize(new Dimension(1584, 1000));
	}
	
	@Override
	public void notifyCurrentStepFileChanged() {
		renderer.setStepFileAndDifficulty(Settings.currentStepFile, Settings.currentDifficulty);
		setPreferredSize(renderer.getScreenSize());
		repaint();
	}
	
	@Override
	public void notifyCurrentDifficultyChanged() {
		renderer.setDifficulty(Settings.currentDifficulty);
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, (int)renderer.getScreenSize().getWidth(), (int)renderer.getScreenSize().getHeight());
		renderer.render(g);
	}
}
