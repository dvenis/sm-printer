package utilities;

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

import models.Measure;
import models.Step;
import models.StepFile;
import models.StepFileDifficultyMap;
import models.StepLine;

public class StepFileRenderer implements Printable {
	private final static double ZOOM_TICK = 0.05;
	
	private final static int COLUMN_MARGIN = 10;
	private final static int PRINTABLE_PAGE_WIDTH = 960;
	private final static int PRINTABLE_PAGE_HEIGHT = 720;
	private final static int PAGE_WIDTH = 1056;
	private final static int PAGE_HEIGHT = 816;
	
	private final static double PAGE_DPI = 96;
	private final static double PRINTABLE_DPI = 72;
	
	private final static String NOTES_DIR = "notes/stepmania5/";
	private final static int STEP_DIM = 128;
	
	private static ImageDimension holdDim;
	private static ImageDimension stepDim;
	private static ImageDimension rollDim;
	
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

	public StepFileRenderer() {
		if (!imagesLoaded) {
			try {
				BufferedImage allNotes = ImageIO.read(new File(NOTES_DIR + "notes.png"));
				stepDim = new ImageDimension(STEP_DIM, STEP_DIM);
				grabNoteImages(allNotes);
				
				holdBody = ImageIO.read(new File(NOTES_DIR + "hold.png"));
				holdEnd = ImageIO.read(new File(NOTES_DIR + "hold_cap_bottom.png"));
				holdDim = new ImageDimension(holdBody.getWidth(), holdBody.getHeight());
				
				rollBody = ImageIO.read(new File(NOTES_DIR + "roll.png"));
				rollEnd = ImageIO.read(new File(NOTES_DIR + "roll_cap_bottom.png"));
				rollDim = new ImageDimension(rollBody.getWidth(), rollBody.getHeight());
				
				imagesLoaded = true;
			} catch (IOException e) {
				imagesLoaded = false;
			}
		}
	}
	
