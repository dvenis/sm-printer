package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import models.Measure;
import models.Step;
import models.StepFile;
import models.StepLine;

public class StepFileRenderer {
	private final static int STEP_DIMENSION = 50;
	private final static int STEP_SPACING = 70;
	//private final static int STEPLINE_HEIGHT = 100;
	private final static int BASE_MEASURE_HEIGHT = 200;
	private final static int HORIZONTAL_OFFSET = 50;
	private final static int VERTICAL_OFFSET = 50;
	private final static double ZOOM_TICK = 0.05;
	
	private StepFile stepFile;
	private Graphics currentGraphics;
	private Dimension screenSize;
	
	private double zoom = 1.0;
	
	public StepFileRenderer() {
		
	}
	
	public void setZoom(double zoom) {
		this.zoom = zoom;
	}
	
	public void zoomIn() {
		zoom += ZOOM_TICK;
		calculateScreenSize();
	}
	
	public void zoomOut() {
		zoom -= ZOOM_TICK;
		calculateScreenSize();
	}
	
	public double getZoom() {
		return zoom;
	}
	
	public void setStepFile(StepFile stepFile) {
		this.stepFile = stepFile;
		calculateScreenSize();
	}
	
	public StepFile getStepFile() {
		return stepFile;
	}
	
	private void calculateScreenSize() {
		screenSize = new Dimension(1000, (int)((stepFile.getMeasures().size() * BASE_MEASURE_HEIGHT + VERTICAL_OFFSET) * zoom));
	}
	
	public Dimension getScreenSize() {
		return screenSize;
	}
	
	public void render(Graphics g) {
//		g.setColor(Color.PINK);
//		g.fillRect(100, 100, 100, 100);		
		currentGraphics = g;
		if (stepFile != null) {
			renderGrid();
			int currentY = (int)(VERTICAL_OFFSET * zoom);
			for (Measure measure : stepFile.getMeasures()) {
				renderMeasure(measure, (int)(HORIZONTAL_OFFSET * zoom), currentY);
				currentY += BASE_MEASURE_HEIGHT * zoom;
			}
		}
	}
	
	private void renderMeasure(Measure measure, int startX, int startY) {
		float currentY = startY;
		currentGraphics.setColor(Color.BLACK);
		currentGraphics.drawLine(startX, startY + 7, startX + 3 * STEP_SPACING, startY + 7);
		float stepLineHeight = (float) BASE_MEASURE_HEIGHT / measure.getLines().size();
		for (StepLine line : measure.getLines()) {
			renderLine(line, startX, (int)currentY);
			currentY += stepLineHeight;
		}
	}
	
	private void renderLine(StepLine line, int startX, int startY) {
		Step[] steps = line.getSteps();
		for (int i = 0; i < steps.length; i++) {
			renderStep(steps[i], startX + STEP_SPACING * i, startY);
		}
	}
	
	private void renderStep(Step step, int startX, int startY) {
		if (step.getType() != Step.Type.NONE){ 
			currentGraphics.setColor(Color.GREEN);
			currentGraphics.fillRoundRect(startX - STEP_DIMENSION / 2, startY - STEP_DIMENSION / 2, (int)(zoom * (STEP_DIMENSION)), (int)(zoom * (STEP_DIMENSION)), 2, 2);	
		}
	}
	
	private void renderGrid() {
		currentGraphics.setColor(Color.GRAY);
		for (int i = 0; i < 4; i++) {
			currentGraphics.drawLine((int)((HORIZONTAL_OFFSET + i * STEP_SPACING) * zoom), 
					(int)(VERTICAL_OFFSET * zoom), 
					(int)((HORIZONTAL_OFFSET + i * STEP_SPACING) * zoom), 
					(int)(screenSize.getHeight()));			
		}

	}
}
