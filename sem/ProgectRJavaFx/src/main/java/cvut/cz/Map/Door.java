package cvut.cz.Map;

import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.items.Item;

/**
 * Represents a door in the game world.
 * A door can be opened using a specific item.
 */
public class Door extends MapSpot{

    // The item required to open the door
    private final String neededItem;

    // The sprite representing the opened door
    private final GameSprite openedDoor;

    /**
     * Constructs a Door object with the specified sprite, collision, and required item.
     *
     * @param gameSpriteSourceInformation The source information for the door's sprite.
     * @param gameSpriteRenderInformation The render information for the door's sprite.
     * @param collision The collision object associated with the door.
     * @param neededItem The name of the item required to open the door.
     * @param openedDoor The sprite representing the opened door.
     */
    public Door(GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation, Collision collision, String neededItem, GameSprite openedDoor) {
        super(gameSpriteSourceInformation, gameSpriteRenderInformation, collision);
        this.neededItem = neededItem;
        this.openedDoor = openedDoor;
    }

    /**
     * Attempts to open the door using the specified item.
     *
     * @param item The item used to attempt to open the door.
     * @return True if the door is successfully opened, false otherwise.
     */
    public boolean openDoor(Item item){
        if (neededItem.equals(item.getItemInformation().name())) {
            collision.setActive(false);
            this.gameSpriteRenderInformation.setTargetWidth(openedDoor.getGameSpriteRenderInformation().getTargetWidth());
            this.gameSpriteRenderInformation.setTargetHeight(openedDoor.getGameSpriteRenderInformation().getTargetHeight());

            this.gameSpriteSourceInformation = openedDoor.getGameSpriteSourceInformation();
            return true;
        }
        return false;
    }
}
