package entities;

import java.awt.Color;
import java.awt.Graphics;

public class Page extends Entity {
	private int PAGE_MARGIN = 100;
	
	private models.Measure[] measures;
	private int columnsPerPage;
	private int measuresPerColumn;
	private int pageNumber;
	
	public Page(models.Measure[] measures, int columnsPerPage, int measuresPerColumn, int pageNumber, 
			int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.measures = measures;
		this.columnsPerPage = columnsPerPage;
		this.measuresPerColumn = measuresPerColumn;
		this.pageNumber = pageNumber;
		//generateObjects();
	}
	
	private void generateObjects() {
		double columnWidth = (double)(width - 2 * PAGE_MARGIN) / columnsPerPage;
		double currentX = x + PAGE_MARGIN;
		int currentMeasureIndex = pageNumber * columnsPerPage * measuresPerColumn;
		
		children = new Entity[columnsPerPage];
		for (int i = 0; i < columnsPerPage; i++) {
			children[i] = new Column(measures, (int)currentX, y + PAGE_MARGIN, (int)columnWidth, height - 2 * PAGE_MARGIN);
			currentX += columnWidth;
		}
		
	}

	@Override
	public void draw(Graphics g) {
		highlightRegion(g, Color.YELLOW);
		
	}

}
