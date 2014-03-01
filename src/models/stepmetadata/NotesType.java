package models.stepmetadata;

import models.stepmetadata.notestypes.DanceCouple;
import models.stepmetadata.notestypes.DanceDouble;
import models.stepmetadata.notestypes.DanceSingle;
import models.stepmetadata.notestypes.DanceSolo;
import models.stepmetadata.notestypes.PumpCouple;
import models.stepmetadata.notestypes.PumpDouble;
import models.stepmetadata.notestypes.PumpSingle;

public abstract class NotesType {
	protected static final NotesType[] notesTypes = new NotesType[] {
		new DanceSingle(PlayType.DANCE),
		new DanceDouble(PlayType.DANCE),
		new DanceCouple(PlayType.DANCE),
		new DanceSolo(PlayType.DANCE),
		new PumpSingle(PlayType.PUMP),
		new PumpDouble(PlayType.PUMP),
		new PumpCouple(PlayType.PUMP),
	};
	
	public static enum PlayType { DANCE, PUMP };
	
	public static NotesType fromMetaCode(String name) {
		for (NotesType notesType : notesTypes) {
			if (name.equals(notesType.getMetaCode())) {
				return notesType;
			}
		}
		System.err.println("The specified dance type could not be found! (" + name + ")");
		return null;
	}
	
	public NotesType(PlayType playType) {
		this.playType = playType;
	}
	
	protected PlayType playType;
	
	public PlayType getPlayType() {
		return playType;
	}
	
	public abstract String getMetaCode(); //code found inside the simfile
	public abstract Orientation getStepOrientation(int stepIndex);
	public abstract int getLineLength();
}
