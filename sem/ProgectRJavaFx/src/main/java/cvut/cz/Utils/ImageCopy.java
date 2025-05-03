package cvut.cz.Utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ImageCopy {
    private static final Logger logger = Logger.getLogger(ImageCopy.class.getName());

    private ImageCopy() {}

    public static void copyPixels(PixelReader pixelReader, PixelWriter pixelWriter, int width, int height, int sourceX, int sourceY, int targetX, int targetY) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                try {
                    pixelWriter.setArgb(targetX + j, targetY + i, pixelReader.getArgb(sourceX + j, sourceY + i));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("we are here:" + e.getMessage());
                }
            }
        }
    }

    public static void saveImage(WritableImage image, File file) {
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            logger.info("Map was saved to: " + file.toURI());
        } catch (IOException | NullPointerException e) {
            logger.severe("Unable to save map to " + file.toURI() + "; ERROR: " + e.getMessage());
        }
    }
}
