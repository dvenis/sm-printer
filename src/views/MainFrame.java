package views;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import models.SimFileDifficulty;
import utilities.Settings;
import utilities.SimFileReader;

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
		
		String directory = getDirectoryToOpen();
		if (directory != null) {
			fileSelectorPanel = new FileSelectorPanel(this, new File(directory));
		} else {
			fileSelectorPanel = new FileSelectorPanel(this, null);
		}
		
		
		JSplitPane westSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, fileSelectorPanel, selectionInfoPanel);
		westSplitPane.setOneTouchExpandable(true);
		westSplitPane.setDividerLocation(450);
		
		JSplitPane centreSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, westSplitPane, new JScrollPane(renderPanel));
		centreSplitPane.setOneTouchExpandable(true);
		centreSplitPane.setDividerLocation(200);
	
		add(centreSplitPane);
		

		setJMenuBar(new MainMenu(this));
		
		setTitle("Simfile Printer");
		
		setSize(1000, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private String getDirectoryToOpen() {
		final String[] directories = {
				"C:/Program Files (x86)/StepMania 5/Songs/",
				"C:/Program Files (x86)/StepMania3.95/Songs/",
				"C:/Program Files (x86)/StepMania3.9b/Songs/",
				"C:/Program Files (x86)/OpenITG/Songs/",
				"C:/Program Files (x86)/ITG2/Songs/"
		};
		
		for (String directory : directories) {
			if (new File(directory).exists()) {
				return directory;
			}
		}
		return null;
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
		SimFileReader reader = new SimFileReader(path);
		settings.stepFile = reader.generateSimFile();
		settings.difficulty = settings.stepFile.getDifficulties().get(0);
		
		selectionInfoPanel.notifyCurrentStepFileChanged();
		renderPanel.notifyCurrentStepFileChanged();
	}
	
	public void openDifficulty(SimFileDifficulty difficulty) {
		settings.difficulty = difficulty;
		
		renderPanel.notifyCurrentDifficultyChanged();
		selectionInfoPanel.notifyCurrentDifficultyChanged();
	}
	
	public void notifyPageDimensionsChanged() {
		renderPanel.notifyPageDimensionsChanged();
	}
	
	public void invertMeasureTrimming() {
		settings.hideLeadingAndTrailingWhiteSpace = !settings.hideLeadingAndTrailingWhiteSpace;
		
		renderPanel.notifyCurrentDifficultyChanged();
		selectionInfoPanel.notifyCurrentDifficultyChanged();
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
