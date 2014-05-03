package views;

import java.awt.BorderLayout;

import javax.swing.border.EmptyBorder;

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
		pageInfoPanel.setBorder(new EmptyBorder(10, 10, 5, 10));
		
		stepFileInfoPanel = new StepFileInfoPanel(main);
		stepFileInfoPanel.setBorder(new EmptyBorder(5, 10, 10, 10));
		
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
