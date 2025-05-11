package cvut.cz.Map;


import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

/**
 * Represents a specific point of interest on the map, such as doors or ventilation spots.
 */
public class MapSpot extends GameSprite {
    // The collision associated with this map spot.
    protected Collision collision;

    /**
     * Constructs a MapSpot object with the specified source information, render information, and collision.
     *
     * @param gameSpriteSourceInformation The source information for the map spot's sprite.
     * @param gameSpriteRenderInformation The render information for the map spot's sprite.
     * @param collision The collision object associated with this map spot.
     */
    public MapSpot(GameSpriteSourceInformation gameSpriteSourceInformation,
                   GameSpriteRenderInformation gameSpriteRenderInformation,
                    Collision collision) {
        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.collision = collision;
    }

    /**
     * Gets the collision object associated with this map spot.
     *
     * @return The collision object.
     */
    public Collision getCollision() { return collision; }
}
