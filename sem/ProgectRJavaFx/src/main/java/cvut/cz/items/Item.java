package cvut.cz.items;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.impl.*;
import cvut.cz.GameSprite;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;

//@JsonFilter("itemFilter")
public class Item extends GameSprite {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("IsBroken")
    protected boolean isBroken;
    @JsonProperty("Amount")
    protected int amount;
    @JsonProperty("canBeCombinedWithInto")
    protected Map<String, String> canBeCombinedWithInto;
    @JsonProperty("canBeEquipped")
    protected boolean canBeEquipped;
    @JsonProperty("canBeUsed")
    protected boolean canBeUsed;
    @JsonProperty("canBeDiscarded")
    protected boolean canBeDiscarded;


    public Item(URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, String name, boolean isBroken, int amount, Map<String, String> canBeCombinedWithInto, boolean canBeEquipped, boolean canBeUsed, boolean canBeDiscarded) {
        super(pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, 0, 0, 0, 0, 0, 0);
        this.name = name;
        this.isBroken = isBroken;
        this.amount = amount;
        this.canBeCombinedWithInto = canBeCombinedWithInto;
        this.canBeEquipped = canBeEquipped;
        this.canBeUsed = canBeUsed;
        this.canBeDiscarded = canBeDiscarded;
    }

    public String getName() { return name; }
    public boolean geyIsBroken() { return isBroken; }
    public int getAmount() { return amount; }
    public void setName(String name) { this.name = name; }
    public void setBroken(boolean broken) { isBroken = broken; }
    public void setAmount(int amount) { this.amount = amount; }

    @Override
    public String toString() {
        return "Name: " + name + "\nBroken: " + isBroken + "\nAmount: " + amount + "\nCanBeCombinedWithInto: " + canBeCombinedWithInto.keySet() + ":" + canBeCombinedWithInto.values() + "\nCanEquipped: " + canBeEquipped + "\nCanBeUsed: " + canBeUsed +"\nCanBeDiscarded: " + canBeDiscarded;
    }
}
