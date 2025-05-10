package cvut.cz.GameSprite;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URL;
import java.util.Objects;

/**
 * Keeps sprite information about the source image from which the sprite should be extracted.
 * This class contains details about the image path and the coordinates and dimensions of the sprite within the source image.
 */

public class GameSpriteSourceInformation implements Cloneable {
    protected URL pathToImage;

    //coordinate X (in pixels) relative to source image where sprite is located
    protected int sourceCoordinateX;

    //coordinate Y (in pixels) relative to source image where sprite is located
    protected int sourceCoordinateY;

    //Width of the source image
    protected int sourceWidth;

    //Height of the source image
    protected int sourceHeight;

    /**
     * Constructs a `GameSpriteSourceInformation` object with the specified image path, coordinates, and dimensions.
     *
     * @param pathToImage The URL path to the source image.
     * @param sourceCoordinateX The X-coordinate of the sprite within the source image.
     * @param sourceCoordinateY The Y-coordinate of the sprite within the source image.
     * @param sourceWidth The width of the sprite within the source image.
     * @param sourceHeight The height of the sprite within the source image.
     */
    @JsonCreator
    public GameSpriteSourceInformation(@JsonProperty("pathToImage") URL pathToImage, @JsonProperty("sourceCoordinateX") int sourceCoordinateX,
                                       @JsonProperty("sourceCoordinateY") int sourceCoordinateY, @JsonProperty("sourceWidth") int sourceWidth, @JsonProperty("sourceHeight") int sourceHeight) {
        this.sourceCoordinateX = sourceCoordinateX;
        this.sourceCoordinateY = sourceCoordinateY;
        this.sourceWidth = sourceWidth;
        this.sourceHeight = sourceHeight;
        this.pathToImage = pathToImage;
    }

    /**
     * Creates and returns a copy of this object.
     *
     * @return A clone of this `GameSpriteSourceInformation` object.
     */
    @Override
    public GameSpriteSourceInformation clone() {
        try {
            return (GameSpriteSourceInformation) super.clone();
        }catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * Checks if this instance is equal to another object.
     *
     * @param obj The object to compare for equality.
     * @return `true` if the objects are equal, `false` otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        GameSpriteSourceInformation other = (GameSpriteSourceInformation) obj;
        return (sourceCoordinateX == other.sourceCoordinateX && sourceCoordinateY == other.sourceCoordinateY &&
                sourceWidth == other.sourceWidth && sourceHeight == other.sourceHeight
                && pathToImage.getPath().equals(other.pathToImage.getPath()));
    }

    /**
     * Calculates the hash code for this instance.
     *
     * @return The hash code of the instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, pathToImage.getPath());
    }

    /**
     * Gets the X-coordinate of the sprite within the source image.
     *
     * @return The X-coordinate of the sprite.
     */
    public int getSourceCoordinateX() { return sourceCoordinateX; }

    /**
     * Gets the Y-coordinate of the sprite within the source image.
     *
     * @return The Y-coordinate of the sprite.
     */
    public int getSourceCoordinateY() { return sourceCoordinateY; }

    /**
     * Gets the width of the sprite within the source image.
     *
     * @return The width of the sprite.
     */
    public int getSourceWidth() { return sourceWidth; }

    /**
     * Gets the height of the sprite within the source image.
     *
     * @return The height of the sprite.
     */
    public int getSourceHeight() { return sourceHeight; }

    /**
     * Gets the URL path to the source image.
     *
     * @return The URL path to the source image.
     */
    public URL getPathImage() { return pathToImage; }

    /**
     * Sets the X-coordinate of the sprite within the source image.
     *
     * @param sourceCoordinateX The new X-coordinate of the sprite.
     */
    public void setSourceCoordinateX(int sourceCoordinateX) { this.sourceCoordinateX = sourceCoordinateX; }

    /**
     * Sets the Y-coordinate of the sprite within the source image.
     *
     * @param sourceCoordinateY The new Y-coordinate of the sprite.
     */
    public void setSourceCoordinateY(int sourceCoordinateY) { this.sourceCoordinateY = sourceCoordinateY; }

    /**
     * Sets the width of the sprite within the source image.
     *
     * @param sourceWidth The new width of the sprite.
     */
    public void setSourceWidth(int sourceWidth) { this.sourceWidth = sourceWidth; }

    /**
     * Sets the height of the sprite within the source image.
     *
     * @param sourceHeight The new height of the sprite.
     */
    public void setSourceHeight(int sourceHeight) { this.sourceHeight = sourceHeight; }

    /**
     * Sets the URL path to the source image.
     *
     * @param pathToImage The new URL path to the source image.
     */
    public void setPathImage(URL pathToImage) { this.pathToImage = pathToImage; }
}
