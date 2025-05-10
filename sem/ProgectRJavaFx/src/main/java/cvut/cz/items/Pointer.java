package cvut.cz.items;


import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

/**
 * Represents a Pointer in the inventory.
 * This class is used to create a pointer object with specific source and render information.
 */
public class Pointer extends GameSprite {
    /**
     * Constructor for creating a Pointer object.
     *
     * @param gameSpriteSourceInformation The source information for the game sprite.
     */
    public Pointer(GameSpriteSourceInformation gameSpriteSourceInformation){
        super(gameSpriteSourceInformation, new GameSpriteRenderInformation(0, 0, 0, 0, 0, 0));
    }
}
