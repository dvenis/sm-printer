package entities;

import java.awt.Graphics;

public abstract class Container extends Entity {
	protected Entity[] children;
	
	public Container(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	protected void drawChildren(Graphics g) {
		for (Entity child : children) {
			child.draw(g);
		}
	}
}
