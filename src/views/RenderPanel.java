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
	
	public void zoomIn() {
		renderer.zoomIn();
		screenChanged();
	}
	
	public void zoomOut() {
		renderer.zoomOut();
		screenChanged();
	}
	
	public void resetZoom() {
		renderer.resetZoom();
		screenChanged();
	}
	
	@Override
	public void notifyCurrentStepFileChanged() {
		renderer.setStepFileAndDifficulty(Settings.currentStepFile, Settings.currentDifficulty);
		screenChanged();
	}
	
	@Override
	public void notifyCurrentDifficultyChanged() {
		renderer.setDifficulty(Settings.currentDifficulty);
		screenChanged();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		renderer.render(g);
	}
	
	private void screenChanged() {
		setPreferredSize(renderer.getScreenSize());
		repaint();
	}
}
