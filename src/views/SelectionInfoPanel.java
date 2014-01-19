package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;

import models.StepFile;
import models.StepFileDifficultyMap;

public class SelectionInfoPanel extends BasePanel {

	private PageInfoPanel pageInfoPanel;
	private StepFileInfoPanel stepFileInfoPanel;
	
	public SelectionInfoPanel(MainFrame main) {
		super(main);
		
		pageInfoPanel = new PageInfoPanel(main);
		stepFileInfoPanel = new StepFileInfoPanel(main);
		
		setLayout(new BorderLayout());
		add(stepFileInfoPanel, BorderLayout.NORTH);
		add(pageInfoPanel, BorderLayout.SOUTH);
	}

	@Override
	public void notifyCurrentStepFileChanged() {
		pageInfoPanel.notifyCurrentStepFileChanged();
		stepFileInfoPanel.notifyCurrentStepFileChanged();
	}
}
