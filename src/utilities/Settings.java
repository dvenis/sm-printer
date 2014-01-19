package utilities;

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
}