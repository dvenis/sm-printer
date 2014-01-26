package entities;

import java.awt.Color;
import java.awt.Graphics;

import models.StepLine;

public class Line extends Container {
	protected models.StepLine line;
	
	protected Hold[] currentHolds;
	
	public Line(models.StepLine line, Hold[] currentHolds, int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.line = line;
		this.currentHolds = currentHolds;
		
		generateObjects();
	}
	
	private void generateObjects() {
		models.Step[] steps = line.getSteps();
		children = new Entity[steps.length];
		
		double currentX = x;
		double stepWidth = (double)width / steps.length; 
		for (int i = 0; i < steps.length; i++) {
			children[i] = new Step(steps[i], currentHolds, i, (int)currentX, y, (int)stepWidth);
			currentX += stepWidth;
		}
	}

	@Override
	public void draw(Graphics g) {
		drawChildren(g);
	}
	
	@Override
	public void drawMidground(Graphics g) {
		drawChildrenMidground(g);
	}

	@Override
	public void drawBackground(Graphics g) {
		highlightRegion(g, Color.RED);
		
		if (line.getTiming() == StepLine.Timing.L1ST) { 
			g.setColor(Color.BLACK);
			g.fillRect(x, y - 2, width, 4);
		} else if (line.getTiming() == StepLine.Timing.L4TH) {
			g.setColor(Color.BLACK);
			g.fillRect(x, y - 1, width, 2);	
		}
		
		drawChildrenBackground(g);
	}
}
