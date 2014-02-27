package entities;

import utilities.Resources;
import utilities.Settings;

public class Roll extends Hold {
	public Roll(Settings settings, int x, int y, int width, int height) {
		super(settings, x, y, width, height);
		
		body = Resources.getInstance().rollBody;
		end = Resources.getInstance().rollEnd;
	}
}
