package cvut.cz.items;

public class InventoryCell {
    private int coordinateX;
    private int coordinateY;
    private Item item;

    public InventoryCell(int coordinateX, int coordinateY, Item item) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.item = item;
    }

    public void useItem(){
        item.setAmount(item.getAmount() - 1);
        if (item.getAmount() <= 0)
            this.item = null;
    }

    public int getCoordinateX() { return coordinateX; }
    public int getCoordinateY() { return coordinateY; }
    public Item getItem() { return item; }

    public void setItem(Item item) { this.item = item; }
}
