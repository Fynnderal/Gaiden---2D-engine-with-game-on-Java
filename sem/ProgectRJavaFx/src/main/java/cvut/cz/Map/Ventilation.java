package cvut.cz.Map;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.characters.PlayableCharacter;

import java.util.logging.Logger;

public class Ventilation extends MapSpot{
    private static final Logger logger = Logger.getLogger(Ventilation.class.getName());

    private Ventilation spotToTeleport;

    public Ventilation(GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        super(gameSpriteSourceInformation, gameSpriteRenderInformation, null);
    }

    public void setSpotToTeleport(Ventilation spotToTeleport) {
        this.spotToTeleport = spotToTeleport;
    }

    public void teleportPlayer(PlayableCharacter playableCharacter){
        if (spotToTeleport == null){
            logger.warning("No spot to teleport");
            return;
        }

        playableCharacter.getGameSpriteRenderInformation().setWorldCoordinateX(spotToTeleport.getGameSpriteRenderInformation().getWorldCoordinateX());
        playableCharacter.getGameSpriteRenderInformation().setWorldCoordinateY(spotToTeleport.getGameSpriteRenderInformation().getWorldCoordinateY());
    }
}
