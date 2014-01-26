package entities;

import java.awt.Graphics;
import java.util.List;

import utilities.Settings;
import models.StepFile;
import models.StepFileDifficultyMap;

public class SimFile extends Container {
	private final static double PAGE_DPI = 96;
	private final static double PRINTABLE_DPI = 72;
	private final static int PAGE_WIDTH = (int)(PAGE_DPI * 11);
	private final static int PAGE_HEIGHT = (int)(PAGE_DPI * 8.5);
	
	private final StepFile stepFile;
	private final StepFileDifficultyMap difficulty;
	private final int columnsPerPage;
	private final int measuresPerColumn;
	
	public SimFile(StepFile stepFile, StepFileDifficultyMap difficulty, int columnsPerPage, int measuresPerColumn, 
			int x, int y) {
		super(x, y, 0, 0);
		
		this.stepFile = stepFile;
		this.difficulty = difficulty;
		this.columnsPerPage = columnsPerPage;
		this.measuresPerColumn = measuresPerColumn;
		
		generateObjects();
	}
	
	private void generateObjects() {
		models.Measure[] measures = padMeasures(difficulty.getMeasures());
		int numberOfPages = StepFile.calculateNumberOfPages(difficulty, columnsPerPage, measuresPerColumn);

		children = new Entity[numberOfPages];
		for (int i = 0; i < numberOfPages; i++) {
			children[i] = new Page(measures, i, columnsPerPage, measuresPerColumn, x, i * PAGE_HEIGHT, PAGE_WIDTH, PAGE_HEIGHT);
		}
	}
	
	private models.Measure[] padMeasures(List<models.Measure> measureList) { 
		int measureSize = (int)Math.ceil((double)measureList.size() / Settings.getMeasuresPerPage()) * Settings.getMeasuresPerPage();
		models.Measure[] result = new models.Measure[measureSize];
		return measureList.toArray(result);
	}

	@Override
	public void draw(Graphics g) {
		drawChildren(g);
	}
	
	@Override
	public void drawBackground(Graphics g) {
		drawChildrenBackground(g);
	}

}
