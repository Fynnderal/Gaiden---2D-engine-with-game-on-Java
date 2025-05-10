package cvut.cz.GameSprite;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Keeps sprite information about how it should be drawn on the screen.
 * This class contains details about the sprite's position and dimensions
 * both on the screen and in the game world.
 */
public class GameSpriteRenderInformation implements Cloneable {
    //coordinate X (in pixels) relative to screen where sprite must be drawn
    protected int screenCoordinateX;

    //coordinate Y (in pixels) relative to screen where sprite must be drawn
    protected int screenCoordinateY;

    //coordinate X (in pixels) relative to map where sprite must be drawn
    protected int worldCoordinateX;

    //coordinate Y (in pixels) relative to map where sprite must be drawn
    protected int worldCoordinateY;

    //Width of the final image on the screen
    protected int targetWidth;

    //Height of the final image on the screen
    protected int targetHeight;

    /**
     * Constructs a `GameSpriteRenderInformation` object with the specified coordinates and dimensions.
     *
     * @param screenCoordinateX The X-coordinate (in pixels) relative to the screen.
     * @param screenCoordinateY The Y-coordinate (in pixels) relative to the screen.
     * @param targetWidth The width of the sprite on the screen.
     * @param targetHeight The height of the sprite on the screen.
     * @param worldCoordinateX The X-coordinate relative to the map.
     * @param worldCoordinateY The Y-coordinate relative to the map.
     */
    @JsonCreator
    public GameSpriteRenderInformation(@JsonProperty("screenCoordinateX") int screenCoordinateX, @JsonProperty("screenCoordinateY") int screenCoordinateY,
                                       @JsonProperty("worldCoordinateX") int targetWidth, @JsonProperty("worldCoordinateY") int targetHeight,
                                       @JsonProperty("targetWidth") int worldCoordinateX, @JsonProperty("targetHeight") int worldCoordinateY) {
        this.screenCoordinateX = screenCoordinateX;
        this.screenCoordinateY = screenCoordinateY;
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        this.worldCoordinateX = worldCoordinateX;
        this.worldCoordinateY = worldCoordinateY;
    }

    /**
     * Creates and returns a copy of this object.
     *
     * @return A clone of this `GameSpriteRenderInformation` object.
     */
    @Override
    public GameSpriteRenderInformation clone() {
        try {
            return (GameSpriteRenderInformation) super.clone();
        }catch(CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * Gets the X-coordinate relative to the map.
     *
     * @return The X-coordinate relative to the map.
     */
    public int getWorldCoordinateX() { return worldCoordinateX; }

    /**
     * Gets the Y-coordinate relative to the map.
     *
     * @return The Y-coordinate relative to the map.
     */
    public int getWorldCoordinateY() { return worldCoordinateY; }

    /**
     * Gets the X-coordinate relative to the screen.
     *
     * @return The X-coordinate relative to the screen.
     */
    public int getScreenCoordinateX() { return screenCoordinateX; }

    /**
     * Gets the Y-coordinate relative to the screen.
     *
     * @return The Y-coordinate relative to the screen.
     */
    public int getScreenCoordinateY() { return screenCoordinateY; }

    /**
     * Gets the width of the sprite on the screen.
     *
     * @return The width of the sprite on the screen.
     */
    public int getTargetWidth() { return targetWidth; }

    /**
     * Gets the height of the sprite on the screen.
     *
     * @return The height of the sprite on the screen.
     */
    public int getTargetHeight() { return targetHeight; }

    /**
     * Sets the X-coordinate relative to the map.
     *
     * @param worldCoordinateX The new X-coordinate relative to the map.
     */
    public void setWorldCoordinateX(int worldCoordinateX) { this.worldCoordinateX = worldCoordinateX; }

    /**
     * Sets the Y-coordinate relative to the map.
     *
     * @param worldCoordinateY The new Y-coordinate relative to the map.
     */
    public void setWorldCoordinateY(int worldCoordinateY) { this.worldCoordinateY = worldCoordinateY; }

    /**
     * Sets the X-coordinate relative to the screen.
     *
     * @param screenCoordinateX The new X-coordinate relative to the screen.
     */
    public void setScreenCoordinateX(int screenCoordinateX) { this.screenCoordinateX = screenCoordinateX; }

    /**
     * Sets the Y-coordinate relative to the screen.
     *
     * @param screenCoordinateY The new Y-coordinate relative to the screen.
     */
    public void setScreenCoordinateY(int screenCoordinateY) { this.screenCoordinateY = screenCoordinateY; }

    /**
     * Sets the width of the sprite on the screen.
     *
     * @param targetWidth The new width of the sprite on the screen.
     */
    public void setTargetWidth(int targetWidth) { this.targetWidth = targetWidth; }

    /**
     * Sets the height of the sprite on the screen.
     *
     * @param targetHeight The new height of the sprite on the screen.
     */
    public void setTargetHeight(int targetHeight) { this.targetHeight = targetHeight; }
}
