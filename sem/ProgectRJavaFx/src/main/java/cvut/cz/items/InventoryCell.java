package cvut.cz.items;

public class InventoryCell {
    private final int coordinateX;
    private final int coordinateY;
    private boolean isItemEquipped;
    private Item item;

    public InventoryCell(int coordinateX, int coordinateY, Item item) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.item = item;
        isItemEquipped = false;
    }

    public int getCoordinateX() { return coordinateX; }
    public int getCoordinateY() { return coordinateY; }
    public Item getItem() { return item; }
    public int getItemAmount() { return item.getAmount(); }
    public boolean isItemEquipped() { return isItemEquipped; }

    public void setItemAmount(int itemAmount) {
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
