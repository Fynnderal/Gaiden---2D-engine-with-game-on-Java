package cvut.cz.Map;

import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

public class MapSpot extends GameSprite {
    protected Collision collision;

    public MapSpot(GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation, Collision collision) {
        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.collision = collision;
    }

    public Collision getCollision() { return collision; }
}
