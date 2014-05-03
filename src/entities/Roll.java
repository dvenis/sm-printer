package entities;

import utilities.Settings;

/**
 * An entity that represents the "roll" step in step mania. The "roll" type requires
 * the user to press the button repeatedly while the hold is active.
 * @author Dan
 *
 */

public class Roll extends Hold {
	public Roll(Settings settings, models.Step step, int x, int y, int width, int height) {
		super(settings, step, x, y, width, height);
	}
}
