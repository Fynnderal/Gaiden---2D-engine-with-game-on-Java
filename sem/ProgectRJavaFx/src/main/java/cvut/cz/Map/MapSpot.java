package cvut.cz.Map;

import cvut.cz.GameSprite;

import java.net.URL;

public class MapSpot extends GameSprite {
    private int mapCoordinateX;
    private int mapCoordinateY;

    public MapSpot(URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int targetWidth, int targetHeight, int worldCoordinateX, int worldCoordinateY) {
        super(pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, 0, 0, targetWidth, targetHeight, worldCoordinateX, worldCoordinateY);
    }


    public void interact() {

    }

}
