package models.stepmetadata;

import models.Step;

public enum Type {
	NONE, REGULAR, HOLD_START, HOLD_END, ROLL, MINE, LIFT, FAKE,
	//custom definitions
	HOLDING, ROLLING, ROLL_END;
	
	public static Type fromChar(char c, Step previousStep) {
		switch (c) {
		case '1':
			return REGULAR;
		case '2':
			return HOLD_START;
		case '3':
			if (previousStep != null && previousStep.getType() == HOLDING || previousStep.getType() == HOLD_START) {
				return HOLD_END;
			}
			return ROLL_END;
		case '4':
			return ROLL;
		case 'M':
			return MINE;
		case 'L':
			return LIFT;
		case 'F':
			return FAKE;
		//nothing ('0')
		default:
			if (previousStep != null) {

				//check previous line to make know if user is holding or not
				switch (previousStep.getType()) {
				case HOLD_START:
				case HOLDING:
					return HOLDING;
				case ROLL:
				case ROLLING:
					return ROLLING;
				default:
					return NONE;
				}			
			} else {
				return NONE;
			}
		}
	}
}