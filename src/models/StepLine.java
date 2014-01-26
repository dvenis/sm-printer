package models;

public class StepLine {
	public enum Timing {
		L1ST, L4TH, L8TH, L12TH, L16TH, L24TH, L32ND, L48TH, L64TH
	}
	
	private Step[] steps;
	private Timing timing;
	
	public StepLine(String rawData, StepLine previousLine, int lineIndex, int numberLinesInMeasure) {
		timing = calculateLength(lineIndex, numberLinesInMeasure);
		
		steps = new Step[rawData.trim().length()];
		if (previousLine != null && previousLine.steps != null) {
			for (int i = 0; i < steps.length; i++) {
				steps[i] = makeStep(rawData.charAt(i), i, previousLine.getSteps()[i]);
			}
		} else {
			for (int i = 0; i < steps.length; i++) {
				steps[i] = makeStep(rawData.charAt(i), i, null);
			}
		}
	}
	
	private Step makeStep(char rawCharacter, int stepIndex, Step previousStep) {
		Step.Type type = charToType(rawCharacter, previousStep);
		Step.Orientation orientation = indexToOrientation(stepIndex);
		//Step.Length length = calculateLength(lineIndex, numberLinesInMeasure);
		return new Step(type, orientation, timing);
	}
	
	
	private Timing calculateLength(int lineIndex, int numberLinesInMeasure) {
		int length;
		if (lineIndex != 0) {
			int gcd = getGCD(numberLinesInMeasure, lineIndex);
			length = numberLinesInMeasure / gcd;
		} else {
			return Timing.L1ST;
		}

		switch(length) {
		case 8:
			return Timing.L8TH;
		case 12:
			return Timing.L12TH;
		case 16:
			return Timing.L16TH;
		case 24:
			return Timing.L24TH;
		case 32:
			return Timing.L32ND;
		case 48:
			return Timing.L48TH;
		case 64:
			return Timing.L64TH;
		default:
			return Timing.L4TH;
		}
	}
	
	private int getGCD(int a, int b) {
		while (b != 0) {
			int temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}
	
	private Step.Type charToType(char c, Step previousStep) {
		switch (c) {
		case '1':
			return Step.Type.REGULAR;
		case '2':
			return Step.Type.HOLD_START;
		case '3':
			if (previousStep != null && previousStep.getType() == Step.Type.HOLDING || previousStep.getType() == Step.Type.HOLD_START) {
				return Step.Type.HOLD_END;
			}
			return Step.Type.ROLL_END;
		case '4':
			return Step.Type.ROLL;
		case 'M':
			return Step.Type.MINE;
		case 'L':
			return Step.Type.LIFT;
		case 'F':
			return Step.Type.FAKE;
		default:
			if (previousStep != null) {

				//check previous line to make know if user is holding or not
				switch (previousStep.getType()) {
				case HOLD_START:
				case HOLDING:
					return Step.Type.HOLDING;
				case ROLL:
				case ROLLING:
					return Step.Type.ROLLING;
				default:
					return Step.Type.NONE;
				}			
			} else {
				return Step.Type.NONE;
			}
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
	
	public Step[] getSteps() {
		return steps;
	}
	
	public Timing getTiming() {
		return timing;
	}
		
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Step step : steps) {
			sb.append(getStepRepr(step));
		}
		return sb.toString();
	}
	
	private String getStepRepr(Step step) {
		if (step == null) {
			return "";
		}
		
		switch (step.getType()) {
		case REGULAR:
			return "#";
		case HOLD_START:
			return "%";
		case HOLDING:
			return "|";
		case ROLLING:
			return "!";
		default: 
			return " ";
		}
	}
}
