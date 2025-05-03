package cvut.cz.GameSprite;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URL;
import java.util.Objects;

public class GameSpriteSourceInformation implements Cloneable {
    protected URL pathToImage;

    protected int sourceCoordinateX;
    protected int sourceCoordinateY;
    protected int sourceWidth;
    protected int sourceHeight;

    @JsonCreator
    public GameSpriteSourceInformation(@JsonProperty("pathToImage") URL pathToImage, @JsonProperty("sourceCoordinateX") int sourceCoordinateX,
                                       @JsonProperty("sourceCoordinateY") int sourceCoordinateY, @JsonProperty("sourceWidth") int sourceWidth, @JsonProperty("sourceHeight") int sourceHeight) {
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

    @Override
    public int hashCode() {
        return Objects.hash(sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, pathToImage.getPath());
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
