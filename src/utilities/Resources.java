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
	
	public static void loadStepAssets() {
		final String notesDir = "notes/stepmania5/";
		final Resources r = getInstance();

		r.freezeBody = loadImage(notesDir + "hold.png");
		r.freezeEnd = loadImage(notesDir + "hold_cap_bottom.png");
		r.rollBody = loadImage(notesDir + "roll.png");
		r.rollEnd = loadImage(notesDir + "roll_cap_bottom.png");
		
	}
	//singleton pattern declarations
	private static class ResourcesHolder {
		public static final Resources INSTANCE = new Resources();
	}
	
	public static Resources getInstance() {
		return ResourcesHolder.INSTANCE;
	}
}
