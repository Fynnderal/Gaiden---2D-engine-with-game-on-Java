package cvut.cz.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

/**
 * Represents an item in the game, extending the functionality of a GameSprite.
 * This class includes information about the item and its quantity.
 */
public class Item extends GameSprite implements Cloneable {
    //Information about the item, such as its properties and attributes.
    private final ItemInformation itemInformation;
    private int amount;

    /**
     * Constructor for creating an Item object.
     *
     * @param gameSpriteSourceInformation The source information for the game sprite.
     * @param itemInformation The information about the item.
     * @param amount The quantity of the item.
     */
    @JsonCreator
    public Item(@JsonProperty("gameSpriteSourceInformation") GameSpriteSourceInformation gameSpriteSourceInformation,
                @JsonProperty("itemInformation") ItemInformation itemInformation, @JsonProperty("amount") int amount) {

        this(gameSpriteSourceInformation, new GameSpriteRenderInformation(0, 0, 0, 0,0, 0), itemInformation, amount);
    }

    /**
     * Constructor for creating an Item object with detailed render and source information.
     *
     * @param gameSpriteSourceInformation The source information for the game sprite.
     * @param gameSpriteRenderInformation The render information for the game sprite.
     * @param itemInformation The information about the item.
     * @param amount The quantity of the item.
     */
    public Item(GameSpriteSourceInformation gameSpriteSourceInformation,  GameSpriteRenderInformation gameSpriteRenderInformation,
                ItemInformation itemInformation, int amount) {

        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.itemInformation = itemInformation;
        this.amount = amount;
    }

    /**
     * Creates and returns a copy of this Item object.
     *
     * @return A cloned Item object, or null if cloning is not supported.
     */
    @Override
    public Item clone() {
        try{
            Item cloned = (Item) super.clone();
            cloned.setGameSpriteSourceInformation(gameSpriteSourceInformation.clone());
            cloned.setGameSpriteRenderInformation(gameSpriteRenderInformation.clone());
            return cloned;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * Gets the information about the item.
     *
     * @return The item information.
     */
    public ItemInformation getItemInformation() { return itemInformation; }

    /**
     * Gets the quantity of the item.
     *
     * @return The quantity of the item.
     */
    public int getAmount() { return amount; }

    /**
     * Sets the quantity of the item.
     *
     * @param amount The new quantity of the item.
     */
    public void setAmount(int amount) { this.amount = amount; }
}
