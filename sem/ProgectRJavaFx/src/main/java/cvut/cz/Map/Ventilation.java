package cvut.cz.Map;

import cvut.cz.GameSpriteRenderInformation;
import cvut.cz.GameSpriteSourceInformation;
import cvut.cz.characters.PlayableCharacter;
import cvut.cz.items.Item;

import java.net.URL;

public class Ventilation extends MapSpot{

    private Ventilation spotToTeleport;

    public Ventilation(GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
    }

    public void teleportPlayer(PlayableCharacter playableCharacter){

    }
}
