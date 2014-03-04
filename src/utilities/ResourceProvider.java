package utilities;

import java.awt.image.BufferedImage;

import models.stepmetadata.Orientation;
import models.stepmetadata.Timing;
import models.stepmetadata.Type;

public interface ResourceProvider {
	public void loadImagesIfNotLoaded();
	public boolean isLoaded();
	
	public BufferedImage getStepImage(Type type, Orientation orientation, Timing timing);
	public BufferedImage getHoldBackgroundImage(Type type, Orientation orientation, Timing timing);
	public BufferedImage getHoldEndBackgroundImage(Type type, Orientation orientation, Timing timing);
}
