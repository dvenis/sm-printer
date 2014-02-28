package models;

import java.awt.Color;

import models.stepmetadata.Timing;
import models.stepmetadata.Type;
import models.stepmetadata.Orientation;

public class Step {

	private Type type;
	private Orientation orientation;
	private Timing timing;
	
	//private double angleFromDown;
	//private Color filterColor;
	
//	
//	public Step(Type type, Orientation orientation) {
//		this(type, orientation, null);
//	}
	
	public Step(Type type, Orientation orientation, Timing timing) {
		this.type = type;
		this.orientation = orientation;
		this.timing = timing;
		
		//calculateAngleFromDown();
	//	calculateFilterColor();
	}
	
//	private void calculateAngleFromDown() {
//		switch (orientation) {
//		case RIGHT:
//			angleFromDown = 3 * Math.PI / 2;
//			break;
//		case DOWN:
//			angleFromDown = 0;
//			break;
//		case UP:
//			angleFromDown = Math.PI;
//			break;
//		default:
//			angleFromDown = Math.PI / 2;
//		}
//	}
	
//	private void calculateFilterColor() {
//		filterColor = Color.RED;
//	}

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
	
	public Timing getLength() {
		return timing;
	}
	
//	public double getAngleFromLeft() {
//		return angleFromDown;
//	}
//	
//	public Color getFilterColor() {
//		return filterColor;
//	}
}
