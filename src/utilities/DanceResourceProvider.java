package utilities;

import java.awt.image.BufferedImage;

import models.stepmetadata.Orientation;
import models.stepmetadata.Timing;
import models.stepmetadata.Type;

public class DanceResourceProvider implements ResourceProvider {

	@Override
	public BufferedImage getStepImage(Type type, Orientation orientation, Timing timing) {
		Resources r = Resources.getInstance();
		switch (type) {
		//return normal step image
		case REGULAR:
		case FREEZE_START:
		case ROLL_START:
			switch (timing) {
			case L1ST:
			case L4TH:
				return r.step4th;
			case L8TH:
				return r.step8th;
			case L12TH:
				return r.step12th;
			case L16TH:
				return r.step16th;
			case L24TH:
				return r.step24th;
			case L32ND:
				return r.step32nd;
			case L48TH:
				return r.step48th;
			default:
				return r.step64th;
			}
		//mine image
		case MINE:
			return r.mine;
		default:
			return null;
		}
	}

	@Override
	public BufferedImage getHoldBackgroundImage(Type type, Orientation orientation, Timing timing) {
		Resources r = Resources.getInstance();
		switch (type) {
		case FREEZE_START:
		case HOLDING:
		case FREEZE_END:
			return r.freezeBody;
		case ROLL_START:
		case ROLLING:
		case ROLL_END:
			return r.rollBody;
		default:
			return r.empty;
		}
	}

	@Override
	public BufferedImage getHoldEndBackgroundImage(Type type, Orientation orientation, Timing timing) {
		Resources r = Resources.getInstance();
		switch (type) {
		case FREEZE_START:
		case HOLDING:
		case FREEZE_END:
			return r.freezeEnd;
		case ROLL_START:
		case ROLLING:
		case ROLL_END:
			return r.rollEnd;
		default:
			return r.empty;
		}
	}

}
