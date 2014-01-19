package models;

public class StepLine {
	private Step[] steps;
	
	public StepLine(String rawData, StepLine previousLine, int lineIndex, int numberLinesInMeasure) {
		steps = new Step[rawData.trim().length()];
		if (previousLine != null && previousLine.steps != null) {
			for (int i = 0; i < steps.length; i++) {
				steps[i] = makeStep(rawData.charAt(i), i, previousLine.getSteps()[i], lineIndex, numberLinesInMeasure);
			}
		} else {
			for (int i = 0; i < steps.length; i++) {
				steps[i] = makeStep(rawData.charAt(i), i, null, lineIndex, numberLinesInMeasure);
			}
		}
	//	System.out.println(rawData);
	}
	
	private Step makeStep(char rawCharacter, int stepIndex, Step previousStep, 
			int lineIndex, int numberLinesInMeasure) {
		Step.Type type = charToType(rawCharacter, previousStep);
		Step.Orientation orientation = indexToOrientation(stepIndex);
		Step.Length length = calculateLength(lineIndex, numberLinesInMeasure);
		return new Step(type, orientation, length);
	}
	
	
	private Step.Length calculateLength(int lineIndex, int numberLinesInMeasure) {
		int length;
		if (lineIndex != 0) {
			int gcd = getGCD(numberLinesInMeasure, lineIndex);
			length = numberLinesInMeasure / gcd;
		} else {
			length = 4;
		}

		switch(length) {
		case 8:
			return Step.Length.L8TH;
		case 12:
			return Step.Length.L12TH;
		case 16:
			return Step.Length.L16TH;
		case 24:
			return Step.Length.L24TH;
		case 32:
			return Step.Length.L32ND;
		case 48:
			return Step.Length.L48TH;
		case 64:
			return Step.Length.L64TH;
		default:
			return Step.Length.L4TH;
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
			if (previousStep != null) {

				//check previous line to make know if user is holding or not
				switch (previousStep.getType()) {
				case HOLD_START:
				case HOLDING:
					return Step.Type.HOLDING;
				case ROLL:
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
