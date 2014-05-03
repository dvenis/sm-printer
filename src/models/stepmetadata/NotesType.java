package models.stepmetadata;

import models.stepmetadata.notestypes.DanceCouple;
import models.stepmetadata.notestypes.DanceDouble;
import models.stepmetadata.notestypes.DanceSingle;
import models.stepmetadata.notestypes.DanceSolo;
import models.stepmetadata.notestypes.PumpCouple;
import models.stepmetadata.notestypes.PumpDouble;
import models.stepmetadata.notestypes.PumpSingle;

public abstract class NotesType implements Comparable<NotesType> {
	protected static final NotesType[] notesTypes = new NotesType[] {
		new DanceSingle(GameMode.DANCE),
		new DanceDouble(GameMode.DANCE),
		new DanceCouple(GameMode.DANCE),
		new DanceSolo(GameMode.DANCE),
		new PumpSingle(GameMode.PUMP),
		new PumpDouble(GameMode.PUMP),
		new PumpCouple(GameMode.PUMP),
	};
	
	public static enum GameMode { DANCE, PUMP };
	
	public static NotesType fromMetaCode(String name) {
		for (NotesType notesType : notesTypes) {
			if (name.equals(notesType.getMetaCode())) {
				return notesType;
			}
		}
		System.err.println("The specified dance type could not be found! (" + name + ")");
		return null;
	}
	
	public NotesType(GameMode gameMode) {
		this.gameMode = gameMode;
	}
	
	protected GameMode gameMode;
	
	public GameMode getGameMode() {
		return gameMode;
	}
	
	@Override
	public int compareTo(NotesType notesType) {
		return toString().compareTo(notesType.toString());
	}
	
	public abstract String getMetaCode(); //code found inside the simfile
	public abstract Orientation getStepOrientation(int stepIndex);
	public abstract int getLineLength();
}
