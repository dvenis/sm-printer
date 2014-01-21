package models;

import java.awt.Color;

public class Step {
	
	public enum Type {
		NONE, REGULAR, HOLD_START, HOLD_END, ROLL, MINE, LIFT, FAKE,
		//custom definitions
		HOLDING, ROLLING, ROLL_END
	}
	
	public enum Orientation {
		LEFT, RIGHT, DOWN, UP
	}

	private Type type;
	private Orientation orientation;
	private StepLine.Timing length;
	
	private double angleFromDown;
	private Color filterColor;
	
	
	public Step(Type type, Orientation orientation) {
		this(type, orientation, null);
	}
	
	public Step(Type type, Orientation orientation, StepLine.Timing length) {
		this.type = type;
		this.orientation = orientation;
		this.length = length;
		
		calculateAngleFromDown();
		calculateFilterColor();
	}
	
	private void calculateAngleFromDown() {
		switch (orientation) {
		case RIGHT:
			angleFromDown = 3 * Math.PI / 2;
			break;
		case DOWN:
			angleFromDown = 0;
			break;
		case UP:
			angleFromDown = Math.PI;
			break;
		default:
			angleFromDown = Math.PI / 2;
		}
	}
	
	private void calculateFilterColor() {
		filterColor = Color.RED;
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
	
	public StepLine.Timing getLength() {
		return length;
	}
	
	public double getAngleFromLeft() {
		return angleFromDown;
	}
	
	public Color getFilterColor() {
		return filterColor;
	}
}
