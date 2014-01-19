package utilities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
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

public class StepFileRenderer implements Printable {
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
	
	private final static String NOTES_DIR = "notes/stepmania5/";
	private final static int STEP_DIM = 128;
	private final static int HOLD_DIM = 128;
	private static boolean imagesLoaded = false;
	private static BufferedImage step4th;
	private static BufferedImage step8th;
	private static BufferedImage step16th;
	private static BufferedImage step32nd;
	private static BufferedImage step64th;
	private static BufferedImage step12th;
	private static BufferedImage step24th;
	private static BufferedImage step48th;
	private static BufferedImage holdBody;
	private static BufferedImage holdEnd;
	private static BufferedImage rollBody;
	private static BufferedImage rollEnd;
	
	private StepFile stepFile;
	private StepFileDifficultyMap difficulty;
	private Graphics currentGraphics;
	private Dimension screenSize;
	
	private double zoom = 1.0;
	
	//printing variables
	private int measuresPerColumn = 3;
	private int columnsPerPage = 4;
	private boolean horizontalOrientation = true;
	
	public StepFileRenderer() {
		if (!imagesLoaded) {
			try {
				BufferedImage allNotes = ImageIO.read(new File(NOTES_DIR + "notes.png"));
				grabNoteImages(allNotes);
				holdBody = ImageIO.read(new File(NOTES_DIR + "hold.png"));
				holdEnd = ImageIO.read(new File(NOTES_DIR + "hold_cap_bottom.png"));
				rollBody = ImageIO.read(new File(NOTES_DIR + "roll.png"));
				rollEnd = ImageIO.read(new File(NOTES_DIR + "roll_cap_bottom.png"));
				
				imagesLoaded = true;
			} catch (IOException e) {
				imagesLoaded = false;
			}
		}
	}
	
	private void grabNoteImages(BufferedImage allNotes) {
		step4th = allNotes.getSubimage(0, 0, STEP_DIM, STEP_DIM);
		step8th = allNotes.getSubimage(0, STEP_DIM, STEP_DIM, STEP_DIM);
		step12th = allNotes.getSubimage(0, STEP_DIM * 2, STEP_DIM, STEP_DIM);
		step16th = allNotes.getSubimage(0, STEP_DIM * 3, STEP_DIM, STEP_DIM);
		step24th = allNotes.getSubimage(0, STEP_DIM * 4, STEP_DIM, STEP_DIM);
		step32nd = allNotes.getSubimage(0, STEP_DIM * 5, STEP_DIM, STEP_DIM);
		step48th = allNotes.getSubimage(0, STEP_DIM * 6, STEP_DIM, STEP_DIM);
		step64th = allNotes.getSubimage(0, STEP_DIM * 7, STEP_DIM, STEP_DIM);
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
	
	private void setCurrentGraphics(Graphics g) {
		currentGraphics = g;
	}
	
	public void render(Graphics g) {
		setCurrentGraphics(g);
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
		int currentY = 0 * PAGE_HEIGHT + (PAGE_HEIGHT - PRINTABLE_PAGE_HEIGHT) / 2;
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
			renderStep(steps[i], startX + stepWidth * i, startY, stepWidth, height);
		}
	}
	
	private void renderStep(Step step, int startX, int startY, int stepDim, int lineHeight) {
		switch (step.getType()) {
		case REGULAR:
			drawRegularStep(step, startX, startY, stepDim);
			break;
		case HOLDING:
			drawHoldingBack(step, startX, startY, stepDim, lineHeight);
			break;
		case HOLD_START:
			drawHoldStartBack(step, startX, startY, stepDim, lineHeight);
			drawRegularStep(step, startX, startY, stepDim);
			break;
		case HOLD_END:
			drawHoldEnd(step, startX, startY, stepDim);
			break;
		case ROLL:
			drawRollStartBack(step, startX, startY, stepDim, lineHeight);
			drawRegularStep(step, startX, startY, stepDim);
			break;
		case ROLLING:
			drawRollingBack(step, startX, startY, stepDim, lineHeight);
			break;
		case ROLL_END:
			drawRollEnd(step, startX, startY, stepDim);
			break;
		}
	}
	
