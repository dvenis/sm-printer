package entities;

import utilities.Resources;
import utilities.SettingsInstance;

public class Roll extends Hold {
	public Roll(SettingsInstance settings, int x, int y, int width, int height) {
		super(settings, x, y, width, height);
		
		body = Resources.getInstance().rollBody;
		end = Resources.getInstance().rollEnd;
	}
}
