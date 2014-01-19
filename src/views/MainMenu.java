package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import utilities.Printer;

public class MainMenu extends JMenuBar implements ActionListener {
	private static final String OPEN_FILE_COMMAND = "openFile";
	private static final String OPEN_DIRECTORY_COMMAND = "openDirectory";
	private static final String PRINT_COMMAND = "print";
	private static final String ABOUT_COMMAND = "about";
	
	protected MainFrame main;
	
	public MainMenu(MainFrame main) {
		this.main = main;
		
		add(createFileMenu());
		add(createHelpMenu());
	}
	
	private JMenu createFileMenu() {
		JMenuItem menuItem;
		JMenu fileMenu = new JMenu("File");
		
		menuItem = new JMenuItem("Open File");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("openFile");
		fileMenu.add(menuItem);
		
		menuItem = new JMenuItem("Open Directory");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("openDirectory");
		fileMenu.add(menuItem);
		
		menuItem = new JMenuItem("Print");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("print");
		fileMenu.add(menuItem);
		
		return fileMenu;
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
		if (OPEN_FILE_COMMAND.equals(e.getActionCommand())) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int selectedOption = fc.showDialog(main, "Open File");
			if (selectedOption == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				main.openFile(file);
			}
		} else if (OPEN_DIRECTORY_COMMAND.equals(e.getActionCommand())) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int selectedOption = fc.showDialog(main, "Open Directory");
			if (selectedOption == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				main.openFile(file);
			}
		} else if (PRINT_COMMAND.equals(e.getActionCommand())) {
			Printer.printSimFile(main.getCurrentStepFile(), main.getCurrentDifficulty());
		} else if (ABOUT_COMMAND.equals(e.getActionCommand())) {
			
		}
		
	}
}
