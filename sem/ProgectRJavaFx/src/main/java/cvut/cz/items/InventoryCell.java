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

    public int getCoordinateX() { return coordinateX; }
    public int getCoordinateY() { return coordinateY; }
}
