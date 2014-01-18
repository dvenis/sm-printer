package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import models.Measure;
import models.Step;
import models.StepFile;
import models.StepFileDifficultyMap;
import models.StepLine;

public class StepFileRenderer {
	private final static int STEP_DIMENSION = 25;
	private final static int STEP_SPACING = 35;
	//private final static int STEPLINE_HEIGHT = 100;
	private final static int BASE_MEASURE_HEIGHT = 200;
	private final static int HORIZONTAL_OFFSET = 50;
	private final static int VERTICAL_OFFSET = 50;
	private final static double ZOOM_TICK = 0.05;
	
	private final static int COLUMN_MARGIN = 10;
	private final static int PRINTABLE_PAGE_WIDTH = 720;
	private final static int PRINTABLE_PAGE_HEIGHT = 540;
	private final static int PAGE_WIDTH = 792;
	private final static int PAGE_HEIGHT = 612;
	
	private final static String NOTES_DIR = "notes/";
	private final static int STEP_DIM = 64;
	private static boolean imagesLoaded = false;
	private static BufferedImage step4th;
	
	private StepFile stepFile;
	private StepFileDifficultyMap difficulty;
	private Graphics currentGraphics;
	private Dimension screenSize;
	
	private double zoom = 1.0;
	
	//printing variables
	private int measuresPerColumn = 3;
	private int columnsPerPage = 5;
	private boolean horizontalOrientation = true;
	
