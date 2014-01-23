package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Step extends Entity {
	protected models.Step step;
	
	protected BufferedImage stepImage;
	
	public Step(models.Step step, int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.step = step;
	}

	@Override
	public void draw(Graphics g) {
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
