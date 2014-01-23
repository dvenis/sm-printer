package entities;

import utilities.Resources;

public class Roll extends Hold {
	public Roll(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		body = Resources.getInstance().rollBody;
		end = Resources.getInstance().rollEnd;
	}
	
//	protected boolean started = false;
//	protected boolean ended = false;
//	
//	protected BufferedImage body;
//	protected BufferedImage end;
//	
//	public Roll(int x, int y, int width, int height) {
//		super(x, y, width, height);
//		
//		body = Resources.getInstance().rollBody;
//		end = Resources.getInstance().rollEnd;
//	}
//	
//	@Override
//	public void extend(int byHeight) {
//		height += byHeight;
//	}
//
//	@Override
//	public void start() {
//		started = true;
//	}
//	
//	@Override
//	public void end() {
//		ended = true;
//	}
//
//	@Override
//	public void draw(Graphics g) {
//		
//		double scaledImageHeight = (double) width / body.getWidth() * body.getHeight();
//		double currentX = x;
//		double currentY = y;
//		double usableHeight = height;
//		if (started) {
//			currentY += width / 2.0;
//			usableHeight -= width / 2.0;
//		}
//		if (ended) {
//			usableHeight -= width / 2.0;
//		}
//
//		int numberOfFullImages = (int)(usableHeight / scaledImageHeight);
//		for (int i = 0; i < numberOfFullImages; i++) {
//			g.drawImage(body, (int)currentX,  (int)currentY, (int)currentX + width, (int)(currentY + scaledImageHeight), 0, 0, body.getWidth(), body.getHeight(), null);
//			currentY += scaledImageHeight;
//		}
//		
//		double leftOverHeight = usableHeight - numberOfFullImages * scaledImageHeight;
//		double fractionHeight = leftOverHeight / scaledImageHeight;
//		g.drawImage(body, (int)currentX,  (int)currentY, (int)currentX + width, (int)(currentY + leftOverHeight), 0, 0, body.getWidth(), (int)(body.getHeight() * fractionHeight), null);
//		currentY += leftOverHeight;
//
//		if (ended) {
//			g.drawImage(end, (int)currentX, (int)currentY, (int)currentX + width, (int)(currentY + scaledImageHeight), 0, 0, end.getWidth(), end.getHeight(), null);
//		}
//	}
}
