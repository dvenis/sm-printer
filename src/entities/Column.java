package entities;

import java.awt.Color;
import java.awt.Graphics;

public class Column extends Container {
	private final static int COLUMN_MARGIN = 10;
	
	private models.Measure[] measures; 
	
	public Column(models.Measure[] measures, int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.measures = measures;
		//generateObjects();
	}
	
	private void generateObjects() {
		double measureHeight = (double)(height - 2 * COLUMN_MARGIN) / measures.length;
		double currentY = y + COLUMN_MARGIN;
		
		children = new Entity[measures.length];
		for (int i = 0; i < measures.length; i++) {
			children[i] = new Measure(measures[i], x + COLUMN_MARGIN, (int)currentY, width - 2 * COLUMN_MARGIN, (int)measureHeight);
		}
	}

	@Override
	public void draw(Graphics g) {
		highlightRegion(g, Color.BLUE);
		drawChildren(g);
	}
}
