package cvut.cz.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

public class Item extends GameSprite implements Cloneable {
    private final ItemInformation itemInformation;
    private int amount;

    @JsonCreator
    public Item(@JsonProperty("gameSpriteSourceInformation") GameSpriteSourceInformation gameSpriteSourceInformation,
                @JsonProperty("itemInformation") ItemInformation itemInformation, @JsonProperty("amount") int amount) {

        this(gameSpriteSourceInformation, new GameSpriteRenderInformation(0, 0, 0, 0,0, 0), itemInformation, amount);
    }

    public Item(GameSpriteSourceInformation gameSpriteSourceInformation,  GameSpriteRenderInformation gameSpriteRenderInformation,
                ItemInformation itemInformation, int amount) {

        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.itemInformation = itemInformation;
        this.amount = amount;
    }

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

    public ItemInformation getItemInformation() { return itemInformation; }
    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount; }
}
