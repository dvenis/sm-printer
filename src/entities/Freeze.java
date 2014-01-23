package entities;

import utilities.Resources;

public class Freeze extends Hold {
	public Freeze(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		body = Resources.getInstance().freezeBody;
		end = Resources.getInstance().freezeEnd;
	}
}
