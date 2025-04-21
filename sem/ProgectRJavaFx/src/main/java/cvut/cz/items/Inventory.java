package cvut.cz.items;

import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.characters.Directions;
import cvut.cz.characters.PlayableCharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Inventory extends GameSprite {
    Logger logger = Logger.getLogger(Inventory.class.getName());

    private final PlayableCharacter character;
    private final InventoryInformation inventoryInformation;
    private final List<Item> items;
    private final List<InventoryCell> cells;
    private int currentCell;
    private InventoryCell equippedCell;

    public Inventory(InventoryInformation inventoryInformation, PlayableCharacter character, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.gameSpriteRenderInformation.setWorldCoordinateY(0);
        this.gameSpriteRenderInformation.setWorldCoordinateX(0);
        this.inventoryInformation = inventoryInformation;
        this.character = character;
        this.items = character.getItems();
        this.currentCell = 0;
        this.cells = new ArrayList<>();

        setCells();
    }

    public boolean addItemToInventory(Item item) {
        if (item == null)
            return false;

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
                items.add(item);
                return true;
            }
        }
        if (emptyCell != null) {
            items.add(item);
            adjustItem(item, emptyCell.getCoordinateX(), emptyCell.getCoordinateY());
            emptyCell.setItem(item);
            return true;
        }

        return false;
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

            currentX = inventoryInformation.firstCellCoordinateX() + columnCounter * (inventoryInformation.inventoryCellGeneralInformation().cellWidth() + inventoryInformation.gapBetweenCellsX());
            currentY = inventoryInformation.firstCellCoordinateY() + rowCounter * (inventoryInformation.inventoryCellGeneralInformation().cellHeight() + inventoryInformation.gapBetweenCellsY());

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
        inventoryInformation.pointer().getGameSpriteRenderInformation().setScreenCoordinateX(cells.get(currentCell).getCoordinateX());
        inventoryInformation.pointer().getGameSpriteRenderInformation().setScreenCoordinateY(cells.get(currentCell).getCoordinateY());
    }

    public boolean combineCells(InventoryCell firstCell, InventoryCell secondCell){
        if (firstCell == null || secondCell == null)
            return false;

        if (firstCell.getItem() == null || secondCell.getItem() == null)
            return false;

        Map<String, String> canBeCombinedWithInto1 = firstCell.getItem().getItemInformation().canBeCombinedWithInto();
        Map<String, String> canBeCombinedWithInto2 = secondCell.getItem().getItemInformation().canBeCombinedWithInto();

        if (canBeCombinedWithInto1 == null || canBeCombinedWithInto2 == null)
            return false;

        if (canBeCombinedWithInto1.containsKey(secondCell.getItem().getItemInformation().name())){
            String resultItemName = canBeCombinedWithInto1.get(secondCell.getItem().getItemInformation().name());

            for (InventoryCell inventoryCell: cells) {
                if (inventoryCell.getItem() == null)
                    continue;

                if (inventoryCell.getItem().getItemInformation().name().equals(resultItemName)) {
                    inventoryCell.setItemAmount(inventoryCell.getItem().getAmount() + 1);
                    discardCell(firstCell);
                    discardCell(secondCell);
                    return true;
                }
            }
            firstCell.setItem(adjustItem(inventoryInformation.possibleItems().get(resultItemName).clone(), firstCell.getCoordinateX(), firstCell.getCoordinateY()));
            discardCell(secondCell);
            return true;
        }
        return false;
    }

    public boolean discardCell(InventoryCell inventoryCell){
        if (inventoryCell == null)
            return false;

        if (inventoryCell.getItem() == null)
            return false;

        if (!inventoryCell.getItem().getItemInformation().canBeDiscarded())
            return false;

        inventoryCell.setItemAmount(inventoryCell.getItem().getAmount() - 1);
        return true;
    }

    public boolean equipCell(InventoryCell inventoryCell){
        if (inventoryCell == null)
            return false;

        if (inventoryCell.getItem() == null)
            return false;

        if (!inventoryCell.getItem().getItemInformation().canBeEquipped())
            return false;

        if (equippedCell == inventoryCell) {
            equippedCell = null;
            inventoryCell.setIsItemEquipped(false);
        }else{
            if (equippedCell != null)
                equippedCell.setIsItemEquipped(false);
            inventoryCell.setIsItemEquipped(true);
            equippedCell = inventoryCell;
        }
        return true;
    }

    public boolean useCell(InventoryCell inventoryCell) {
        if (inventoryCell == null)
            return false;

        if (inventoryCell.getItem() == null)
            return false;

        if (!inventoryCell.getItem().getItemInformation().canBeUsed())
            return false;

        character.useItem(inventoryCell.getItem());

        inventoryCell.setItemAmount(inventoryCell.getItem().getAmount() - 1);
        return true;
    }

    private Item adjustItem(Item item, int inventoryCellX, int inventoryCellY) {
        item.getGameSpriteRenderInformation().setScreenCoordinateX(inventoryCellX + inventoryInformation.inventoryCellGeneralInformation().itemCoordinateXRelativeToCell());
        item.getGameSpriteRenderInformation().setScreenCoordinateY(inventoryCellY + inventoryInformation.inventoryCellGeneralInformation().itemCoordinateYRelativeToCell());
        item.getGameSpriteRenderInformation().setTargetWidth(inventoryInformation.inventoryCellGeneralInformation().itemWidthInCell());
        item.getGameSpriteRenderInformation().setTargetHeight(inventoryInformation.inventoryCellGeneralInformation().itemHeightInCell());
        return item;
    }

    public InventoryCell getSelectedInventoryCell(){ return cells.get(currentCell); }
    public List<InventoryCell> getCells() { return cells; }
    public InventoryInformation getInventoryInformation() { return inventoryInformation; }
    public InventoryCell getEquippedCell() { return equippedCell; }
}
