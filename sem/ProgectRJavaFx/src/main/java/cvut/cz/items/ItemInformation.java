package cvut.cz.items;

import java.net.URL;
import java.util.Map;

public class ItemInformation {
    private int sourceX;
    private int sourceY;
    private int sourceWidth;
    private int sourceHeight;
    private String name;
    private URL image;
    private boolean isBroken;
    private Map<String, String> canBeCombinedWithInto;
    boolean canBeEquipped;
    boolean canBeUsed;
    boolean canBeDiscarded;
    int standardAmount;

    public ItemInformation(int sourceX, int sourceY, int sourceWidth, int sourceHeight, String name, URL image, boolean isBroken, Map<String, String> canBeCombinedWithInto, boolean canBeEquipped, boolean canBeUsed, boolean canBeDiscarded, int standardAmount) {
        this.name = name;
        this.image = image;
        this.isBroken = isBroken;
        this.canBeCombinedWithInto = canBeCombinedWithInto;
        this.canBeEquipped = canBeEquipped;
        this.canBeUsed = canBeUsed;
        this.canBeDiscarded = canBeDiscarded;
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.sourceWidth = sourceWidth;
        this.sourceHeight = sourceHeight;
        this.standardAmount = standardAmount;

    }

    public String getName() { return name; }
    public URL getImage() { return image; }
    public boolean isBroken() { return isBroken; }
    public Map<String, String> getCanBeCombinedWithInto() { return canBeCombinedWithInto; }
    public boolean isCanBeEquipped() { return canBeEquipped; }
    public boolean isCanBeUsed() { return canBeUsed; }
    public boolean isCanBeDiscarded() { return canBeDiscarded; }
    public int getSourceX() { return sourceX; }
    public int getSourceY() { return sourceY; }
    public int getSourceWidth() { return sourceWidth; }
    public int getSourceHeight() { return sourceHeight; }
    public int getStandardAmount() { return standardAmount; }
}
