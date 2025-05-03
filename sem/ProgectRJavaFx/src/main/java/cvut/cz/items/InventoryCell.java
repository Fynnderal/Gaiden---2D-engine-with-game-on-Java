package cvut.cz.items;


public class InventoryCell {
    private final int coordinateX;
    private final int coordinateY;
    private boolean isItemEquipped;
    private Item item;

    public InventoryCell(int coordinateX,int coordinateY, Item item) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.item = item;
        isItemEquipped = false;
    }

    public int getCoordinateX() { return coordinateX; }
    public int getCoordinateY() { return coordinateY; }
    public Item getItem() { return item; }
    public int getItemAmount() {
        if (item == null)
            return 0;

        return item.getAmount();
    }
    public boolean getIsItemEquipped() { return isItemEquipped; }

    public void setItemAmount(int itemAmount) {
        if (item == null)
            return;

        item.setAmount(itemAmount);
        if (itemAmount <= 0)
        {
            this.item = null;
        }
    }
    public void setItem(Item item) {
        this.item = item;
    }
    public void setIsItemEquipped(boolean isItemEquipped) { this.isItemEquipped = isItemEquipped; }

}
