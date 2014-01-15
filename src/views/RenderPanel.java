package views;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

import models.StepFile;

public class RenderPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private StepFile stepFile;
	private StepFileRenderer renderer;
	
	public RenderPanel() {
		renderer = new StepFileRenderer();
		
		setVisible(true);
		//setSize(5000, 5000);
		setPreferredSize(new Dimension(1000, 1000));
		
		add(new JButton("This is a button"));
	}
	
	public void setStepFile(StepFile stepFile) {
		//this.stepFile = stepFile;
		renderer.setStepFile(stepFile);
		setPreferredSize(renderer.getScreenSize());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, (int)renderer.getScreenSize().getWidth(), (int)renderer.getScreenSize().getHeight());
		renderer.render(g);
	}
}
