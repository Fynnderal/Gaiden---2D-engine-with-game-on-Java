package cvut.cz.Utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Utility class for makeing copy of and image;
 */
public class ImageCopy {
    private static final Logger logger = Logger.getLogger(ImageCopy.class.getName());

    private ImageCopy() {}

    /**
     * Copies pixels from a source image to a target image.
     *
     * @param pixelReader The PixelReader to read pixels from the source image.
     * @param pixelWriter The PixelWriter to write pixels to the target image.
     * @param width The width of the area to copy.
     * @param height The height of the area to copy.
     * @param sourceX The X-coordinate of the top-left corner of the source area.
     * @param sourceY The Y-coordinate of the top-left corner of the source area.
     * @param targetX The X-coordinate of the top-left corner of the target area.
     * @param targetY The Y-coordinate of the top-left corner of the target area.
     */
    public static void copyPixels(PixelReader pixelReader, PixelWriter pixelWriter, int width, int height, int sourceX, int sourceY, int targetX, int targetY) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                try {
                    pixelWriter.setArgb(targetX + j, targetY + i, pixelReader.getArgb(sourceX + j, sourceY + i));
                } catch (IndexOutOfBoundsException e) {
                    logger.warning("skipped pixel: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Saves an image to a file in PNG format.
     *
     * @param image The WritableImage from the image to save.
     * @param file The file to save the image to.
     */
    public static void saveImage(WritableImage image, File file) {
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            logger.info("Map was saved to: " + file.toURI());
        } catch (IOException | NullPointerException e) {
            logger.severe("Unable to save map to " + file.toURI() + "; ERROR: " + e.getMessage());
        }
    }
}
