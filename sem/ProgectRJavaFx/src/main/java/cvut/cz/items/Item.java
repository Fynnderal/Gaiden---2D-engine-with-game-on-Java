package cvut.cz.items;

import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

public class Item extends GameSprite implements Cloneable {
    private ItemInformation itemInformation;
    private int amount;

    public Item(GameSpriteSourceInformation gameSpriteSourceInformation, ItemInformation itemInformation, int amount) {
        this(gameSpriteSourceInformation, new GameSpriteRenderInformation(0, 0, 0, 0,0, 0), itemInformation, amount);
    }

    public Item(GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation, ItemInformation itemInformation, int amount) {
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
    public void setItemInformation(ItemInformation itemInformation) { this.itemInformation = itemInformation; }
}
