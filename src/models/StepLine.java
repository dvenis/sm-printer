package models;

public class StepLine {
	private Step[] steps;
//	private Step left;
//	private Step right;
//	private Step up;
//	private Step down;
	
	public StepLine(String rawData) {
		steps = new Step[rawData.trim().length()];
		for (int i = 0; i < steps.length; i++) {
			steps[i] = charAndIndexToStep(rawData.charAt(i), i);
		}
	}
	
	private Step charAndIndexToStep(char c, int index) {
		Step.Type type = charToType(c);
		Step.Orientation orientation = indexToOrientation(index);
		return new Step(type, orientation);
	}
	
	private Step.Type charToType(char c) {
		switch (c) {
		case '1':
			return Step.Type.REGULAR;
		case '2':
			return Step.Type.HOLD_START;
		case '3':
			return Step.Type.HOLD_END;
		case '4':
			return Step.Type.ROLL;
		case 'M':
			return Step.Type.MINE;
		case 'L':
			return Step.Type.LIFT;
		case 'F':
			return Step.Type.FAKE;
		default:
			return Step.Type.NONE;
		}
	}
	
	private Step.Orientation indexToOrientation(int index) {
		if (steps.length == 4) {
			switch (index) {
			case 0:
				return Step.Orientation.LEFT;
			case 1:
				return Step.Orientation.DOWN;
			case 2:
				return Step.Orientation.UP;
			case 3:
				return Step.Orientation.RIGHT;
			}
		}
		return Step.Orientation.LEFT;
	}
	
//	public StepLine(Step left, Step right, Step up, Step down) {
//		this.left = left;
//		this.right = right;
//		this.up = up;
//		this.down = down;
//	}
	
	public Step[] getSteps() {
		return steps;
	}

//	public Step getLeft() {
//		return left;
//	}
//
//	public void setLeft(Step left) {
//		this.left = left;
//	}
//
//	public Step getRight() {
//		return right;
//	}
//
//	public void setRight(Step right) {
//		this.right = right;
//	}
//
//	public Step getUp() {
//		return up;
//	}
//
//	public void setUp(Step up) {
//		this.up = up;
//	}
//
//	public Step getDown() {
//		return down;
//	}
//
//	public void setDown(Step down) {
//		this.down = down;
//	}
		
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Step step : steps) {
			sb.append(getStepRepr(step));
		}
//		sb.append(getStepRepr(left));
//		sb.append(getStepRepr(down));
//		sb.append(getStepRepr(up));
//		sb.append(getStepRepr(right));
		return sb.toString();
	}
	
	private String getStepRepr(Step step) {
		if (step == null) {
			return "";
		}
		
		switch (step.getType()) {
		case REGULAR:
		case HOLD_START:
			return "#";
		default: 
			return " ";
		}
	}
}