	private void grabNoteImages(BufferedImage allNotes) {
		step4th = allNotes.getSubimage(0, 0, stepDim.width, stepDim.height);
		step8th = allNotes.getSubimage(0, stepDim.height, stepDim.width, stepDim.height);
		step12th = allNotes.getSubimage(0, stepDim.height * 2, stepDim.width, stepDim.height);
		step16th = allNotes.getSubimage(0, stepDim.height * 3, stepDim.width, stepDim.height);
		step24th = allNotes.getSubimage(0, stepDim.height * 4, stepDim.width, stepDim.height);
		step32nd = allNotes.getSubimage(0, stepDim.height * 5, stepDim.width, stepDim.height);
		step48th = allNotes.getSubimage(0, stepDim.height * 6, stepDim.width, stepDim.height);
		step64th = allNotes.getSubimage(0, stepDim.height * 7, stepDim.width, stepDim.height);
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
	
	public void resetZoom() {
		zoom = 1.0;
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
	
	public StepFile getStepFile() {
		return stepFile;
	}
	
	public StepFileDifficultyMap getDifficulty() {
		return difficulty;
	}
	
	public int getNumberOfPages() {		
		return StepFile.calculateNumberOfPages(difficulty, Settings.columnsPerPage, Settings.measuresPerColumn);
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
		((Graphics2D)g).scale(zoom, zoom);
		setCurrentGraphics(g);
		if (stepFile != null) {

			int measuresPerPage = Settings.measuresPerColumn * Settings.columnsPerPage;
			int pages = (int)Math.ceil((double)difficulty.getNumberOfMeasures() / measuresPerPage); 
			for (int i = 0; i < pages; i++) {
				renderPage(i, 0, i * PAGE_HEIGHT);
			}
		}
	}
	
	private void renderPage(int pageNumber, int startX, int startY) {		
		int columnWidth = PRINTABLE_PAGE_WIDTH / Settings.columnsPerPage;
		//start at the margins
		int currentX = startX + (PAGE_WIDTH - PRINTABLE_PAGE_WIDTH) / 2;
		int currentY = startY + (PAGE_HEIGHT - PRINTABLE_PAGE_HEIGHT) / 2;
		int measuresPerPage = Settings.measuresPerColumn * Settings.columnsPerPage;
		
		//for testing
		drawSpaceRect(Color.BLUE, currentX, currentY, PRINTABLE_PAGE_WIDTH, PRINTABLE_PAGE_HEIGHT);
		
		//page number
		currentGraphics.drawString(Integer.toString(pageNumber + 1), currentX, currentY);
		
		for (int i = 0; i < Settings.columnsPerPage; i++) {
			renderColumn(measuresPerPage * pageNumber + i * Settings.measuresPerColumn, currentX, currentY, columnWidth, PRINTABLE_PAGE_HEIGHT);
			currentX += columnWidth;
		}
	}
	
	private void renderColumn(int measureStartIndex, int startX, int startY, int width, int height) {
		int currentX = startX + COLUMN_MARGIN;
		int currentY = startY + COLUMN_MARGIN;
		int usableWidth = width - 2 * COLUMN_MARGIN;
		int usableHeight = height - 2 * COLUMN_MARGIN; 
		int measureHeight = usableHeight / Settings.measuresPerColumn;
		
		List<Measure> measures = difficulty.getMeasures();
		
		//for testing
		drawSpaceRect(Color.GREEN, currentX, currentY, width - 2 * COLUMN_MARGIN, height - 2 * COLUMN_MARGIN);
		
		for (int i = 0; i < Settings.measuresPerColumn; i++) {
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
			at.scale((double) stepDim / this.stepDim.width, (double) stepDim / this.stepDim.height);
			at.translate(-this.stepDim.width / 2, -this.stepDim.height / 2);
			
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
			currentGraphics.drawImage(holdBody, x, y + stepDim / 2, x + stepDim, y + lineHeight,
					0, 0, holdDim.width, holdDim.height, null);
		}
	}
	
	private void drawHoldingBack(Step step, int x, int y, int stepWidth, int lineHeight) {
		if (imagesLoaded) {
			//TODO store the HOLD dimensions properly and keep consistent with step logic
			currentGraphics.drawImage(holdBody, x, y, x + stepWidth, y + lineHeight, 
					0, 0, holdDim.width, holdDim.height, null);
		}
	}
	
	private void drawHoldEnd(Step step, int x, int y, int stepWidth) {
		if (imagesLoaded) {
			//TODO store the HOLD dimensions properly and keep consistent with step logic
			currentGraphics.drawImage(holdEnd, x, y, x + stepWidth, y + stepWidth / 2,
					0, 0, holdDim.width, holdDim.height, null);
		}
	}
	
	private void drawRollStartBack(Step step, int x, int y, int stepDim, int lineHeight) {
		if (imagesLoaded) {
			//TODO store the HOLD dimensions properly and keep consistent with step logic
			currentGraphics.drawImage(rollBody, x, y + stepDim / 2, x + stepDim, y + lineHeight,
					0, 0, rollDim.width, rollDim.height, null);
		}
	}
	
	private void drawRollingBack(Step step, int x, int y, int stepWidth, int lineHeight) {
		if (imagesLoaded) {
			//TODO store the HOLD dimensions properly and keep consistent with step logic
			currentGraphics.drawImage(rollBody, x, y, x + stepWidth, y + lineHeight, 
					0, 0, rollDim.width, rollDim.height, null);
		}
	}
	
	private void drawRollEnd(Step step, int x, int y, int stepWidth) {
		if (imagesLoaded) {
			//TODO store the HOLD dimensions properly and keep consistent with step logic
			currentGraphics.drawImage(rollEnd, x, y, x + stepWidth, y + stepWidth / 2, 
					0, 0, rollDim.width, rollDim.height, null);
		}
	}
	
	private void drawSpaceRect(Color color, int x, int y, int width, int height) {
		currentGraphics.setColor(color);
		currentGraphics.fillRect(x, y, width, height);
	}

	@Override
	public int print(Graphics g, PageFormat pageFormat, int page) throws PrinterException {
		((Graphics2D)g).scale(PRINTABLE_DPI / PAGE_DPI, PRINTABLE_DPI / PAGE_DPI);
		setCurrentGraphics(g);
		if (page < getNumberOfPages()) {
			renderPage(page, 0, 0);
		} else {
			return NO_SUCH_PAGE;
		}
		
		return PAGE_EXISTS;
	}
}
