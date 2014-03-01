package entities;

import utilities.Resources;
import utilities.Settings;

public class Freeze extends Hold {
	public Freeze(Settings settings, models.Step step, int x, int y, int width, int height) {
		super(settings, step, x, y, width, height);
		
//		body = Resources.getInstance().freezeBody;
//		end = Resources.getInstance().freezeEnd;
	}
}
