package cvut.cz.Map;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.characters.Directions;
import cvut.cz.characters.PlayableCharacter;

import java.util.logging.Logger;

/**
 * Represents a ventilation spot on the map that allows teleportation of a playable character
 */
public class Ventilation extends MapSpot{
    private static final Logger logger = Logger.getLogger(Ventilation.class.getName());

    private Ventilation spotToTeleport;

    /**
     * Constructs a Ventilation object with the specified source and render information.
     *
     * @param gameSpriteSourceInformation The source information for the ventilation spot's sprite.
     * @param gameSpriteRenderInformation The render information for the ventilation spot's sprite.
     */
    public Ventilation(GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        super(gameSpriteSourceInformation, gameSpriteRenderInformation, null);
    }

    /**
     * Sets the ventilation spot to which this spot can teleport a playable character.
     *
     * @param spotToTeleport The target ventilation spot for teleportation.
     */
    public void setSpotToTeleport(Ventilation spotToTeleport) {
        this.spotToTeleport = spotToTeleport;
    }

    /**
     * Teleports the specified playable character to the connected ventilation spot.
     * If no target spot is set does nothing.
     *
     * @param playableCharacter The playable character to teleport.
     */
    public void teleportPlayer(PlayableCharacter playableCharacter, GameMap map){
        if (spotToTeleport == null){
            logger.warning("No spot to teleport");
            return;
        }

        int oldWorldX = playableCharacter.getGameSpriteRenderInformation().getWorldCoordinateX();
        int oldWorldY = playableCharacter.getGameSpriteRenderInformation().getWorldCoordinateY();

        playableCharacter.getGameSpriteRenderInformation().setWorldCoordinateX(spotToTeleport.getGameSpriteRenderInformation().getWorldCoordinateX());
        playableCharacter.getGameSpriteRenderInformation().setWorldCoordinateY(spotToTeleport.getGameSpriteRenderInformation().getWorldCoordinateY());

        int dy = playableCharacter.getGameSpriteRenderInformation().getWorldCoordinateY() - oldWorldY;
        int dx = playableCharacter.getGameSpriteRenderInformation().getWorldCoordinateX() - oldWorldX;

        if (dx > 0)
            map.translateMap(Directions.LEFT, dx);
        else
            map.translateMap(Directions.RIGHT, -dx);
        if (dy > 0)
            map.translateMap(Directions.UP, dy);
        else
            map.translateMap(Directions.DOWN, -dy);
    }
}
