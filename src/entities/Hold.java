package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilities.Settings;

public abstract class Hold extends Entity implements Holdable{
	protected boolean started = false;
	protected boolean ended = false;
	
	protected BufferedImage body;
	protected BufferedImage end;
	
	public Hold(Settings settings, int x, int y, int width, int height) {
		super(settings, x, y, width, height);
	}
	
	@Override
	public void extend(int byHeight) {
		height += byHeight;
	}

	@Override
	public void start() {
		started = true;
	}
	
	@Override
	public void end() {
		ended = true;
	}

	@Override
	public void draw(Graphics g) {
		//nothing to draw in foreground
	}
	
	@Override
	public void drawMidground(Graphics g) {
		//TODO refactor this method to work as expected
		double scaledImageHeight = (double) width / body.getWidth() * body.getHeight();
		double currentX = x;
		double currentY = y;
		double usableHeight = height;
		if (started) {
			currentY += width / 2.0;
			usableHeight -= width / 2.0;
		}
		//not sure why this works: it just does
		if (ended && started) {
			usableHeight -= width / 2.0;
		}

		int numberOfFullImages = (int)(usableHeight / scaledImageHeight);
		for (int i = 0; i < numberOfFullImages; i++) {
			g.drawImage(body, (int)currentX,  (int)currentY, (int)currentX + width, (int)(currentY + scaledImageHeight), 0, 0, body.getWidth(), body.getHeight(), null);
			currentY += scaledImageHeight;
		}
		
		double leftOverHeight = usableHeight - numberOfFullImages * scaledImageHeight;
		double fractionHeight = leftOverHeight / scaledImageHeight;
		g.drawImage(body, (int)currentX,  (int)currentY, (int)currentX + width, (int)(currentY + leftOverHeight), 0, 0, body.getWidth(), (int)(body.getHeight() * fractionHeight), null);
		currentY += leftOverHeight;

		if (ended) {
			g.drawImage(end, (int)currentX, (int)currentY, (int)currentX + width, (int)(currentY + scaledImageHeight), 0, 0, end.getWidth(), end.getHeight(), null);
		}	
	}
	
	@Override
	public void drawBackground(Graphics g) {
		//nothing to draw in background
	}
}
