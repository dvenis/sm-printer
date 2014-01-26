package utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resources {
	public BufferedImage freezeBody;
	public BufferedImage freezeEnd;
	public BufferedImage rollBody;
	public BufferedImage rollEnd;
	
	public BufferedImage step4th;
	public BufferedImage step8th;
	public BufferedImage step16th;
	public BufferedImage step32nd;
	public BufferedImage step64th;
	public BufferedImage step12th;
	public BufferedImage step24th;
	public BufferedImage step48th;
	
	public BufferedImage mine;
	
	public boolean stepAssetsLoaded = false;
	
	public static BufferedImage loadImage(String path) {
		return loadImage(new File(path));
	}
	
	public static BufferedImage loadImage(File file) {
		if (!file.exists()) {
			return null;
		}
		
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void loadStepAssetsIfNotLoaded() {
		final String notesDir = "notes/stepmania5/";
		final Resources r = getInstance();
		
		if (r.stepAssetsLoaded) {
			return;
		}

		r.freezeBody = loadImage(notesDir + "hold.png");
		r.freezeEnd = loadImage(notesDir + "hold_cap_bottom.png");
		r.rollBody = loadImage(notesDir + "roll.png");
		r.rollEnd = loadImage(notesDir + "roll_cap_bottom.png");
		r.mine = loadImage(notesDir + "mine.png");
		
		BufferedImage allNotes = loadImage(notesDir + "notes.png");
		int stepDim = allNotes.getWidth();
		r.step4th = allNotes.getSubimage(0, 0, stepDim, stepDim);
		r.step8th = allNotes.getSubimage(0, stepDim, stepDim, stepDim);
		r.step12th = allNotes.getSubimage(0, stepDim * 2, stepDim, stepDim);
		r.step16th = allNotes.getSubimage(0, stepDim * 3, stepDim, stepDim);
		r.step24th = allNotes.getSubimage(0, stepDim * 4, stepDim, stepDim);
		r.step32nd = allNotes.getSubimage(0, stepDim * 5, stepDim, stepDim);
		r.step48th = allNotes.getSubimage(0, stepDim * 6, stepDim, stepDim);
		r.step64th = allNotes.getSubimage(0, stepDim * 7, stepDim, stepDim);
		
		r.stepAssetsLoaded = true;
	}
	
	//singleton pattern declarations
	private static class ResourcesHolder {
		public static final Resources INSTANCE = new Resources();
	}
	
	public static Resources getInstance() {
		return ResourcesHolder.INSTANCE;
	}
}
