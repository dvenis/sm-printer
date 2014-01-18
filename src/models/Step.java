package models;

import java.awt.Color;

public class Step {
	
	public enum Type {
		NONE, REGULAR, HOLD_START, HOLD_END, ROLL, MINE, LIFT, FAKE,
		//custom definitions
		HOLDING, ROLLING
	}
	
	public enum Orientation {
		LEFT, RIGHT, DOWN, UP
	}

	private Type type;
	private Orientation orientation;
	
	private double angleFromLeft;
	private Color filterColor;
	
	public Step(Type type) {
		this(type, Orientation.LEFT);
	}
	
	public Step(Type type, Orientation orientation) {
		this.type = type;
		this.orientation = orientation;
		
		calculateAngleFromLeft();
		calculateFilterColor();
	}
	
	private void calculateAngleFromLeft() {
		switch (orientation) {
		case RIGHT:
			angleFromLeft = Math.PI;
			break;
		case DOWN:
			angleFromLeft = 3 * Math.PI / 2;
			break;
		case UP:
			angleFromLeft = Math.PI / 2;
			break;
		default:
			angleFromLeft = 0;
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
	
	public double getAngleFromLeft() {
		return angleFromLeft;
	}
	
	public Color getFilterColor() {
		return filterColor;
	}
}
