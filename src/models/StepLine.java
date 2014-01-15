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
			steps[i] = charToStep(rawData.charAt(i));
		}
		
//		left = charToStep(rawData.charAt(0));
//		down = charToStep(rawData.charAt(1));
//		up = charToStep(rawData.charAt(2));
//		right = charToStep(rawData.charAt(3));
	}
	
	private Step charToStep(char c) {
		switch (c) {
		case '1':
			return new Step(Step.Type.REGULAR);
		case '2':
			return new Step(Step.Type.HOLD_START);
		case '3':
			return new Step(Step.Type.HOLD_END);
		case '4':
			return new Step(Step.Type.ROLL);
		case 'M':
			return new Step(Step.Type.MINE);
		case 'L':
			return new Step(Step.Type.LIFT);
		case 'F':
			return new Step(Step.Type.FAKE);
		default:
			return new Step(Step.Type.NONE);
		}
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
