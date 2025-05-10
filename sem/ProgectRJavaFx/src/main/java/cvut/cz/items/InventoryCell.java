package cvut.cz.items;

/**
 * Represents one cell of the inventory.
 * It keeps information about coordinates and items that are saved in this cell.
 */
public class InventoryCell {
    private final int coordinateX;
    private final int coordinateY;

    private boolean isItemEquipped;
    private Item item;

    /**
     * Constructs an InventoryCell with the specified coordinates and item.
     *
     * @param coordinateX The X-coordinate of the cell.
     * @param coordinateY The Y-coordinate of the cell.
     * @param item The item to be stored in the cell, or null if the cell is empty.
     */
    public InventoryCell(int coordinateX,int coordinateY, Item item) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.item = item;
        isItemEquipped = false;
    }

    /**
     * Gets the X-coordinate of the inventory cell.
     *
     * @return The X-coordinate of the cell.
     */
    public int getCoordinateX() { return coordinateX; }

    /**
     * Gets the Y-coordinate of the inventory cell.
     *
     * @return The Y-coordinate of the cell.
     */
    public int getCoordinateY() { return coordinateY; }

    /**
     * Gets the item stored in the inventory cell.
     *
     * @return The item in the cell, or null if the cell is empty.
     */
    public Item getItem() { return item; }

    /**
     * Gets the amount of items stored in the cell.
     *
     * @return The amount of items, or 0 if no item is stored in the cell.
     */
    public int getItemAmount() {
        if (item == null)
            return 0;

        return item.getAmount();
    }

    /**
     * Checks if the item in the cell is equipped.
     *
     * @return True if the item is equipped, false otherwise.
     */
    public boolean getIsItemEquipped() { return isItemEquipped; }

    /**
     * Sets the amount of the item stored in the cell.
     * If the amount is less than or equal to zero, the item is removed from the cell.
     *
     * @param itemAmount The new amount of the item. If less than or equal to zero, the item is set to null.
     */
    public void setItemAmount(int itemAmount) {
        if (item == null)
            return;

        item.setAmount(itemAmount);
        if (itemAmount <= 0)
        {
            this.item = null;
        }
    }

    /**
     * Sets the item stored in the inventory cell.
     *
     * @param item The new item to be stored in the cell.
     */
    public void setItem(Item item) { this.item = item; }

    /**
     * Sets whether the item in the cell is equipped.
     *
     * @param isItemEquipped True if the item is equipped, false otherwise.
     */
    public void setIsItemEquipped(boolean isItemEquipped) { this.isItemEquipped = isItemEquipped; }

}
