package cvut.cz.items;


import com.fasterxml.jackson.databind.ObjectMapper;
import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.characters.Directions;
import cvut.cz.characters.PlayableCharacter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Inventory extends GameSprite {
    Logger logger = Logger.getLogger(Inventory.class.getName());

    private final static ObjectMapper mapper = new ObjectMapper();

    protected static final String pathToItems = "playerItems.json";

    private final InventoryInformation inventoryInformation;
    private final List<Item> items;
    private final List<InventoryCell> cells;
    private final InventoryCellGeneralInformation inventoryCellGeneralInformation;

    private int currentCell;
    private InventoryCell equippedCell;
    private Pointer pointer;
    private Map<String, Item> possibleItems;

    private PlayableCharacter character;

    public Inventory(InventoryInformation inventoryInformation, InventoryCellGeneralInformation inventoryCellGeneralInformation,
                     GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {

        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.gameSpriteRenderInformation.setWorldCoordinateY(0);
        this.gameSpriteRenderInformation.setWorldCoordinateX(0);
        this.inventoryInformation = inventoryInformation;
        this.inventoryCellGeneralInformation = inventoryCellGeneralInformation;
        this.currentCell = 0;
        this.cells = new ArrayList<>();
        this.items =  new ArrayList<>();

        readAvailableItems();
        setCells();
    }

    public void addItemToInventory(Item item) {
        if (item == null)
            return;

        InventoryCell emptyCell = null;

        for (InventoryCell cell : cells) {
            if (cell.getItem() == null) {
                if (emptyCell == null)
                    emptyCell = cell;
                continue;
            }
            if (cell.getItem().getItemInformation().name().equals(item.getItemInformation().name())) {
                cell.setItemAmount(cell.getItemAmount() + item.getAmount());
                adjustItem(item, cell.getCoordinateX(), cell.getCoordinateY());
                return;
            }
        }

        if (emptyCell != null) {
            adjustItem(item, emptyCell.getCoordinateX(), emptyCell.getCoordinateY());
            emptyCell.setItem(item);
        }
    }

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

            currentX = inventoryInformation.firstCellCoordinateX() + columnCounter * (inventoryCellGeneralInformation.cellWidth() + inventoryInformation.gapBetweenCellsX());
            currentY = inventoryInformation.firstCellCoordinateY() + rowCounter * (inventoryCellGeneralInformation.cellHeight() + inventoryInformation.gapBetweenCellsY());

            if (items == null || i >= items.size())
                currentItem = null;
            else {
                currentItem = items.get(i);
                adjustItem(currentItem, currentX, currentY);
            }


            cells.add(new InventoryCell(currentX, currentY, currentItem));
            columnCounter++;
        }
    }

    public void movePointer(Directions direction) {
        if (pointer == null)
            return;

        if (cells.isEmpty())
            logger.severe("There is no inventory cells");

        switch(direction){
            case RIGHT:
                currentCell = ++currentCell % cells.size();
                break;

            case LEFT:
                currentCell = --currentCell % cells.size();
                if (currentCell < 0)
                    currentCell = cells.size() +currentCell;
                break;

            case DOWN:
                currentCell = (currentCell + inventoryInformation.numberOfCellsInRow()) % cells.size();
                break;
            case UP:
                currentCell = (currentCell - inventoryInformation.numberOfCellsInRow()) % cells.size();
                if (currentCell < 0)
                    currentCell = cells.size() +currentCell;
                break;

        }
        pointer.getGameSpriteRenderInformation().setScreenCoordinateX(cells.get(currentCell).getCoordinateX());
        pointer.getGameSpriteRenderInformation().setScreenCoordinateY(cells.get(currentCell).getCoordinateY());
    }

    public void combineCells(InventoryCell firstCell, InventoryCell secondCell){
        if (possibleItems == null)
            return;

        if (firstCell == null || secondCell == null)
            return;

        if (firstCell.getItem() == null || secondCell.getItem() == null)
            return;

        Map<String, String> canBeCombinedWithInto1 = firstCell.getItem().getItemInformation().canBeCombinedWithInto();
        Map<String, String> canBeCombinedWithInto2 = secondCell.getItem().getItemInformation().canBeCombinedWithInto();

        if (canBeCombinedWithInto1 == null || canBeCombinedWithInto2 == null)
            return;

        if (canBeCombinedWithInto1.containsKey(secondCell.getItem().getItemInformation().name())){
            String resultItemName = canBeCombinedWithInto1.get(secondCell.getItem().getItemInformation().name());

            for (InventoryCell inventoryCell: cells) {
                if (inventoryCell.getItem() == null)
                    continue;

                if (inventoryCell.getItem().getItemInformation().name().equals(resultItemName)) {
                    inventoryCell.setItemAmount(inventoryCell.getItem().getAmount() + 1);
                    discardCell(firstCell);
                    discardCell(secondCell);
                    return;
                }
            }
            firstCell.setItem(adjustItem(possibleItems.get(resultItemName).clone(), firstCell.getCoordinateX(), firstCell.getCoordinateY()));
            discardCell(secondCell);
        }
    }

    public void discardCell(InventoryCell inventoryCell){
        if (inventoryCell == null)
            return;

        if (inventoryCell.getItem() == null)
            return;

        if (!inventoryCell.getItem().getItemInformation().canBeDiscarded())
            return;

        inventoryCell.setItemAmount(inventoryCell.getItem().getAmount() - 1);
    }

    public void equipCell(InventoryCell inventoryCell){
        if (inventoryCell == null)
            return;

        if (inventoryCell.getItem() == null)
            return;

        if (!inventoryCell.getItem().getItemInformation().canBeEquipped())
            return;

        if (equippedCell == inventoryCell) {
            equippedCell = null;
            inventoryCell.setIsItemEquipped(false);
        }else{
            if (equippedCell != null)
                equippedCell.setIsItemEquipped(false);
            inventoryCell.setIsItemEquipped(true);
            equippedCell = inventoryCell;
        }
    }

    public void useCell(InventoryCell inventoryCell) {
        if (inventoryCell == null)
            return;

        if (inventoryCell.getItem() == null)
            return;

        if (!inventoryCell.getItem().getItemInformation().canBeUsed())
            return;

        character.useItem(inventoryCell.getItem());

        inventoryCell.setItemAmount(inventoryCell.getItem().getAmount() - 1);
    }

    public InventoryCell getInventoryCellByName(String nameOfItem){
        for (InventoryCell inventoryCell: cells) {
            if (inventoryCell.getItem() == null)
                continue;

            if (inventoryCell.getItem().getItemInformation().name().equals(nameOfItem))
                return inventoryCell;
        }
        return null;
    }

    private Item adjustItem(Item item, int inventoryCellX, int inventoryCellY) {
        double widthScaleFactor = (double)inventoryCellGeneralInformation.itemWidthInCell() / item.getGameSpriteSourceInformation().getSourceWidth();
        double heightScaleFactor = (double)inventoryCellGeneralInformation.itemHeightInCell() / item.getGameSpriteSourceInformation().getSourceHeight();
        double scaleFactor = Math.min(widthScaleFactor, heightScaleFactor);

        item.getGameSpriteRenderInformation().setTargetWidth((int) (item.getGameSpriteSourceInformation().getSourceWidth() * scaleFactor));
        item.getGameSpriteRenderInformation().setTargetHeight((int) (item.getGameSpriteSourceInformation().getSourceHeight() * scaleFactor));

        int cellCenterX =  inventoryCellGeneralInformation.itemCoordinateXRelativeToCell() + inventoryCellGeneralInformation.itemWidthInCell() / 2;
        int cellCenterY = inventoryCellGeneralInformation.itemCoordinateYRelativeToCell() + inventoryCellGeneralInformation.itemHeightInCell() / 2;

        int itemCoordinateX = cellCenterX - item.getGameSpriteRenderInformation().getTargetWidth() / 2;
        int itemCoordinateY = cellCenterY - item.getGameSpriteRenderInformation().getTargetHeight() / 2;

        item.getGameSpriteRenderInformation().setScreenCoordinateX(inventoryCellX + itemCoordinateX);
        item.getGameSpriteRenderInformation().setScreenCoordinateY(inventoryCellY + itemCoordinateY);

        return item;
    }

    public void readAvailableItems() {
        try (FileReader fileReader = new FileReader(pathToItems)) {
            Object[] temp = mapper.readValue(fileReader, Item[].class);
            for (Object object: temp){
                if (object instanceof Item){
                    items.add((Item) object);
                }
            }
        } catch (IOException | ClassCastException e) {
            System.err.println("[ERROR] Problem with reading json file. Problem: " + e.getMessage());
        }

    }

    public void writeAvailableItems() {
        try (FileWriter fileWriter = new FileWriter(pathToItems)) {
            mapper.writeValue(fileWriter, getItems().toArray());
        } catch (IOException e) {
            System.err.println("[ERROR] Problem with writing into json file. Problem: " + e.getMessage());
        }
    }

    public List<Item> getItems() {
        items.clear();
        for (InventoryCell cell: cells) {
            if (cell == null || cell.getItem() == null)
                continue;
            items.add(cell.getItem());
        }
        return items;
    }

    public InventoryCell getSelectedInventoryCell(){ return cells.get(currentCell); }
    public List<InventoryCell> getCells() { return cells; }
    public InventoryCell getEquippedCell() { return equippedCell; }
    public InventoryCellGeneralInformation getInventoryCellGeneralInformation() { return inventoryCellGeneralInformation; }
    public Pointer getPointer() { return pointer; }
    public Map<String, Item> getPossibleItems() {return possibleItems;}

    public void setPointer(Pointer pointer) {
        pointer.getGameSpriteRenderInformation().setScreenCoordinateX(inventoryInformation.firstCellCoordinateX());
        pointer.getGameSpriteRenderInformation().setScreenCoordinateY(inventoryInformation.firstCellCoordinateY());
        pointer.getGameSpriteRenderInformation().setTargetWidth(inventoryCellGeneralInformation.cellWidth());
        pointer.getGameSpriteRenderInformation().setTargetHeight(inventoryCellGeneralInformation.cellHeight());
        this.pointer = pointer;
    }

    public void setItems(List<Item> items) {
        if (items == null)
            return;

        for (Item item : items) {
            addItemToInventory(item);
        }
    }

    public void setPossibleItems(Map<String, Item> possibleItems) {this.possibleItems = possibleItems; }
    public void setCharacter(PlayableCharacter character) { this.character = character; }
}
