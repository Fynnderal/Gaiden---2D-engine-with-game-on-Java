package cvut.cz.Map;

/**
 * Represents a collision object in the game world.
 * This class defines the position, dimensions, and state of a collision area.
 */
public class Collision {
    // Coordinates of the collision area in the world
    private int worldCoordinateX;
    private int worldCoordinateY;

    // Dimensions of the collision area
    private int width;
    private int height;

    // State of the collision area (active or inactive)
    private boolean active;

    /**
     * Constructs a Collision object with the specified position and dimensions.
     *
     * @param worldCoordinateX The X-coordinate of the collision in the world.
     * @param worldCoordinateY The Y-coordinate of the collision in the world.
     * @param width The width of the collision area.
     * @param height The height of the collision area.
     */
    public Collision(int worldCoordinateX, int worldCoordinateY, int width, int height) {
        this.worldCoordinateX = worldCoordinateX;
        this.worldCoordinateY = worldCoordinateY;
        this.width = width;
        this.height = height;
        this.active = true;
    }

    /**
     * Checks if the collision is active.
     *
     * @return True if the collision is active, false otherwise.
     */
    public boolean isActive() { return active; }

    /**
     * Gets the X-coordinate of the collision in the world.
     *
     * @return The X-coordinate of the collision.
     */
    public int getWorldCoordinateX() { return worldCoordinateX; }

    /**
     * Gets the Y-coordinate of the collision in the world.
     *
     * @return The Y-coordinate of the collision.
     */
    public int getWorldCoordinateY() { return worldCoordinateY; }

    /**
     * Gets the width of the collision area.
     *
     * @return The width of the collision.
     */
    public int getWidth() { return width; }

    /**
     * Gets the height of the collision area.
     *
     * @return The height of the collision.
     */
    public int getHeight() { return height; }

    /**
     * Sets the X-coordinate of the collision in the world.
     *
     * @param worldCoordinateX The new X-coordinate of the collision.
     */
    public void setWorldCoordinateX(int worldCoordinateX) { this.worldCoordinateX = worldCoordinateX; }

    /**
     * Sets the Y-coordinate of the collision in the world.
     *
     * @param worldCoordinateY The new Y-coordinate of the collision.
     */
    public void setWorldCoordinateY(int worldCoordinateY) { this.worldCoordinateY = worldCoordinateY; }

    /**
     * Sets the width of the collision area.
     *
     * @param width The new width of the collision.
     */
    public void setWidth(int width) { this.width = width; }

    /**
     * Sets the height of the collision area.
     *
     * @param height The new height of the collision.
     */
    public void setHeight(int height) { this.height = height; }

    /**
     * Sets the active state of the collision.
     *
     * @param active True to activate the collision, false to deactivate it.
     */
    public void setActive(boolean active) { this.active = active; }
}
