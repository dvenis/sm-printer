package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import utilities.Resources;
import utilities.Settings;

public class Page extends Container {
	private int PAGE_MARGIN = 48;
	
	private models.Measure[] measures;
	private int columnsPerPage;
	private int measuresPerColumn;
	private int pageNumber;
	private String pageHeader;
	
	public Page(models.Measure[] measures, String pageHeader, int pageNumber, int columnsPerPage, int measuresPerColumn,
			int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.measures = measures;
		this.columnsPerPage = columnsPerPage;
		this.measuresPerColumn = measuresPerColumn;
		this.pageHeader = pageHeader;
		this.pageNumber = pageNumber;
		
		generateObjects();
	}
	
	private void generateObjects() {
		double columnWidth = (double)(width - 2 * PAGE_MARGIN) / columnsPerPage;
		double currentX = x + PAGE_MARGIN;
		int currentMeasureIndex = pageNumber * columnsPerPage * measuresPerColumn;
		
		children = new Entity[columnsPerPage];
		for (int i = 0; i < columnsPerPage; i++) {
			models.Measure[] columnMeasures = Arrays.copyOfRange(measures, currentMeasureIndex, currentMeasureIndex + measuresPerColumn);
			children[i] = new Column(columnMeasures, 
					(int)currentX, y + PAGE_MARGIN, (int)columnWidth, height - 2 * PAGE_MARGIN);
			currentX += columnWidth;
			currentMeasureIndex += measuresPerColumn;
		}
		
	}

	@Override
	public void draw(Graphics g) {
		drawChildren(g);
		
		g.setColor(Color.BLACK);
		g.setFont(Resources.getInstance().pageHeader);
		g.drawString(pageHeader, x + PAGE_MARGIN, y + PAGE_MARGIN - 10);
		g.drawString("Page " + (pageNumber + 1), x + width / 2 - 30, y + PAGE_MARGIN - 10);
	}
	
	@Override
	public void drawMidground(Graphics g) {
		drawChildrenMidground(g);
	}

	@Override
	public void drawBackground(Graphics g) {
		highlightRegion(g, Settings.pageColor);
		outlineRegion(g, Settings.pageOutlineColor);
		drawChildrenBackground(g);
	}
}
