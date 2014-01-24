package entities;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Container {
	private models.StepLine stepLine;
	
	private Entity[] stepEntities;
	private Hold[] currentHolds;
	
	public Line(models.Step[] steps, Hold[] currentHolds, int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.currentHolds = currentHolds;
	}

	@Override
	public void draw(Graphics g) {
		highlightRegion(g, Color.RED);
		
		for (Entity step : stepEntities) {
			step.draw(g);
		}
		
	}

}
