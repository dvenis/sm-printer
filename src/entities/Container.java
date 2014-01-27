package entities;

import java.awt.Graphics;

public abstract class Container extends Entity {
	protected Entity[] children;
	
	public Container(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	protected void drawChildren(Graphics g) {
		if (children !=  null) {
			for (Entity child : children) {
				child.draw(g);
			}
		}
	}
	
	protected void drawChildrenMidground(Graphics g) {
		if (children != null) {
			for (Entity child : children) {
				child.drawMidground(g);
			}
		}
	}
	
	protected void drawChildrenBackground(Graphics g) {
		if (children != null) {
			for (Entity child : children) {
				child.drawBackground(g);
			}
		}
	}
}