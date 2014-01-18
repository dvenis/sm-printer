package models;

public class Step {
	
	public enum Type {
		NONE, REGULAR, HOLD_START, HOLD_END, ROLL, MINE, LIFT, FAKE
	}
	
	public enum Orientation {
		LEFT, RIGHT, DOWN, UP
	}

	private Type type;
	private Orientation orientation;
	
	public Step(Type type) {
		this(type, Orientation.LEFT);
	}
	
	public Step(Type type, Orientation orientation) {
		this.type = type;
		this.orientation = orientation;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public Orientation getOrientation() {
		return orientation;
	}
	
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}	
}
