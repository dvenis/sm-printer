package entities;

import java.awt.Color;
import java.awt.Graphics;

import utilities.Settings;
import models.stepmetadata.Timing;

/**
 * An entity that represents one line on a step chart. Each measure contains multiple lines
 * (or rows) that display specific timing button presses.
 * 
 * @author Dan
 *
 */

public class Line extends Container {
	protected models.SimFileLine line;
	
	protected Hold[] currentHolds;
	
	protected double stepSideLength;
	
	public Line(Settings settings, models.SimFileLine line, Hold[] currentHolds, int x, int y, int width, int height) {
		super(settings, x, y, width, height);
		
		this.line = line;
		this.currentHolds = currentHolds;
		
		generateObjects();
	}
	
	private void generateObjects() {
		models.Step[] steps = line.getSteps();
		children = new Entity[steps.length];
		
		stepSideLength = (double)width / steps.length; 
		double currentX = x;
		for (int i = 0; i < steps.length; i++) {
			children[i] = new Step(settings, steps[i], currentHolds, i, (int)currentX, y, (int)stepSideLength, height);
			currentX += stepSideLength;
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
	//	highlightRegion(g, Color.RED);
		
		if (line.getTiming() == Timing.L1ST) { 
			g.setColor(Color.BLACK);
			g.fillRect(x, y + (int)(stepSideLength / 2) - 2, width, 4);
		} else if (line.getTiming() == Timing.L4TH) {
			g.setColor(Color.BLACK);
			g.fillRect(x, y + (int)(stepSideLength / 2) - 1, width, 2);	
		}
		
		drawChildrenBackground(g);
	}
}
