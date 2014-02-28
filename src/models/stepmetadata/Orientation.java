package models.stepmetadata;

public enum Orientation {
	LEFT(Math.PI / 2), RIGHT(3 * Math.PI / 2), DOWN(0), UP(Math.PI),
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
