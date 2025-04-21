package cvut.cz.Map;

public class Collision {
    private int worldCoordinateX;
    private int worldCoordinateY;
    private int width;
    private int height;
    private boolean active;

    public Collision(int worldCoordinateX, int worldCoordinateY, int width, int height) {
        this.worldCoordinateX = worldCoordinateX;
        this.worldCoordinateY = worldCoordinateY;
        this.width = width;
        this.height = height;
        this.active = true;
    }
    public boolean isActive() { return active; }
    public int getWorldCoordinateX() { return worldCoordinateX; }
    public int getWorldCoordinateY() { return worldCoordinateY; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public void setWorldCoordinateX(int worldCoordinateX) { this.worldCoordinateX = worldCoordinateX; }
    public void setWorldCoordinateY(int worldCoordinateY) { this.worldCoordinateY = worldCoordinateY; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }

    public void setActive(boolean active) { this.active = active; }
}
