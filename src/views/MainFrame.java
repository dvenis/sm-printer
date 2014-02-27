package views;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import models.StepFileDifficultyMap;
import utilities.Settings;
import utilities.StepFileReader;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Settings settings;
	
	private RenderPanel renderPanel;
	private SelectionInfoPanel selectionInfoPanel;
	private FileSelectorPanel fileSelectorPanel;
	
	public MainFrame() {
		settings = new Settings();
		
		renderPanel = new RenderPanel(this);
		
		selectionInfoPanel = new SelectionInfoPanel(this);
		fileSelectorPanel = new FileSelectorPanel(this, new File("C:\\Program Files (x86)\\ITG2\\Songs"));
		
		JSplitPane westSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, fileSelectorPanel, selectionInfoPanel);
		westSplitPane.setOneTouchExpandable(true);
		westSplitPane.setDividerLocation(500);
		
		JSplitPane centreSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, westSplitPane, new JScrollPane(renderPanel));
		centreSplitPane.setOneTouchExpandable(true);
		centreSplitPane.setDividerLocation(150);
	
		add(centreSplitPane);
		
		openStepFile("data/Renaissance.sm");
		
		setJMenuBar(new MainMenu(this));
		
		setTitle("Simfile Printer");
		
		setSize(1000, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	
	public void openFile(File file) {
		System.out.println(file);
		fileSelectorPanel.openFileOrDirectory(file);
		if (file.isFile()) {
			openStepFile(file.getAbsolutePath());
		}
	}
	
	public void openStepFile(String path) {
		StepFileReader reader = new StepFileReader(path);
		settings.stepFile = reader.generateStepFile();
		settings.difficulty = settings.stepFile.getDifficulties().get(0);
		
		selectionInfoPanel.notifyCurrentStepFileChanged();
		renderPanel.notifyCurrentStepFileChanged();
	}
	
	public void openDifficulty(StepFileDifficultyMap difficulty) {
		settings.difficulty = difficulty;
		
		renderPanel.notifyCurrentDifficultyChanged();
		selectionInfoPanel.notifyCurrentDifficultyChanged();
	}
	
	public void notifyPageDimensionsChanged() {
		renderPanel.notifyPageDimensionsChanged();
	}
	
	public void zoomIn() {
		renderPanel.zoomIn();
	}
	
	public void zoomOut() {
		renderPanel.zoomOut();
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}
