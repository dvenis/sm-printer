package views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class FilePanel extends BasePanel {
	
	public FilePanel(MainFrame main) {
		super(main);
		
		FileSelectorPanel fileSelectorPanel = new FileSelectorPanel(main, new File("C:\\Users\\Dan\\Pictures"));
		SelectionInfoPanel fileSelectionInfoPanel = new SelectionInfoPanel(main);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.BOTH;
		add(fileSelectorPanel, c);
		
		c.gridy = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(fileSelectionInfoPanel, c);
		
//		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, fileSelectorPanel, fileSelectionInfoPanel);
//		splitPane.setOneTouchExpandable(true);
//		//splitPane.setDividerLocation(500);
//		
//		add(splitPane);
	}
}
