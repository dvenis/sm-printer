package views;

import java.io.File;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class FilePanel extends JPanel {
	public FilePanel() {
		FileSelectorPanel fileSelectorPanel = new FileSelectorPanel(new File("C:\\Users\\Dan\\Pictures"));
		SelectionInfoPanel fileSelectionInfoPanel = new SelectionInfoPanel();
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, fileSelectorPanel, fileSelectionInfoPanel);
		splitPane.setOneTouchExpandable(true);
		//splitPane.setDividerLocation(500);
		
		add(splitPane);
	}
}
