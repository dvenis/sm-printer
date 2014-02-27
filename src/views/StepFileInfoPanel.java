package views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import utilities.Settings;
import utilities.SettingsInstance;
import models.StepFileDifficultyMap;

public class StepFileInfoPanel extends BasePanel implements ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel titleLabel;
	private JLabel titleFieldLabel;
	private JLabel subtitleLabel;
	private JLabel subtitleFieldLabel;
	private JLabel artistLabel;
	private JLabel artistFieldLabel;
	private JLabel creditLabel;
	private JLabel creditFieldLabel;
	
	private JComboBox<StepFileDifficultyMap> difficultySelector;
	private DefaultComboBoxModel<StepFileDifficultyMap> difficultyList;
	
	public StepFileInfoPanel(MainFrame main) {	
		super(main);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridheight = 1;
		c.gridwidth = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.ipady = 10;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
	
		
		titleLabel = new JLabel("Title: ");
		titleFieldLabel = new JLabel();
		addLabelAndField(c, 0, titleLabel, titleFieldLabel);
		
		subtitleLabel = new JLabel("Subtitle: ");
		subtitleFieldLabel = new JLabel();
		addLabelAndField(c, 1, subtitleLabel, subtitleFieldLabel);
		
		artistLabel = new JLabel("Artist: ");
		artistFieldLabel = new JLabel();
		addLabelAndField(c, 2, artistLabel, artistFieldLabel);
		
		creditLabel = new JLabel("Credit: ");
		creditFieldLabel = new JLabel();
		addLabelAndField(c, 3, creditLabel, creditFieldLabel);
		
		c.weighty = 1;
		c.gridy = 4;
		c.gridx = 0;
		c.gridwidth = 2;
		difficultyList = new DefaultComboBoxModel<StepFileDifficultyMap>();
		difficultySelector = new JComboBox<StepFileDifficultyMap>(difficultyList);		
		difficultySelector.addItemListener(this);
		add(difficultySelector, c);
		
//		setLayout(new BorderLayout());
//		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.getViewport().add(infoPane);
//		add(scrollPane, BorderLayout.CENTER);
	}
	
	private void addLabelAndField(GridBagConstraints c, int row, JLabel label, JLabel field) {
		c.gridx = 0;
		c.gridy = row;
		add(label, c);
		c.gridx = 1;
		add(field, c);
	}
	
	
	@Override
	public void notifyCurrentStepFileChanged() {
		//setStepFileAndDifficulty(Settings.currentStepFile, Settings.currentDifficulty);
		updateInfo();
	}
	
	private void updateInfo() {
//		updateAndHideField(titleLabel, titleFieldLabel, Settings.currentStepFile.getTitle());
//		updateAndHideField(subtitleLabel, subtitleFieldLabel, Settings.currentStepFile.getSubtitle());
//		updateAndHideField(artistLabel, artistFieldLabel, Settings.currentStepFile.getArtist());
//		updateAndHideField(creditLabel, creditFieldLabel, Settings.currentStepFile.getCredit());
		SettingsInstance settings = MainFrame.getSettings();
		updateAndHideField(titleLabel, titleFieldLabel, settings.stepFile.getTitle());
		updateAndHideField(subtitleLabel, subtitleFieldLabel, settings.stepFile.getSubtitle());
		updateAndHideField(artistLabel, artistFieldLabel, settings.stepFile.getArtist());
		updateAndHideField(creditLabel, creditFieldLabel, settings.stepFile.getCredit());
		
		difficultyList = new DefaultComboBoxModel<StepFileDifficultyMap>(settings.stepFile.getDifficulties().toArray(new StepFileDifficultyMap[0]));
		difficultySelector.setModel(difficultyList);
	}
	
	private void updateAndHideField(JLabel label, JLabel fieldLabel, String text) {
		if (text != null && !text.equals("")) {
			label.setVisible(true);
			fieldLabel.setVisible(true);
			fieldLabel.setText(text);
		} else {
			label.setVisible(false);
			fieldLabel.setVisible(false);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			StepFileDifficultyMap difficulty = (StepFileDifficultyMap)event.getItem();
			main.openDifficulty(difficulty);
			System.out.println("opened " + difficulty);
		}		
	}

}
