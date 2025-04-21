package cvut.cz.Map;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.items.Item;

public class Door extends MapSpot{

    private Item neededItem;


    public Door(GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation, Collision collision, Item neededItem) {
        super(gameSpriteSourceInformation, gameSpriteRenderInformation, collision);
        this.neededItem = neededItem;
    }

    public boolean openDoor(Item item){
        if (neededItem == item) {
            collision.setActive(false);
            return true;
        }
        return false;
    }
}
