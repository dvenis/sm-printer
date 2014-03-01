package models.stepmetadata.notestypes;

import models.stepmetadata.NotesType;
import models.stepmetadata.Orientation;

public class PumpSingle extends NotesType {

	public PumpSingle(PlayType playType) {
		super(playType);
	}

	@Override
	public Orientation getStepOrientation(int lineIndex) {
		switch(lineIndex) {
		case 0:
			return Orientation.DOWN_LEFT;
		case 1:
			return Orientation.UP_LEFT;
		case 2:
			return Orientation.CENTER;
		case 3:
			return Orientation.UP_RIGHT;
		case 4:
			return Orientation.DOWN_RIGHT;
		default:
			return Orientation.NONE;
		}
	}

	@Override
	public int getLineLength() {
		return 5;
	}

	@Override
	public String getMetaCode() {
		return "pump-single";
	}

	@Override
	public String toString() {
		return "Pump Single";
	}
	
}
