package cvut.cz.items;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.impl.*;
import cvut.cz.GameSprite;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;

//@JsonFilter("itemFilter")
public class Item {
    @JsonProperty("ImageURL")
    protected URL imageURL;
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


    private Item() {}

    public String getName() { return name; }
    public boolean geyIsBroken() { return isBroken; }
    public int getAmount() { return amount; }
    public void setName(String name) { this.name = name; }
    public void setBroken(boolean broken) { isBroken = broken; }
    public void setAmount(int amount) { this.amount = amount; }

    @Override
    public String toString() {
        return "ImageURL: " + imageURL + "\nName: " + name + "\nBroken: " + isBroken + "\nAmount: " + amount + "\nCanBeCombinedWithInto: " + canBeCombinedWithInto.keySet() + ":" + canBeCombinedWithInto.values() + "\nCanEquipped: " + canBeEquipped + "\nCanBeUsed: " + canBeUsed +"\nCanBeDiscarded: " + canBeDiscarded;
    }
}
