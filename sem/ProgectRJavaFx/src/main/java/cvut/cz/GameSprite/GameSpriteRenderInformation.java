package cvut.cz.GameSprite;

public class GameSpriteRenderInformation implements Cloneable {
    protected int screenCoordinateX;
    protected int screenCoordinateY;
    protected int worldCoordinateX;
    protected int worldCoordinateY;
    protected int targetWidth;
    protected int targetHeight;

    public GameSpriteRenderInformation(int screenCoordinateX, int screenCoordinateY, int targetWidth, int targetHeight, int worldCoordinateX, int worldCoordinateY) {
        this.screenCoordinateX = screenCoordinateX;
        this.screenCoordinateY = screenCoordinateY;
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        this.worldCoordinateX = worldCoordinateX;
        this.worldCoordinateY = worldCoordinateY;
    }

    @Override
    public GameSpriteRenderInformation clone() {
        try {
            return (GameSpriteRenderInformation) super.clone();
        }catch(CloneNotSupportedException e) {
            return null;
        }
    }

    public int getWorldCoordinateX() { return worldCoordinateX; }
    public int getWorldCoordinateY() { return worldCoordinateY; }
    public int getScreenCoordinateX() { return screenCoordinateX; }
    public int getScreenCoordinateY() { return screenCoordinateY; }
    public int getTargetWidth() { return targetWidth; }
    public int getTargetHeight() { return targetHeight; }


    public void setWorldCoordinateX(int worldCoordinateX) { this.worldCoordinateX = worldCoordinateX; }
    public void setWorldCoordinateY(int worldCoordinateY) { this.worldCoordinateY = worldCoordinateY; }
    public void setScreenCoordinateX(int screenCoordinateX) { this.screenCoordinateX = screenCoordinateX; }
    public void setScreenCoordinateY(int screenCoordinateY) { this.screenCoordinateY = screenCoordinateY; }
    public void setTargetWidth(int targetWidth) { this.targetWidth = targetWidth; }
    public void setTargetHeight(int targetHeight) { this.targetHeight = targetHeight; }
}
