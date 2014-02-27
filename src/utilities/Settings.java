package utilities;

import java.awt.Color;
import java.io.File;

import models.StepFile;
import models.StepFileDifficultyMap;

public class Settings {
	public int measuresPerColumn = 3;
	public int columnsPerPage = 4;
	public boolean horizontalOrientation = true;
	public boolean hideLeadingWhiteSpace = false;
	
	public File currentDirectory;
	
	public StepFile stepFile;
	public StepFileDifficultyMap difficulty;
	
	public double pageWidthInches = 8.5;
	public double pageHeightInches = 11;
	
	public Color pageColor = null;//Color.YELLOW;
	public Color pageOutlineColor = Color.BLACK;
	public Color columnColor = null;//Color.BLUE;
	public Color columnOutlineColor = Color.BLACK;
	public Color measureColor = null;//Color.GREEN;
	public Color measureOutlineColor = Color.BLACK;
	
	public int getMeasuresPerPage() {
		return measuresPerColumn * columnsPerPage;
	}
	
	public int getNumberOfPages() {
		int measuresPerPage = measuresPerColumn * columnsPerPage;
		return (int)Math.ceil((double)difficulty.getNumberOfMeasures() / measuresPerPage); 
	}
	
	public Settings() {
		
	}
}
