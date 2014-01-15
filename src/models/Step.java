package models;

public class Step {
	
	public enum Type {
		NONE, REGULAR, HOLD_START, HOLD_END, ROLL, MINE, LIFT, FAKE
	}

	private Type type;
	
	public Step(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
}
