package cvut.cz;

import java.net.URL;

public abstract class GameSprite {
    protected URL pathToImage;
    protected int sourceCoordinateX;
    protected int sourceCoordinateY;
    protected int sourceWidth;
    protected int sourceHeight;

    protected int screenCoordinateX;
    protected int screenCoordinateY;
    protected int worldCoordinateX;
    protected int worldCoordinateY;
    protected int targetWidth;
    protected int targetHeight;

    public URL getPathImage() { return pathToImage; }
    public int getSourceCoordinateX() { return sourceCoordinateX; }
    public int getSourceCoordinateY() { return sourceCoordinateY; }
    public int getSourceWidth() { return sourceWidth; }
    public int getSourceHeight() { return sourceHeight; }

    public int getWorldCoordinateX() { return worldCoordinateX; }
    public int getWorldCoordinateY() { return worldCoordinateY; }
    public int getScreenCoordinateX() { return screenCoordinateX; }
    public int getScreenCoordinateY() { return screenCoordinateY; }
    public int getTargetWidth() { return targetWidth; }
    public int getTargetHeight() { return targetHeight; }



    public GameSprite(URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int targetCoordinateX, int targetCoordinateY, int targetWidth, int targetHeight, int worldCoordinateX, int worldCoordinateY)
    {
        this.pathToImage = pathToImage;
        this.sourceCoordinateX = sourceCoordinateX;
        this.sourceCoordinateY = sourceCoordinateY;
        this.sourceWidth = sourceWidth;
        this.sourceHeight = sourceHeight;
        this.screenCoordinateX = targetCoordinateX;
        this.screenCoordinateY = targetCoordinateY;
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        this.worldCoordinateX = worldCoordinateX;
        this.worldCoordinateY = worldCoordinateY;
    }
}

