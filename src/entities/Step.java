package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import utilities.Resources;
import utilities.Settings;
import models.Step.Type;

public class Step extends Entity {
	protected models.Step step;
	
	//same thing as super.width; just a more intuitive name
	protected int sideLength; 
	
	protected Hold hold;
	protected Hold[] currentHolds;
	protected int holdIndex;
	
	protected BufferedImage stepImage;
	
	public Step(Settings settings, models.Step step, Hold[] currentHolds, int holdIndex,
			int x, int y, int sideLength, int lineHeight) {
		super(settings, x, y, sideLength, lineHeight);
		
		this.step = step;
		this.currentHolds = currentHolds;
		this.holdIndex = holdIndex;
		this.sideLength = sideLength;
		
		if (step.getType() == models.Step.Type.REGULAR
				|| step.getType() == models.Step.Type.HOLD_START
				|| step.getType() == models.Step.Type.ROLL) {
			stepImage = getStepLengthImage(step);
		} else if (step.getType() == models.Step.Type.MINE){ 
			stepImage = Resources.getInstance().mine;
		}
		updateHolds();
	}
	
	private void updateHolds() {
		//TODO clean up this atrocity
		if (step.getType() == Type.ROLL) { 
			hold = new Roll(settings, x, y, width, height);
			hold.start();
			currentHolds[holdIndex] = hold;
		} else if (step.getType() == Type.HOLD_START) {
			hold = new Freeze(settings, x, y, width, height);
			hold.start();
			currentHolds[holdIndex] = hold;
		} else if (step.getType() == Type.ROLL_END
				|| step.getType() == Type.HOLD_END) { 
			if (currentHolds[holdIndex] != null) {
				currentHolds[holdIndex].extend(height);
				currentHolds[holdIndex].end();
				currentHolds[holdIndex] = null;
			}
		} else if (step.getType() == Type.ROLLING
				|| step.getType() == Type.HOLDING) {
			if (currentHolds[holdIndex] != null) {
				currentHolds[holdIndex].extend(height);
			} else {
				if (step.getType() == Type.ROLLING){ 
					hold = new Roll(settings, x, y, width, height);
					currentHolds[holdIndex] = hold;
				} else if (step.getType() == Type.HOLDING) { 
					hold = new Freeze(settings, x, y, width, height);
					currentHolds[holdIndex] = hold;
				}
			}
		}
	}
	
	private BufferedImage getStepLengthImage(models.Step step) {
		Resources r = Resources.getInstance();
		switch(step.getLength()) {
		case L1ST:
		case L4TH:
			return r.step4th;
		case L8TH:
			return r.step8th;
		case L12TH:
			return r.step12th;
		case L16TH:
			return r.step16th;
		case L24TH:
			return r.step24th;
		case L32ND:
			return r.step32nd;
		case L48TH:
			return r.step48th;
		default:
			return r.step64th;
		}
	}

	@Override
	public void draw(Graphics g) {
		if (stepImage != null) {
			double imageWidth = (double)stepImage.getWidth();
			double imageHeight = (double)stepImage.getHeight();
			
			AffineTransform at = new AffineTransform();
			at.translate(x + sideLength / 2, y + sideLength / 2);
			at.rotate(step.getAngleFromLeft());
			at.scale(sideLength / imageWidth, sideLength / imageHeight);
			at.translate(-imageWidth / 2, -imageHeight / 2);
			
			((Graphics2D)g).drawImage(stepImage, at, null);
		}
		if (hold != null) {
			hold.draw(g);
		}
	}
	
	@Override
	public void drawMidground(Graphics g) {
		if (hold != null) {
			hold.drawMidground(g);
		}
	}

	@Override
	public void drawBackground(Graphics g) {
		//nothing to draw in background
	}
}
