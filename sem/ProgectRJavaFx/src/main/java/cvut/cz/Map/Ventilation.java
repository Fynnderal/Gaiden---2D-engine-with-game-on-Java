package cvut.cz.Map;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.characters.PlayableCharacter;

public class Ventilation extends MapSpot{

    private Ventilation spotToTeleport;

    public Ventilation(GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        super(gameSpriteSourceInformation, gameSpriteRenderInformation, null);
    }

    public void teleportPlayer(PlayableCharacter playableCharacter){

    }
}
