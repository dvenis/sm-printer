package views;

import java.awt.Dimension;
import java.awt.Graphics;

import utilities.StepFileRenderer;
import models.StepFile;
import models.StepFileDifficultyMap;

public class RenderPanel extends BasePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private StepFile stepFile;
	private StepFileRenderer renderer;
	
	public RenderPanel(MainFrame main) {
		super(main);
		renderer = new StepFileRenderer();
		
		setVisible(true);
		//setSize(5000, 5000);
		setPreferredSize(new Dimension(1000, 1000));
	}
	
	public void setDifficulty(StepFileDifficultyMap difficulty) {
		renderer.setDifficulty(difficulty);
		repaint();
	}
	
	public void setStepFileAndDifficulty(StepFile stepFile, StepFileDifficultyMap difficulty) {
		renderer.setStepFileAndDifficulty(stepFile, difficulty);
		setPreferredSize(renderer.getScreenSize());
		repaint();
	}
	
	public void setStepFileAndDifficultyIndex(StepFile stepFile, int difficultyIndex) {		
		renderer.setStepFileAndDifficultyIndex(stepFile, difficultyIndex);
		setPreferredSize(renderer.getScreenSize());
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, (int)renderer.getScreenSize().getWidth(), (int)renderer.getScreenSize().getHeight());
		renderer.render(g);
	}
}