	private void drawRegularStep(Step step, int x, int y, int stepDim) {
		if (imagesLoaded) {
			//do the transforms
			//note they are done in the opposite order from being called
			AffineTransform at = new AffineTransform();
			at.translate(x + stepDim / 2, y + stepDim / 2);
			at.rotate(step.getAngleFromLeft());
			at.scale((double) stepDim / STEP_DIM, (double) stepDim / STEP_DIM);
			at.translate(-STEP_DIM / 2, -STEP_DIM / 2);
			
			((Graphics2D)currentGraphics).drawImage(getStepImage(step), at, null);
		}
	}
	
	private BufferedImage getStepImage(Step step) {
		switch(step.getLength()) {
		case L4TH:
			return step4th;
		case L8TH:
			return step8th;
		case L12TH:
			return step12th;
		case L16TH:
			return step16th;
		case L24TH:
			return step24th;
		case L32ND:
			return step32nd;
		case L48TH:
			return step48th;
		default:
			return step64th;
		}
	}
	
	private void drawHoldStartBack(Step step, int x, int y, int stepDim, int lineHeight) {
		if (imagesLoaded) {
			//TODO store the HOLD dimensions properly and keep consistent with step logic
			currentGraphics.drawImage(holdBody, x, y + stepDim / 2, x + stepDim, y + lineHeight, 0, 0, HOLD_DIM, HOLD_DIM / 2, null);
		}
	}
	
	private void drawHoldingBack(Step step, int x, int y, int stepWidth, int lineHeight) {
		if (imagesLoaded) {
			//TODO store the HOLD dimensions properly and keep consistent with step logic
			currentGraphics.drawImage(holdBody, x, y, x + stepWidth, y + lineHeight, 0, 0, HOLD_DIM, HOLD_DIM/2, null);
		}
	}
	
	private void drawHoldEnd(Step step, int x, int y, int stepWidth) {
		if (imagesLoaded) {
			//TODO store the HOLD dimensions properly and keep consistent with step logic
			currentGraphics.drawImage(holdEnd, x, y, x + stepWidth, y + stepWidth / 2, 0, 0, HOLD_DIM, HOLD_DIM/2, null);
		}
	}
	
	private void drawRollStartBack(Step step, int x, int y, int stepDim, int lineHeight) {
		if (imagesLoaded) {
			//TODO store the HOLD dimensions properly and keep consistent with step logic
			currentGraphics.drawImage(rollBody, x, y + stepDim / 2, x + stepDim, y + lineHeight, 0, 0, HOLD_DIM, HOLD_DIM / 2, null);
		}
	}
	
	private void drawRollingBack(Step step, int x, int y, int stepWidth, int lineHeight) {
		if (imagesLoaded) {
			//TODO store the HOLD dimensions properly and keep consistent with step logic
			currentGraphics.drawImage(rollBody, x, y, x + stepWidth, y + lineHeight, 0, 0, HOLD_DIM, HOLD_DIM/2, null);
		}
	}
	
	private void drawRollEnd(Step step, int x, int y, int stepWidth) {
		if (imagesLoaded) {
			//TODO store the HOLD dimensions properly and keep consistent with step logic
			currentGraphics.drawImage(rollEnd, x, y, x + stepWidth, y + stepWidth / 2, 0, 0, HOLD_DIM, HOLD_DIM/2, null);
		}
	}
	
	private void drawSpaceRect(Color color, int x, int y, int width, int height) {
		currentGraphics.setColor(color);
		currentGraphics.fillRect(x, y, width, height);
	}

	@Override
	public int print(Graphics g, PageFormat pageFormat, int page) throws PrinterException {
		setCurrentGraphics(g);
		if (page < getNumberOfPages()) {
			renderPage(page);
		} else {
			return NO_SUCH_PAGE;
		}
		
		return PAGE_EXISTS;
	}
}
