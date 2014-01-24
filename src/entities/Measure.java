package entities;

import java.awt.Color;
import java.awt.Graphics;

public class Measure extends Container {
	protected models.Measure measure; 
	
	public Measure(models.Measure measure, int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.measure = measure;
	}

	@Override
	public void draw(Graphics g) {
		highlightRegion(g, Color.GREEN);
		drawChildren(g);
	}
	
}