	public StepFileRenderer() {
		if (!imagesLoaded) {
			try {
				step4th = ImageIO.read(new File(NOTES_DIR + "4th.png"));
				imagesLoaded = true;
			} catch (IOException e) {
				imagesLoaded = false;
			}
		}
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
	
	public void setDifficulty(StepFileDifficultyMap difficulty) {
		this.difficulty = difficulty;
	}
	
	public void setStepFileAndDifficulty(StepFile stepFile, StepFileDifficultyMap difficulty) {
		this.stepFile = stepFile;
		this.difficulty = difficulty;
		calculateScreenSize();
	}
	
	public void setStepFileAndDifficultyIndex(StepFile stepFile, int difficultyIndex) {		
		if (stepFile.getDifficulties().size() > difficultyIndex) {
			this.stepFile = stepFile;
			this.difficulty = stepFile.getDifficulties().get(difficultyIndex);
			calculateScreenSize();
		}
	}
	
//	public void setStepFile(StepFile stepFile) {
//		this.stepFile = stepFile;
//		calculateScreenSize();
//	}
	
	public StepFile getStepFile() {
		return stepFile;
	}
	
	public StepFileDifficultyMap getDifficulty() {
		return difficulty;
	}
	
	public int getNumberOfPages() {
		int measuresPerPage = measuresPerColumn * columnsPerPage;
		return (int)Math.ceil((double)difficulty.getNumberOfMeasures() / measuresPerPage); 
	}
	
	private void calculateScreenSize() {
		screenSize = new Dimension((int)(PAGE_WIDTH * zoom), (int)(getNumberOfPages() * PAGE_HEIGHT * zoom));
	}
	
	public Dimension getScreenSize() {
		return screenSize;
	}
	
	public void render(Graphics g) {
		currentGraphics = g;
		if (stepFile != null) {

			int measuresPerPage = measuresPerColumn * columnsPerPage;
			int pages = (int)Math.ceil((double)difficulty.getNumberOfMeasures() / measuresPerPage); 
			for (int i = 0; i < pages; i++) {
				renderPage(i);
			}
		}
	}
	
	private void renderPage(int pageNumber) {		
		int columnWidth = PRINTABLE_PAGE_WIDTH / columnsPerPage;
		//start at the margins
		int currentX = (PAGE_WIDTH - PRINTABLE_PAGE_WIDTH) / 2;
		int currentY = pageNumber * PAGE_HEIGHT + (PAGE_HEIGHT - PRINTABLE_PAGE_HEIGHT) / 2;
		int measuresPerPage = measuresPerColumn * columnsPerPage;
		
		//for testing
		drawSpaceRect(Color.BLUE, currentX, currentY, PRINTABLE_PAGE_WIDTH, PRINTABLE_PAGE_HEIGHT);
		
		//page number
		currentGraphics.drawString(Integer.toString(pageNumber + 1), currentX, currentY);
		
		for (int i = 0; i < columnsPerPage; i++) {
			renderColumn(measuresPerPage * pageNumber + i * measuresPerColumn, currentX, currentY, columnWidth, PRINTABLE_PAGE_HEIGHT);
			currentX += columnWidth;
		}
	}
	
	private void renderColumn(int measureStartIndex, int startX, int startY, int width, int height) {
		int currentX = startX + COLUMN_MARGIN;
		int currentY = startY + COLUMN_MARGIN;
		int usableWidth = width - 2 * COLUMN_MARGIN;
		int usableHeight = height - 2 * COLUMN_MARGIN; 
		int measureHeight = usableHeight / measuresPerColumn;
		
		List<Measure> measures = difficulty.getMeasures();
		
		//for testing
		drawSpaceRect(Color.GREEN, currentX, currentY, width - 2 * COLUMN_MARGIN, height - 2 * COLUMN_MARGIN);
		
		for (int i = 0; i < measuresPerColumn; i++) {
			if (measureStartIndex + i < measures.size()) {
				renderMeasure(measures.get(measureStartIndex + i), measureStartIndex + i, currentX, currentY, usableWidth, measureHeight);
				currentY += measureHeight;
			} else {
				break;
			}
		}
	}
	
	private void renderMeasure(Measure measure, int measureNumber, int startX, int startY, int width, int height) {
		float currentY = startY;
		currentGraphics.setColor(Color.BLACK);
		currentGraphics.drawLine(startX, startY + 1, startX + width, startY + 1);
		
		//measure number
		currentGraphics.drawString("Measure: " + Integer.toString(measureNumber + 1), startX, startY);
		
		float stepLineHeight = (float) height / measure.getLines().size();
		//System.out.println("LINES: " + measure.getLines().size());
		for (StepLine line : measure.getLines()) {
			renderLine(line, startX, (int)currentY, width, (int)stepLineHeight);
			currentY += stepLineHeight;
		}
	}
	
	private void renderLine(StepLine line, int startX, int startY, int width, int height) {
		Step[] steps = line.getSteps();
		int stepWidth = width / steps.length;
		currentGraphics.setColor(Color.YELLOW);
		currentGraphics.drawLine(startX, startY, startX + width, startY);
		for (int i = 0; i < steps.length; i++) {
			renderStep(steps[i], startX + stepWidth * i, startY, stepWidth, stepWidth);
		}
	}
	
	private void renderStep(Step step, int startX, int startY, int width, int height) {
		if (step.getType() != Step.Type.NONE){ 
			if (imagesLoaded) {
//				currentGraphics.drawImage(step4th, startX, startY, startX + width, startY + height,
//						STEP_DIM, STEP_DIM, 0, 0, null);
				drawRotatedStep(step, startX, startY, width, height);
			} else {
				currentGraphics.setColor(Color.ORANGE);
				currentGraphics.fillRect(startX, startY, width, height);
			}
		}
	}

	private void drawRotatedStep(Step step, int startX, int startY, int width, int height) {
		double angle;
		switch (step.getOrientation()) {
		case LEFT:
			angle = 0;
			break;
		case RIGHT:
			angle = Math.PI;
			break;
		case DOWN:
			angle = 3 * Math.PI / 2;
			break;
		case UP:
			angle = Math.PI / 2;
			break;
		default:
			angle = 0;
		}
		//System.out.println(angle);
		AffineTransform at = new AffineTransform();
		at.translate(startX + width / 2, startY + height / 2);
		at.rotate(angle);
		at.scale((double) width / STEP_DIM, (double) height / STEP_DIM);
		at.translate(-STEP_DIM / 2, -STEP_DIM / 2);
//		Graphics2D rotatedGraphics = (Graphics2D)currentGraphics;
//		rotatedGraphics.rotate(angle, 32, 32);
//		rotatedGraphics.drawImage(step4th, startX, startY, startX + width, startY + height,
//				0, STEP_DIM, STEP_DIM, 0, null);
		//currentGraphics.drawImage(step4th, startX, startY, width, height, null);
		((Graphics2D)currentGraphics).drawImage(step4th, at, null);
		//rotatedGraphics.rotate(-angle, 32, 32);
//		int srcx1;
//		int srcy1;
//		int srcx2;		
//		int srcy2;
//		switch (orientation) {
//		case LEFT:
//			srcx1 = 0;
//			srcy1 = 0;
//			srcx2 = STEP_DIM;
//			srcy2 = STEP_DIM;
//			break;
//		case RIGHT:
//			srcx1 = STEP_DIM;
//			srcy1 = 0;
//			srcx2 = 0;
//			srcy2 = STEP_DIM;
//			break;
//		case DOWN:
//			srcx1 = STEP_DIM;
//			srcy1 = 0;
//			srcx2 = 0;
//			srcy2 = STEP_DIM;
//			break;
//		case UP:
//			srcx1 = 0;
//			srcy1 = 0;
//			srcx2 = STEP_DIM;
//			srcy2 = STEP_DIM;
//			break;
//		default:
//			srcx1 = 0;
//			srcy1 = 0;
//			srcx2 = STEP_DIM;
//			srcy2 = STEP_DIM;
//		}
//		currentGraphics.drawImage(step4th, startX, startY, startX + width, startY + height,
//				STEP_DIM, STEP_DIM, 0, 0, null);
	}
	
//	private void renderGrid() {
//		currentGraphics.setColor(Color.GRAY);
//		for (int i = 0; i < 4; i++) {
//			currentGraphics.drawLine((int)((HORIZONTAL_OFFSET + i * STEP_SPACING) * zoom), 
//					(int)(VERTICAL_OFFSET * zoom), 
//					(int)((HORIZONTAL_OFFSET + i * STEP_SPACING) * zoom), 
//					(int)(screenSize.getHeight()));			
//		}
//
//	}
	
	private void drawSpaceRect(Color color, int x, int y, int width, int height) {
		currentGraphics.setColor(color);
		currentGraphics.fillRect(x, y, width, height);
	}
}
