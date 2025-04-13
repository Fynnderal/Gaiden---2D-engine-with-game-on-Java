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


    public Item(ItemInformation itemInformation) {
        super(itemInformation.getImage(), itemInformation.getSourceX(), itemInformation.getSourceY(), itemInformation.getSourceWidth(), itemInformation.getSourceHeight(), 0, 0, 0, 0, 0, 0);
        this.name = itemInformation.getName();
        this.isBroken = itemInformation.isBroken();
        this.amount = itemInformation.getStandardAmount();
        this.canBeCombinedWithInto = itemInformation.getCanBeCombinedWithInto();
        this.canBeEquipped = itemInformation.isCanBeEquipped();
        this.canBeUsed = itemInformation.isCanBeUsed();
        this.canBeDiscarded = itemInformation.isCanBeDiscarded();
    }

    public String getName() { return name; }
    public boolean getIsBroken() { return isBroken; }
    public int getAmount() { return amount; }
    public Map<String, String> getCanBeCombinedWithInto() { return canBeCombinedWithInto; }
    public boolean getCanBeEquipped() { return canBeEquipped; }
    public boolean getCanBeUsed() { return canBeUsed; }
    public boolean getCanBeDiscarded() { return canBeDiscarded; }


    public void setName(String name) { this.name = name; }
    public void setBroken(boolean broken) { isBroken = broken; }
    public void setAmount(int amount) { this.amount = amount; }


    @Override
    public String toString() {
        return "Name: " + name + "\nBroken: " + isBroken + "\nAmount: " + amount + "\nCanBeCombinedWithInto: " + canBeCombinedWithInto.keySet() + ":" + canBeCombinedWithInto.values() + "\nCanEquipped: " + canBeEquipped + "\nCanBeUsed: " + canBeUsed +"\nCanBeDiscarded: " + canBeDiscarded;
    }
}
