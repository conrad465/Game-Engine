package engine.systems;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import data.DataUtils;
import engine.exceptions.EngineException;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * A Javafx transition to be used with a sprite for animation. Uses a rectangle viewbox to look at 
 * individual frames from a sprite sheet, and switch them at a certain speed.
 * Inspired from https://netopyr.com/2012/03/09/creating-a-sprite-animation-with-javafx/ .
 * @author fitzj
 *
 */
public class SpriteTransition extends Transition {

	private static final String DURATION_KEY = "Duration";
	private static final String COUNT_KEY = "Count";
	private static final String COLUMN_KEY = "Columns";
	private static final String X_KEY = "XOffset";
	private static final String Y_KEY = "YOffset";
	private static final String W_KEY = "Width";
	private static final String H_KEY = "Height";
	
	private static final String MISSING_FILE_MESSAGE = "Missing animation properties file.";
	private static final String BAD_FORMAT_MESSAGE = "Improperly formatted animation properties file.";
	
	private final ImageView image;
	private final int total;
	private final int cols;
	private final int x;
	private final int y;
	private final int w;
	private final int h;
	
	private int lastIndex;
	
	public SpriteTransition(ImageView image, String file) throws EngineException {
		
		this.image = image;
		
		Properties prop = new Properties();
		int duration = 0;
		
		try {
			File f = DataUtils.loadFile(file);
			prop.load(new FileInputStream(f));
		} catch (Exception e) {
			throw new EngineException(MISSING_FILE_MESSAGE);
		}
		
		try {
			duration = Integer.parseInt(prop.getProperty(DURATION_KEY));
			this.total = Integer.parseInt(prop.getProperty(COUNT_KEY));
			this.cols = Integer.parseInt(prop.getProperty(COLUMN_KEY));
			this.x = Integer.parseInt(prop.getProperty(X_KEY));
			this.y = Integer.parseInt(prop.getProperty(Y_KEY));
			this.w = Integer.parseInt(prop.getProperty(W_KEY));
			this.h = Integer.parseInt(prop.getProperty(H_KEY));
		} catch (Exception e) {
			throw new EngineException(BAD_FORMAT_MESSAGE);
		}
		
		image.setViewport(new Rectangle2D(x, y, w, h));
        setCycleDuration(new Duration(duration));
        setInterpolator(Interpolator.LINEAR);
	}
	
	protected void interpolate(double k) {
		final int index = Math.min((int) Math.floor(k * total), total - 1);
        if (index != lastIndex) {
            final int xPos = (index % cols) * w  + x;
            final int yPos = (index / cols) * h + y;
            image.setViewport(new Rectangle2D(xPos, yPos, w, h));
            lastIndex = index;
        }
	}

}
