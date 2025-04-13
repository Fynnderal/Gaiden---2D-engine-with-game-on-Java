package cvut.cz.Map;

import cvut.cz.items.Item;

import java.net.URL;

public class Door extends MapSpot{

    private Item neededItem;
    private Collision collision;

    public Door(URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int targetWidth, int targetHeight, int worldCoordinateX, int worldCoordinateY) {
        super(pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, targetWidth, targetHeight, worldCoordinateX, worldCoordinateY);
    }

    public void openDoor(Item item){

    }
}
