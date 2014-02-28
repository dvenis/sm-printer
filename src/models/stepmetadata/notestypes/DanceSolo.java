package models.stepmetadata.notestypes;

import models.stepmetadata.NotesType;
import models.stepmetadata.Orientation;

public class DanceSolo extends NotesType {

	@Override
	public Orientation getStepOrientation(int lineIndex) {
		switch(lineIndex) {
		case 0: return Orientation.LEFT;
		case 1: return Orientation.DOWN;
		case 2: return Orientation.UP;
		case 3: return Orientation.RIGHT;
		default: return Orientation.NONE;
		}
	}

	@Override
	public int getLineLength() { 
		return 4;
	}

	@Override
	public String getMetaCode() {
		return "dance-solo";
	}

	@Override
	public String toString() {
		return "Dance Solo";
	}
	
}
