package entities;

import utilities.Settings;

/**
 * An entity that represents the "freeze" step type in step mania. The "freeze" step type is a 
 * hold where the user must hold the button down throughout the hold.
 * 
 * @author Dan
 *
 */

public class Freeze extends Hold {
	public Freeze(Settings settings, models.Step step, int x, int y, int width, int height) {
		super(settings, step, x, y, width, height);
	}
}
