package entities;

import utilities.Resources;

public class Roll extends Hold {
	public Roll(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		body = Resources.getInstance().rollBody;
		end = Resources.getInstance().rollEnd;
	}
}
