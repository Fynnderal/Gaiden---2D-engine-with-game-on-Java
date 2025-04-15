package cvut.cz.items;

public class InventoryCell {
    private final int coordinateX;
    private final int coordinateY;
    private Item item;
    private int itemAmount;

    public InventoryCell(int coordinateX, int coordinateY, Item item) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.item = item;
        itemAmount = item == null ? 0 : 1;
    }

    public void useItem(){
        itemAmount--;
        if (itemAmount <= 0)
            this.item = null;
    }



    public int getCoordinateX() { return coordinateX; }
    public int getCoordinateY() { return coordinateY; }
    public Item getItem() { return item; }
    public int getItemAmount() { return itemAmount; }

    public void setItemAmount(int itemAmount) { this.itemAmount = itemAmount; }
    public void setItem(Item item) { this.item = item; }
}
