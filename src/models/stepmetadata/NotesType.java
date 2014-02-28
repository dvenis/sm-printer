package models.stepmetadata;

public abstract class NotesType {
	private static final NotesType[] notesTypes = new NotesType[] {
		new DanceSingle(),
		new DanceDouble(),
		new DanceCouple(),
		new DanceSolo()
	};
	
	public static NotesType fromMetaCode(String name) {
		for (NotesType notesType : notesTypes) {
			if (name.equals(notesType.getMetaCode())) {
				return notesType;
			}
		}
		System.err.println("The specified dance type could not be found! (" + name + ")");
		return null;
	}
	
	public abstract String getMetaCode(); //code found inside the simfile
	public abstract Orientation getStepOrientation(int stepIndex);
	public abstract int getLineLength();
}
