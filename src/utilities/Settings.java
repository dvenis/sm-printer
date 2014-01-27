package utilities;

import java.awt.Color;
import java.io.File;

import models.StepFile;
import models.StepFileDifficultyMap;

public class Settings {
	public static int measuresPerColumn = 3;
	public static int columnsPerPage = 4;
	public static boolean horizontalOrientation = true;
	
	public static File currentDirectory;
	
	public static StepFile currentStepFile;
	public static StepFileDifficultyMap currentDifficulty;
	
	public static double pageWidthInches = 8.5;
	public static double pageHeightInches = 11;
	
	public static Color pageColor = null;//Color.YELLOW;
	public static Color pageOutlineColor = Color.BLACK;
	public static Color columnColor = null;//Color.BLUE;
	public static Color columnOutlineColor = Color.BLACK;
	public static Color measureColor = null;//Color.GREEN;
	public static Color measureOutlineColor = Color.BLACK;
	
	public static int getMeasuresPerPage() {
		return measuresPerColumn * columnsPerPage;
	}
}