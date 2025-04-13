package cvut.cz.Map;

import cvut.cz.characters.PlayableCharacter;
import cvut.cz.items.Item;

import java.net.URL;

public class Ventilation extends MapSpot{

    private Ventilation spotToTeleport;

    public Ventilation(URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int targetWidth, int targetHeight, int worldCoordinateX, int worldCoordinateY) {
        super(pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, targetWidth, targetHeight, worldCoordinateX, worldCoordinateY);
    }

    public void teleportPlayer(PlayableCharacter playableCharacter){

    }
}
