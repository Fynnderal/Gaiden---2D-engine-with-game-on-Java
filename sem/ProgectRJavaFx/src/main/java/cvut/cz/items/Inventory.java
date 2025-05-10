package cvut.cz.items;


import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents the inventory of the player.
 * It manages the creation of the inventory, adding items, and organizing inventory cells.
 */
public class Inventory extends GameSprite {
    private final InventoryInformation inventoryInformation;
    private final InventoryCellGeneralInformation inventoryCellGeneralInformation;

    //items that inventory contains
    private final List<Item> items;

    //cells of which inventory consist
    private final List<InventoryCell> cells;

    //cell in which currently equipped item is located
    private InventoryCell equippedCell;

    //pointer that is used by player to choose item
    private Pointer pointer;

    //all items that inventory can contain. Key is the name of the item, Item is instance of this item.
    private Map<String, Item> possibleItems;

    private final InventoryController inventoryController;

    /**
     * Constructs an Inventory object with the specified information and sprite details.
     *
     * @param inventoryInformation Information about the inventory layout and properties.
     * @param inventoryCellGeneralInformation General information about the inventory cells.
     * @param gameSpriteSourceInformation Source information for the inventory's sprite.
     * @param gameSpriteRenderInformation Render information for the inventory's sprite.
     */
    public Inventory(InventoryInformation inventoryInformation, InventoryCellGeneralInformation inventoryCellGeneralInformation,
                     GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {

        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.gameSpriteRenderInformation.setWorldCoordinateY(0);
        this.gameSpriteRenderInformation.setWorldCoordinateX(0);
        this.inventoryInformation = inventoryInformation;
        this.inventoryCellGeneralInformation = inventoryCellGeneralInformation;
        this.cells = new ArrayList<>();
        this.items =  new ArrayList<>();
        this.inventoryController = new InventoryController(this);
        setCells();
    }

    /**
     * Adds an item to the inventory.
     * If there is no space available, the item is not added.
     *
     * @param item The item to be added.
     * @return True if the item was added successfully, false otherwise.
     */
    public boolean addItemToInventory(Item item) {
        if (item == null)
            return false;

        //first available cell that contains no item
        InventoryCell emptyCell = null;

        //checks all cells if there is the same item in the inventory
        for (InventoryCell cell : cells) {
            if (cell.getItem() == null) {
                if (emptyCell == null)
                    emptyCell = cell;
                continue;
            }
            //if cell contains the same item as item, it just increases number of items.
            if (cell.getItem().getItemInformation().name().equals(item.getItemInformation().name())) {
                cell.setItemAmount(cell.getItemAmount() + item.getAmount());
                adjustItem(item, cell.getCoordinateX(), cell.getCoordinateY());
                return true;
            }
        }

        //if there is empty cell in the inventory, add item to this cell
        if (emptyCell != null) {
            adjustItem(item, emptyCell.getCoordinateX(), emptyCell.getCoordinateY());
            emptyCell.setItem(item);
            return true;
        }

        return false;
    }

    /**
     * Creates and configures the cells of the inventory.
     */
    private void setCells() {
        int columnCounter = 0;
        int rowCounter = 0;
        int currentX, currentY;
        Item currentItem;

        for (int i = 0; i < inventoryInformation.numberOfCells(); i++) {
            if (columnCounter == inventoryInformation.numberOfCellsInRow()) {
                rowCounter++;
                columnCounter = 0;
            }

            //X coordinate (in pixels) of the current cell
            currentX = inventoryInformation.firstCellCoordinateX() + columnCounter * (inventoryCellGeneralInformation.cellWidth() + inventoryInformation.gapBetweenCellsX());
            //Y coordinate (in pixels) of the current cell
            currentY = inventoryInformation.firstCellCoordinateY() + rowCounter * (inventoryCellGeneralInformation.cellHeight() + inventoryInformation.gapBetweenCellsY());

            //if there is more cells than items, next cell must  contain nothing
            if (i >= items.size())
                currentItem = null;
            else {
                currentItem = items.get(i);
                adjustItem(currentItem, currentX, currentY);
            }


            cells.add(new InventoryCell(currentX, currentY, currentItem));
            columnCounter++;
        }
    }

    /**
     * Finds the inventory cell that contains an item with a specific name.
     *
     * @param nameOfItem The name of the item to find.
     * @return The inventory cell containing the item, or null if the item is not found.
     */
    public InventoryCell getInventoryCellByName(String nameOfItem){
        for (InventoryCell inventoryCell: cells) {
            if (inventoryCell.getItem() == null)
                continue;

            if (inventoryCell.getItem().getItemInformation().name().equals(nameOfItem))
                return inventoryCell;
        }
        return null;
    }

    /**
     * Prepares an item to be added to an inventory cell.
     * Adjusts the item's size and coordinates to fit within the cell.
     *
     * @param item The item to adjust.
     * @param inventoryCellX The X coordinate (in pixels) of the target inventory cell.
     * @param inventoryCellY The Y coordinate (in pixels) of the target inventory cell.
     */
    public void adjustItem(Item item, int inventoryCellX, int inventoryCellY) {
        /*
        It calculates scale factor, than multiplies inventory's sprite by it to fit the inventory cell
        Scale factor must be minimum of maximal width scale factor and maximal height scale factor to preserve proportions
         */
        double widthScaleFactor = (double)inventoryCellGeneralInformation.itemWidthInCell() / item.getGameSpriteSourceInformation().getSourceWidth();
        double heightScaleFactor = (double)inventoryCellGeneralInformation.itemHeightInCell() / item.getGameSpriteSourceInformation().getSourceHeight();
        double scaleFactor = Math.min(widthScaleFactor, heightScaleFactor);


        item.getGameSpriteRenderInformation().setTargetWidth((int) (item.getGameSpriteSourceInformation().getSourceWidth() * scaleFactor));
        item.getGameSpriteRenderInformation().setTargetHeight((int) (item.getGameSpriteSourceInformation().getSourceHeight() * scaleFactor));

        int cellCenterX =  inventoryCellGeneralInformation.itemCoordinateXRelativeToCell() + inventoryCellGeneralInformation.itemWidthInCell() / 2;
        int cellCenterY = inventoryCellGeneralInformation.itemCoordinateYRelativeToCell() + inventoryCellGeneralInformation.itemHeightInCell() / 2;

        /*
        Center of the item's sprite must be in the center of the inventory cell.
        It means that difference between item's X coordinate and cell's X coordinate must equal to a half of item's sprite width.
        In a similar way difference between item's Y coordinate and cell's Y coordinate must equal to a half of item's sprite height.
         */
        int itemCoordinateX = cellCenterX - item.getGameSpriteRenderInformation().getTargetWidth() / 2;
        int itemCoordinateY = cellCenterY - item.getGameSpriteRenderInformation().getTargetHeight() / 2;

        item.getGameSpriteRenderInformation().setScreenCoordinateX(inventoryCellX + itemCoordinateX);
        item.getGameSpriteRenderInformation().setScreenCoordinateY(inventoryCellY + itemCoordinateY);
    }

    /**
     * Clears all items from the inventory.
     */
    public void deleteitems() {
        for (InventoryCell cell : cells) {
            if (cell == null || cell.getItem() == null)
                continue;
            cell.setItem(null);
        }
    }
    /**
     * Retrieves the list of items currently in the inventory.
     *
     * @return A list of items in the inventory.
     */
    public List<Item> getItems() {
        items.clear();
        for (InventoryCell cell: cells) {
            if (cell == null || cell.getItem() == null)
                continue;
            items.add(cell.getItem());
        }
        return  List.copyOf(items);
    }

    public InventoryCell getSelectedInventoryCell(){ return cells.get(inventoryController.getCurrentCell()); }
    public List<InventoryCell> getCells() { return cells; }
    public InventoryCell getEquippedCell() { return equippedCell; }
    public InventoryCellGeneralInformation getInventoryCellGeneralInformation() { return inventoryCellGeneralInformation; }
    public InventoryInformation getInventoryInformation() { return inventoryInformation; }
    public Pointer getPointer() { return pointer; }
    public Map<String, Item> getPossibleItems() {return possibleItems;}
    public InventoryController getInventoryController() { return inventoryController; }

    /**
     * Attaches a pointer to the inventory.
     *
     * @param pointer The pointer to be attached.
     */
    public void setPointer(Pointer pointer) {
        //By default pointer's coordinates and size equal to coordinates of the first cell
        pointer.getGameSpriteRenderInformation().setScreenCoordinateX(inventoryInformation.firstCellCoordinateX());
        pointer.getGameSpriteRenderInformation().setScreenCoordinateY(inventoryInformation.firstCellCoordinateY());
        pointer.getGameSpriteRenderInformation().setTargetWidth(inventoryCellGeneralInformation.cellWidth());
        pointer.getGameSpriteRenderInformation().setTargetHeight(inventoryCellGeneralInformation.cellHeight());
        this.pointer = pointer;
    }

    /**
     * Sets the list of items in the inventory.
     *
     * @param items The list of items to be set.
     */
    public void setItems(List<Item> items) {
        if (items == null)
            return;

        for (Item item : items) {
            addItemToInventory(item);
        }
    }

    public void setPossibleItems(Map<String, Item> possibleItems) {this.possibleItems = possibleItems; }
    public void setEquippedCell(InventoryCell equippedCell) { this.equippedCell = equippedCell; }
}
