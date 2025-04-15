package cvut.cz.items;

import cvut.cz.GameSprite;
import cvut.cz.GameSpriteRenderInformation;

public class Item extends GameSprite {
    private final ItemInformation itemInformation;

    public Item(ItemInformation itemInformation) {
        super(itemInformation.gameSpriteSourceInformation(), new GameSpriteRenderInformation(0, 0, 0, 0, 0, 0));
        this.itemInformation = itemInformation;
    }

    public ItemInformation getItemInformation() { return itemInformation; }
}
