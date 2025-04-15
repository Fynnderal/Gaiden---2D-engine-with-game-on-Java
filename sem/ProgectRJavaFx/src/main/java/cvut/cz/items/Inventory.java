package cvut.cz.items;

import cvut.cz.GameSprite;
import cvut.cz.GameSpriteRenderInformation;
import cvut.cz.GameSpriteSourceInformation;
import cvut.cz.characters.Directions;
import cvut.cz.characters.PlayableCharacter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Inventory extends GameSprite {
    Logger logger = Logger.getLogger(Inventory.class.getName());

    private final InventoryInformation inventoryInformation;
    private final List<Item> items;
    private final List<InventoryCell> cells;
    private int currentCell;

    public Inventory(InventoryInformation inventoryInformation, PlayableCharacter character, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.gameSpriteRenderInformation.setWorldCoordinateY(0);
        this.gameSpriteRenderInformation.setWorldCoordinateX(0);
        this.inventoryInformation = inventoryInformation;

        if (character.getItems() != null)
            items = Arrays.asList(character.getItems());
        else {
            items = null;
            logger.warning("character items is null");
        }

        this.currentCell = 0;
        cells = new ArrayList<>();

        setCells();
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
                    inventoryCell.setItemAmount(inventoryCell.getItemAmount() + 1);
                    firstCell.useItem();
                    secondCell.useItem();
                    return true;
                }
            }
            firstCell.setItem(adjustItem(new Item(inventoryInformation.possibleItems().get(resultItemName)), firstCell.getCoordinateX(), firstCell.getCoordinateY()));
            secondCell.useItem();
            return true;
        }
        return false;
    }

    public boolean discardCell(InventoryCell inventoryCell){
        System.out.println("discarded: " + inventoryCell.getItem().getItemInformation().name());
        return false;
    }

    public boolean equipCell(InventoryCell inventoryCell){
        System.out.println("equipped: " + inventoryCell.getItem().getItemInformation().name());

        return false;
    }

    public boolean useCell(InventoryCell inventoryCell){
        System.out.println("used: " + inventoryCell.getItem().getItemInformation().name());
        return false;
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
}
