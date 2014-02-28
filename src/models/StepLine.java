package models;

import models.stepmetadata.NotesType;
import models.stepmetadata.Type;
import models.stepmetadata.Orientation;
import models.stepmetadata.Timing;

public class StepLine {
	
	private Step[] steps;
	private Timing timing;
	
	public StepLine(String rawData, StepLine previousLine, NotesType notesType,
			int lineIndex, int numberLinesInMeasure) {
		timing = Timing.fromLineIndexAndMeasureSize(lineIndex, numberLinesInMeasure);
		
		steps = new Step[rawData.trim().length()];
		if (previousLine != null && previousLine.steps != null) {
			for (int i = 0; i < steps.length; i++) {
				steps[i] = makeStep(rawData.charAt(i), i, notesType, previousLine.getSteps()[i]);
			}
		} else {
			for (int i = 0; i < steps.length; i++) {
				steps[i] = makeStep(rawData.charAt(i), i, notesType, null);
			}
		}
	}
	
	private Step makeStep(char rawCharacter, int stepIndex, NotesType notesType, Step previousStep) {
		Type type = Type.fromChar(rawCharacter, previousStep);
		Orientation orientation = notesType.getStepOrientation(stepIndex);
		return new Step(type, orientation, timing);
	}
	
	public Step[] getSteps() {
		return steps;
	}
	
	public Timing getTiming() {
		return timing;
	}
		
	//debug strings
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
