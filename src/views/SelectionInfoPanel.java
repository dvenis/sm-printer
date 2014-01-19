package views;

import java.awt.BorderLayout;

public class SelectionInfoPanel extends BasePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
