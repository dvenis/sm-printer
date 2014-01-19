package views;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import models.StepFile;
import models.StepFileDifficultyMap;
import utilities.StepFileReader;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StepFile currentStepFile;
	private StepFileDifficultyMap currentDifficulty;
	
	private RenderPanel renderPanel;
	private SelectionInfoPanel selectionInfoPanel;
	private FileSelectorPanel fileSelectorPanel;
	
	public MainFrame() {
		//readStepFiles();
			
		renderPanel = new RenderPanel(this);
		
		selectionInfoPanel = new SelectionInfoPanel(this);
		fileSelectorPanel = new FileSelectorPanel(this, new File("C:\\Users\\Dan\\Pictures"));
		
		JSplitPane westSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, fileSelectorPanel, selectionInfoPanel);
		westSplitPane.setOneTouchExpandable(true);
		westSplitPane.setDividerLocation(500);
		
		JSplitPane centreSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, westSplitPane, new JScrollPane(renderPanel));
		centreSplitPane.setOneTouchExpandable(true);
		centreSplitPane.setDividerLocation(150);
	
		add(centreSplitPane);
		
		openStepFile("data/Renaissance.sm");
		
		setSize(1000, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public StepFile getCurrentStepFile() {
		return currentStepFile;
	}
	
	public StepFileDifficultyMap getCurrentDifficulty() {
		return currentDifficulty;
	}
	
	public void openStepFile(String path) {
		StepFileReader reader = new StepFileReader(path);
		currentStepFile = reader.generateStepFile();
		currentDifficulty = currentStepFile.getDifficulties().get(0);
		
		selectionInfoPanel.setStepFileAndDifficulty(currentStepFile, currentDifficulty);
		renderPanel.setStepFileAndDifficulty(currentStepFile, currentDifficulty);
	}
	
	public void openDifficulty(StepFileDifficultyMap difficulty) {
		currentDifficulty = difficulty;
		renderPanel.setDifficulty(difficulty);
	}
	
//	private void readStepFiles() {
//		StepFileReader reader = new StepFileReader("data/COW GIRL.sm");
//		currentStepFile = reader.generateStepFile();
//	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}
