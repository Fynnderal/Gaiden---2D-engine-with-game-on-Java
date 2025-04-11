package cvut.cz.items;

import cvut.cz.GameSprite;

import java.net.URL;

public class Pointer extends GameSprite {

    public Pointer(URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight){
        super(pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, 0, 0, 0, 0, 0, 0);
    }

    public void move(int offsetX, int offsetY){
        this.screenCoordinateX += offsetX;
        this.screenCoordinateY += offsetY;
    }

    public void setScreenCoordinateX(int screenCoordinateX){ this.screenCoordinateX = screenCoordinateX; }
    public void setScreenCoordinateY(int screenCoordinateY) { this.screenCoordinateY = screenCoordinateY; }
    public void setTargetWidth(int targetWidth) { this.targetWidth = targetWidth; }
    public void setTargetHeight(int targetHeight) { this.targetHeight = targetHeight; }
}
