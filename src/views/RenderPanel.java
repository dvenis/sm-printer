package views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import entities.SimFile;
import utilities.Resources;

public class RenderPanel extends BasePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double pageDPI = 96;
	private double printableDPI = 72;
	private int pageWidth = (int)(pageDPI * settings.pageHeightInches);
	private int pageHeight = (int)(pageDPI * settings.pageWidthInches);
	
	private static final double ZOOM_TICK = 1.1;
	
	private double zoom = 1.0;
	
	private SimFile simFileDrawer;
	
	public RenderPanel(MainFrame main) {
		super(main);
		
		Resources.loadStepAssetsIfNotLoaded();
		
		setVisible(true);
		setPreferredSize(new Dimension(1584, 1000));
	}
	
	public void zoomIn() {
		zoom *= ZOOM_TICK;
		screenChanged();
	}
	
	public void zoomOut() {
		zoom /= ZOOM_TICK;
		screenChanged();
	}
	
	public void resetZoom() {
		zoom = 1.0;
		screenChanged();
	}
	
	@Override
	public void notifyCurrentStepFileChanged() {
//		simFileDrawer = new SimFile(Settings.currentStepFile, Settings.currentDifficulty,
//				Settings.columnsPerPage, Settings.measuresPerColumn, 0, 0, PAGE_WIDTH, PAGE_HEIGHT);
		simFileDrawer = new SimFile(MainFrame.getSettings(), 0, 0, pageWidth, pageHeight);
		screenChanged();
	}
	
	@Override
	public void notifyCurrentDifficultyChanged() {
//		simFileDrawer = new SimFile(Settings.currentStepFile, Settings.currentDifficulty,
//				Settings.columnsPerPage, Settings.measuresPerColumn, 0, 0, PAGE_WIDTH, PAGE_HEIGHT);
		simFileDrawer = new SimFile(MainFrame.getSettings(), 0, 0, pageWidth, pageHeight);
		screenChanged();
	}
	
	@Override
	public void notifyPageDimensionsChanged() {
//		simFileDrawer = new SimFile(Settings.currentStepFile, Settings.currentDifficulty,
//				Settings.columnsPerPage, Settings.measuresPerColumn, 0, 0, PAGE_WIDTH, PAGE_HEIGHT);
		simFileDrawer = new SimFile(MainFrame.getSettings(), 0, 0, pageWidth, pageHeight);
		screenChanged();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		
		Graphics2D g2D = (Graphics2D)g;
		g2D.scale(zoom, zoom);
		if (simFileDrawer != null) {
			simFileDrawer.drawBackground(g2D);
			simFileDrawer.drawMidground(g2D);
			simFileDrawer.draw(g2D);
		}
	}
	
	private void screenChanged() {
		int width = (int)(simFileDrawer.getWidth() * zoom);
		int height = (int)(simFileDrawer.getHeight() * zoom);
		setPreferredSize(new Dimension(width, height));
		repaint();
	}
}
