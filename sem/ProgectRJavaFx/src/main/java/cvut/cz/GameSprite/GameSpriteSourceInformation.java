package cvut.cz.GameSprite;

import java.net.URL;

public class GameSpriteSourceInformation implements Cloneable {
    protected URL pathToImage;

    protected int sourceCoordinateX;
    protected int sourceCoordinateY;
    protected int sourceWidth;
    protected int sourceHeight;

    public GameSpriteSourceInformation(URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight) {
        this.sourceCoordinateX = sourceCoordinateX;
        this.sourceCoordinateY = sourceCoordinateY;
        this.sourceWidth = sourceWidth;
        this.sourceHeight = sourceHeight;
        this.pathToImage = pathToImage;
    }

    @Override
    public GameSpriteSourceInformation clone() {
        try {
            return (GameSpriteSourceInformation) super.clone();
        }catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public int getSourceCoordinateX() { return sourceCoordinateX; }
    public int getSourceCoordinateY() { return sourceCoordinateY; }
    public int getSourceWidth() { return sourceWidth; }
    public int getSourceHeight() { return sourceHeight; }
    public URL getPathImage() { return pathToImage; }

    public void setSourceCoordinateX(int sourceCoordinateX) { this.sourceCoordinateX = sourceCoordinateX; }
    public void setSourceCoordinateY(int sourceCoordinateY) { this.sourceCoordinateY = sourceCoordinateY; }
    public void setSourceWidth(int sourceWidth) { this.sourceWidth = sourceWidth; }
    public void setSourceHeight(int sourceHeight) { this.sourceHeight = sourceHeight; }
    public void setPathImage(URL pathToImage) { this.pathToImage = pathToImage; }
}
