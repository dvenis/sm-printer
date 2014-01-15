package views;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import models.StepFile;
import utilities.StepFileReader;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StepFile test;
	
	public MainFrame() {
		readStepFiles();
		
		//FileSelectorPanel fileSelectorPanel = new FileSelectorPanel(new File("C:\\Users\\Dan\\Pictures"));
		FilePanel filePanel = new FilePanel();
		//add(fileSelectorPanel, BorderLayout.WEST);
		
		RenderPanel renderPanel = new RenderPanel();
		renderPanel.setStepFile(test);
		//add(new JScrollPane(renderPanel), BorderLayout.EAST);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, filePanel, new JScrollPane(renderPanel));
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);
	
		add(splitPane);
		
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void readStepFiles() {
		StepFileReader reader = new StepFileReader("data/steps.sm");
		test = reader.generateStepFile();
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}
