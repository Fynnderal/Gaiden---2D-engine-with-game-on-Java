package cvut.cz.Map;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.items.Inventory;
import cvut.cz.items.Item;

/**
 * Represents a vending machine in the game.
 */
public class VendingMachine extends MapSpot{

    // The item that the vending machine gives
    private final Item itemToGive;

    // True if the vending machine has been already used
    private boolean isUsed;


    /**
     * Creates a vending machine object.
     *
     * @param  gameSpriteSourceInformation The information about the source image of the sprite
     * @param gameSpriteRenderInformation The information about the rendering of the sprite
     * @param itemToGive The item that the vending machine gives
     */
    public VendingMachine(GameSpriteSourceInformation gameSpriteSourceInformation,
                   GameSpriteRenderInformation gameSpriteRenderInformation,
                   Item itemToGive) {

        super(gameSpriteSourceInformation, gameSpriteRenderInformation, null);
        this.itemToGive = itemToGive;
        isUsed = false;
    }
    /**
     *
     * Provides the item from the vending machine if it has not already been used.
     *
     */
    public void giveItem(Inventory inventory){
        if (isUsed)
            return;

        if (inventory.addItemToInventory(itemToGive))
            isUsed = true;
    }
}
