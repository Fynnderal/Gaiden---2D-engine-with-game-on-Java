package cvut.cz;

import cvut.cz.characters.States;
import cvut.cz.items.Item;
import javafx.scene.image.Image;

public abstract class GameSprite {
    protected Image srcImage;
    protected double sourceCoordinateX;
    protected double sourceCoordinateY;
    protected double sourceWidth;
    protected double sourceHeight;

    protected double targetCoordinateX;
    protected double targetCoordinateY;
    protected double targetWidth;
    protected double targetHeight;

    public Image getImage() { return srcImage; }
    double getSourceCoordinateX() { return sourceCoordinateX; }
    double getSourceCoordinateY() { return sourceCoordinateY; }
    double getSourceWidth() { return sourceWidth; }
    double getSourceHeight() { return sourceHeight; }

    double getTargetCoordinateX() { return targetCoordinateX; }
    double getTargetCoordinateY() { return targetCoordinateY; }
    double getTargetWidth() { return targetWidth; }
    double getTargetHeight() { return targetHeight; }

}

