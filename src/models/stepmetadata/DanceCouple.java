package models.stepmetadata;

public class DanceCouple extends NotesType {

	@Override
	public Orientation getStepOrientation(int lineIndex) {
		switch(lineIndex) {
		case 0:
		case 4:
			return Orientation.LEFT;
		case 1:
		case 5:
			return Orientation.DOWN;
		case 2:
		case 6:
			return Orientation.UP;
		case 3:
		case 7:
			return Orientation.RIGHT;
		default:
			return Orientation.NONE;
		}
	}

	@Override
	public int getLineLength() {
		return 8;
	}

	@Override
	public String getMetaCode() {
		return "dance-couple";
	}

	@Override
	public String toString() {
		return "Dance Couple";
	}
	
}
