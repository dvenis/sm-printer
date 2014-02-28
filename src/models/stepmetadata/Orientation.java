package models.stepmetadata;

public enum Orientation {
	LEFT(Math.PI / 2), RIGHT(3 * Math.PI / 2), DOWN(0), UP(Math.PI),
	//pump it up
	DOWN_LEFT(0), UP_LEFT(Math.PI / 2), UP_RIGHT(Math.PI), DOWN_RIGHT(3 * Math.PI / 2), CENTER(0),
	//custom
	NONE(0);
	
	private double rotationAngle;
	
	private Orientation(double rotationAngle) {
		this.rotationAngle = rotationAngle;
	}
	
	public double getRotationAngle() {
		return rotationAngle;
	}
}
