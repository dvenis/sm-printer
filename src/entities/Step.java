package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import utilities.Resources;

import models.Step.Type;

public class Step extends Entity {
	protected models.Step step;
	
	protected Hold hold;
	protected Hold[] currentHolds;
	protected int holdIndex;
	
	protected BufferedImage stepImage;
	
	public Step(models.Step step, Hold[] currentHolds, int holdIndex,
			int x, int y, int sideLength) {
		super(x, y, sideLength, sideLength);
		
		this.step = step;
		this.currentHolds = currentHolds;
		this.holdIndex = holdIndex;
		
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
		if (step.getType() == Type.ROLL) { 
			hold = new Roll(x, y, width, height);
			hold.start();
			currentHolds[holdIndex] = hold;// new Roll(x, y + width / 2, width, height);
		} else if (step.getType() == Type.HOLD_START) {
			hold = new Freeze(x, y, width, height);
			hold.start();
			currentHolds[holdIndex] = hold; //new Freeze(x, y + width / 2, width, height);
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
				//hold.extend(height);
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
			at.translate(x + width / 2, y + height / 2);
			at.rotate(step.getAngleFromLeft());
			at.scale(width / imageWidth, height / imageHeight);
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
		highlightRegion(g, Color.GRAY);
	}
}
