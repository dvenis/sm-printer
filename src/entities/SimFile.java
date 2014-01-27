package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.List;

import utilities.Settings;
import models.StepFile;
import models.StepFileDifficultyMap;

public class SimFile extends Container implements Printable {
	private final int pageWidth;
	private final int pageHeight;
	
	private final StepFile stepFile;
	private final StepFileDifficultyMap difficulty;
	private final int columnsPerPage;
	private final int measuresPerColumn;
	
	public SimFile(StepFile stepFile, StepFileDifficultyMap difficulty, int columnsPerPage, int measuresPerColumn, 
			int x, int y, int pageWidth, int pageHeight) {
		super(x, y, 0, 0);
		
		this.stepFile = stepFile;
		this.difficulty = difficulty;
		this.columnsPerPage = columnsPerPage;
		this.measuresPerColumn = measuresPerColumn;
		this.pageWidth = pageWidth;
		this.pageHeight = pageHeight;
		
		generateObjects();
	}
	
	private void generateObjects() {
		final String pageHeader = stepFile.getTitle() + " - " + stepFile.getArtist() + " (" + difficulty + ")";
		
		models.Measure[] measures = padMeasures(difficulty.getMeasures());
		int numberOfPages = StepFile.calculateNumberOfPages(difficulty, columnsPerPage, measuresPerColumn);
		
		children = new Entity[numberOfPages];
		for (int i = 0; i < numberOfPages; i++) {
			children[i] = new Page(measures, pageHeader, i, columnsPerPage, measuresPerColumn, 
					x, i * pageHeight, pageWidth, pageHeight);
		}
		
		width = pageWidth;
		height = numberOfPages * pageHeight;
	}
	
	private models.Measure[] padMeasures(List<models.Measure> measureList) { 
		int measureSize = (int)Math.ceil((double)measureList.size() / Settings.getMeasuresPerPage()) * Settings.getMeasuresPerPage();
		models.Measure[] result = new models.Measure[measureSize];
		return measureList.toArray(result);
	}
	
	public int getNumberOfPages() {
		return children.length;
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
		drawChildrenBackground(g);
	}

	@Override
	public int print(Graphics g, PageFormat pageFormat, int pageNumber)
			throws PrinterException {
		if (pageNumber < children.length) {
			
		} else {
			return NO_SUCH_PAGE;
		}
		
		Graphics2D g2D = (Graphics2D)g;
		final double xAxisScale = (double)pageFormat.getWidth() / pageWidth;
		final double yAxisScale = (double)pageFormat.getHeight() / pageHeight;
		if (Math.abs(xAxisScale - yAxisScale) > 1E7) {
			System.err.println("WARNING: The print page has a different aspect ratio than the sim file painter");
		}
		g2D.scale(xAxisScale, yAxisScale);
		g2D.translate(0, -pageNumber * pageHeight);
		Page page = (Page)children[pageNumber];
		page.drawBackground(g2D);
		page.drawMidground(g2D);
		page.draw(g2D);
		
		return PAGE_EXISTS;
	}

}
