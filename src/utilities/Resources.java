package utilities;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import models.stepmetadata.NotesType;

public class Resources {
	
	private DanceResourceProvider danceResourceProvider;
	private PumpResourceProvider pumpResourceProvider;
	
	public String notesDir = "notes/stepmania5/";
	
	public boolean stepAssetsLoaded = false;
	
	public Font pageHeader = new Font("Arial", Font.PLAIN, 14);
	public Font measureNumber = new Font("sans-serif", Font.BOLD, 20);
	
	private Resources() {
		danceResourceProvider = new DanceResourceProvider(this);
		pumpResourceProvider = new PumpResourceProvider(this);
	}
	
	public ResourceProvider getProvider(NotesType notesType) {
		switch (notesType.getGameMode()) {
		case DANCE:
			return danceResourceProvider;
		case PUMP:
			return pumpResourceProvider;
		}
		return null;
	}
	
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
	
	public static BufferedImage[][] getImageSubImages(BufferedImage img, 
			int subImgWidth, int subImgHeight) {
		int horizontalSubImages = img.getWidth() / subImgWidth;
		int verticalSubImages = img.getHeight() / subImgHeight;
		BufferedImage[][] subImages = new BufferedImage[verticalSubImages][horizontalSubImages];
		
		for (int i = 0; i < verticalSubImages; i++) {
			for (int j = 0; j < horizontalSubImages; j++) {
				subImages[i][j] = img.getSubimage(j * subImgWidth, i * subImgHeight, subImgWidth, subImgHeight);
			}
		}
		return subImages;
	}
	
	public static BufferedImage[] getImageSubImages(BufferedImage img, int subImgHeight) {
		int verticalSubImages = img.getHeight() / subImgHeight;
		BufferedImage[] subImages = new BufferedImage[verticalSubImages];
		
		for (int i = 0; i < verticalSubImages; i++) {
			subImages[i] = img.getSubimage(0, i * subImgHeight, img.getWidth(), subImgHeight);
		}
		return subImages;
	}
	
	public void loadStepAssetsIfNotLoaded() {
		danceResourceProvider.loadImagesIfNotLoaded();
		pumpResourceProvider.loadImagesIfNotLoaded();
	}
	
//	public static void loadStepAssetsIfNotLoaded() {
//		final String notesDir = "notes/stepmania5/";
//		final Resources r = getInstance();
//		
//		if (r.stepAssetsLoaded) {
//			return;
//		}
//
//		r.freezeBody = loadImage(notesDir + "hold.png");
//		r.freezeEnd = loadImage(notesDir + "hold_cap_bottom.png");
//		r.rollBody = loadImage(notesDir + "roll.png");
//		r.rollEnd = loadImage(notesDir + "roll_cap_bottom.png");
//		r.mine = loadImage(notesDir + "mine.png");
//		
//		BufferedImage allNotes = loadImage(notesDir + "notes.png");
//		int stepDim = allNotes.getWidth();
//		r.step4th = allNotes.getSubimage(0, 0, stepDim, stepDim);
//		r.step8th = allNotes.getSubimage(0, stepDim, stepDim, stepDim);
//		r.step12th = allNotes.getSubimage(0, stepDim * 2, stepDim, stepDim);
//		r.step16th = allNotes.getSubimage(0, stepDim * 3, stepDim, stepDim);
//		r.step24th = allNotes.getSubimage(0, stepDim * 4, stepDim, stepDim);
//		r.step32nd = allNotes.getSubimage(0, stepDim * 5, stepDim, stepDim);
//		r.step48th = allNotes.getSubimage(0, stepDim * 6, stepDim, stepDim);
//		r.step64th = allNotes.getSubimage(0, stepDim * 7, stepDim, stepDim);
//		
//		r.empty = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
//		
//		r.stepAssetsLoaded = true;
//	}
	
	//singleton pattern declarations
	private static class ResourcesHolder {
		public static final Resources INSTANCE = new Resources();
	}
	
	public static Resources getInstance() {
		return ResourcesHolder.INSTANCE;
	}
}
