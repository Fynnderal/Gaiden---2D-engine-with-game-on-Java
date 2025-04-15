package cvut.cz.Map;

import cvut.cz.GameSpriteRenderInformation;
import cvut.cz.GameSpriteSourceInformation;
import cvut.cz.items.Item;

public class Door extends MapSpot{

    private Item neededItem;
    private Collision collision;

    public Door(GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
    }

    public void openDoor(Item item){

    }
}
