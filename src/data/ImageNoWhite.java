package data;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Utility class to remove white from an image such that the background behind the whitespace of an image
 * can be seen, converts both awt and javafx images
 *
 * @author cndracos
 */
public class ImageNoWhite {

    /**
     * Takes in an awt image and creates a new one with no white pixels and returns it
     *
     * @param image to be converted
     * @return new image with no white pixels
     */
    public java.awt.Image convertAWT(java.awt.Image image) {
        BufferedImage original = (BufferedImage) image;
        BufferedImage whiteFilter = new BufferedImage(original.getWidth(),
                original.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = whiteFilter.createGraphics();
        for (int i = 0; i < whiteFilter.getWidth(); i++) {
            for (int j = 0; j < whiteFilter.getHeight(); j++) {
                if (original.getRGB(i, j) != Color.WHITE.getRGB()) {
                    whiteFilter.setRGB(i, j, original.getRGB(i, j));
                }
            }
        }
        g.dispose();

        return whiteFilter;
    }

    /*
     * The method that converts javafx images, by calling the awt converter and translating back
     * into a javafx instance image
     *
     * @param image to be converted
     * @return new image no white pizels
     *
     */
    public Image convert(Image image) {
        BufferedImage whiteFilter = SwingFXUtils.fromFXImage(image, null);
        return SwingFXUtils.toFXImage((BufferedImage) convertAWT(whiteFilter), null);
    }

}
