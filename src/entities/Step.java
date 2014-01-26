package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import utilities.Resources;

public class Step extends Entity {
	protected models.Step step;
	
	protected BufferedImage stepImage;
	
	public Step(models.Step step, int x, int y, int sideLength) {
		super(x, y, sideLength, sideLength);
		
		this.step = step;
		if (step.getType() != models.Step.Type.NONE) { 
			stepImage = getStepImage(step);
		}
	}
	
	private BufferedImage getStepImage(models.Step step) {
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
			double imageWidth = (double) stepImage.getWidth();
			double imageHeight = (double) stepImage.getHeight();
			
			AffineTransform at = new AffineTransform();
			at.translate(x + width / 2, y + height / 2);
			at.rotate(step.getAngleFromLeft());
			at.scale(width / imageWidth, height / imageHeight);
			at.translate(-imageWidth / 2, -imageHeight / 2);
			
			((Graphics2D)g).drawImage(stepImage, at, null);
		}
	}

	@Override
	public void drawBackground(Graphics g) {
		highlightRegion(g, Color.GRAY);
	}
}
