package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import utilities.Printer;
import utilities.Settings;

public class MainMenu extends JMenuBar implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String OPEN_COMMAND = "openFile";
	private static final String PRINT_COMMAND = "print";
	private static final String ABOUT_COMMAND = "about";
	private static final String ZOOM_IN_COMMAND = "zoomIn";
	private static final String ZOOM_OUT_COMMAND = "zoomOut";
	private static final String HIDE_LEADING_AND_TRALING_MEASURES_COMMAND = "hideLeadingAndTrailingMeasures";
	
	protected MainFrame main;
	
	public MainMenu(MainFrame main) {
		this.main = main;
		
		add(createFileMenu());
		add(createViewMenu());
		add(createHelpMenu());
	}
	
	private JMenu createFileMenu() {
		JMenuItem menuItem;
		JMenu fileMenu = new JMenu("File");
		
		menuItem = new JMenuItem("Open File");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand(OPEN_COMMAND);
		fileMenu.add(menuItem);
		
		menuItem = new JMenuItem("Print");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand(PRINT_COMMAND);
		fileMenu.add(menuItem);
		
		return fileMenu;
	}
	
	private JMenu createViewMenu() {
		JMenuItem menuItem;
		JMenu viewMenu = new JMenu("View");
		
		menuItem = new JMenuItem("Zoom In");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand(ZOOM_IN_COMMAND);
		viewMenu.add(menuItem);
		
		menuItem = new JMenuItem("Zoom Out");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand(ZOOM_OUT_COMMAND);
		viewMenu.add(menuItem);
		
		menuItem = new JCheckBoxMenuItem("Hide Leading and Trailing Empty Measures");
		menuItem.addActionListener(this);
		menuItem.setActionCommand(HIDE_LEADING_AND_TRALING_MEASURES_COMMAND);
		viewMenu.add(menuItem);
		
		return viewMenu;
	}
	
	private JMenu createHelpMenu() {
		JMenuItem menuItem;
		JMenu helpMenu = new JMenu("Help");
		
		menuItem = new JMenuItem("About");
		menuItem.addActionListener(this);
		helpMenu.add(menuItem);
		
		return helpMenu;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (OPEN_COMMAND.equals(e.getActionCommand())) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int selectedOption = fc.showDialog(main, "Open File or Directory");
			if (selectedOption == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				main.openFile(file);
			}
		} else if (PRINT_COMMAND.equals(e.getActionCommand())) {
			Printer.printSimFile(MainFrame.getSettings());
		} else if (ZOOM_IN_COMMAND.equals(e.getActionCommand())) {
			main.zoomIn();
		} else if (ZOOM_OUT_COMMAND.equals(e.getActionCommand())) {
			main.zoomOut();
		} else if (ABOUT_COMMAND.equals(e.getActionCommand())) {
			
		}
		
	}
}
