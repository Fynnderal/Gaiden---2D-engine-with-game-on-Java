package cvut.cz.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import cvut.cz.GameSprite;
import javafx.scene.image.Image;
import com.fasterxml.jackson.databind.ObjectMapper;


public abstract class Item implements GameSprite {
    protected Image srcImage;
    protected double sourceCoordinateX;
    protected double sourceCoordinateY;
    protected double sourceWidth;
    protected double sourceHeight;

    protected double targetCoordinateX;
    protected double targetCoordinateY;
    protected double targetWidth;
    protected double targetHeight;

    @JsonProperty("Type")
    protected String type;
    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Broken")
    protected boolean isBroken;

    public Image getImage() {return srcImage;}
    public double getSourceCoordinateX() { return sourceCoordinateX; }
    public double getSourceCoordinateY() { return sourceCoordinateY; }
    public double getSourceWidth() { return sourceWidth; }
    public double getSourceHeight() { return sourceHeight; }

    public double getTargetCoordinateX() {return targetCoordinateX;}
    public double getTargetCoordinateY() { return targetCoordinateY;}
    public double getTargetWidth() {return targetWidth;}
    public double getTargetHeight() {return targetHeight;}

}
