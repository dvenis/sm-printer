package utilities;

import java.awt.image.BufferedImage;

import models.stepmetadata.Orientation;
import models.stepmetadata.Timing;
import models.stepmetadata.Type;

public class DanceResourceProvider implements ResourceProvider {
	
	private BufferedImage freezeBody;
	private BufferedImage freezeEnd;
	private BufferedImage rollBody;
	private BufferedImage rollEnd;
	
	private BufferedImage[] steps;
	
//	private BufferedImage step4th;
//	private BufferedImage step8th;
//	private BufferedImage step16th;
//	private BufferedImage step32nd;
//	private BufferedImage step64th;
//	private BufferedImage step12th;
//	private BufferedImage step24th;
//	private BufferedImage step48th;
	
	private BufferedImage mine;
	
	private boolean isLoaded = false;
	private Resources parent;
	
	public DanceResourceProvider(Resources parent) {
		this.parent = parent;
	}
	
	@Override
	public void loadImagesIfNotLoaded() {
		final String notesDir = parent.notesDir;
		
		if (isLoaded) {
			return;
		}

		freezeBody = Resources.loadImage(notesDir + "hold.png");
		freezeEnd = Resources.loadImage(notesDir + "hold_cap_bottom.png");
		rollBody = Resources.loadImage(notesDir + "roll.png");
		rollEnd = Resources.loadImage(notesDir + "roll_cap_bottom.png");
		mine = Resources.loadImage(notesDir + "mine.png");
		
		BufferedImage allNotes = Resources.loadImage(notesDir + "notes.png");
		int stepDim = allNotes.getWidth();
		steps = Resources.getImageSubImages(allNotes, stepDim);
//		step4th = allNotes.getSubimage(0, 0, stepDim, stepDim);
//		step8th = allNotes.getSubimage(0, stepDim, stepDim, stepDim);
//		step12th = allNotes.getSubimage(0, stepDim * 2, stepDim, stepDim);
//		step16th = allNotes.getSubimage(0, stepDim * 3, stepDim, stepDim);
//		step24th = allNotes.getSubimage(0, stepDim * 4, stepDim, stepDim);
//		step32nd = allNotes.getSubimage(0, stepDim * 5, stepDim, stepDim);
//		step48th = allNotes.getSubimage(0, stepDim * 6, stepDim, stepDim);
//		step64th = allNotes.getSubimage(0, stepDim * 7, stepDim, stepDim);
		
		isLoaded = true;
	}

	@Override
	public boolean isLoaded() {
		return isLoaded;
	}

	@Override
	public BufferedImage getStepImage(Type type, Orientation orientation, Timing timing) {
		switch (type) {
		//return normal step image
		case REGULAR:
		case FREEZE_START:
		case ROLL_START:
			switch (timing) {
			case L1ST:
			case L4TH:
				return steps[0];
			case L8TH:
				return steps[1];
			case L12TH:
				return steps[2];
			case L16TH:
				return steps[3];
			case L24TH:
				return steps[4];
			case L32ND:
				return steps[5];
			case L48TH:
				return steps[6];
			default:
				return steps[7];
			}
		//mine image
		case MINE:
			return mine;
		default:
			return null;
		}
	}

	@Override
	public BufferedImage getHoldBackgroundImage(Type type, Orientation orientation, Timing timing) {
		switch (type) {
		case FREEZE_START:
		case HOLDING:
		case FREEZE_END:
			return freezeBody;
		case ROLL_START:
		case ROLLING:
		case ROLL_END:
			return rollBody;
		default:
			return parent.empty;
		}
	}

	@Override
	public BufferedImage getHoldEndBackgroundImage(Type type, Orientation orientation, Timing timing) {
		switch (type) {
		case FREEZE_START:
		case HOLDING:
		case FREEZE_END:
			return freezeEnd;
		case ROLL_START:
		case ROLLING:
		case ROLL_END:
			return rollEnd;
		default:
			return parent.empty;
		}
	}

}
