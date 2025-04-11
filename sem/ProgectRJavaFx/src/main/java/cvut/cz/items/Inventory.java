package cvut.cz.items;

import cvut.cz.GameSprite;
import cvut.cz.characters.Directions;
import cvut.cz.characters.GameCharacter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.URL;
import java.util.logging.Logger;

public class Inventory extends GameSprite {
    Logger logger = Logger.getLogger(Inventory.class.getName());

    private List<Item> items;
    private Pointer pointer;
    private InventoryCellInformation cellInformation;
    private List<InventoryCell> cells;
    private int currentCell;

    public Inventory(InventoryCellInformation cellInformation, Pointer pointer, GameCharacter character, URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int targetCoordinateX, int targetCoordinateY, int targetWidth, int targetHeight) {
        super(pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, targetCoordinateX, targetCoordinateY, targetWidth, targetHeight, 0, 0);
        if (character.getItems() != null)
            items = Arrays.asList(character.getItems());
        else {
            items = null;
            logger.warning("character items is null");
        }
        this.currentCell = 0;
        this.pointer = pointer;
        this.cellInformation = cellInformation;
        cells = new ArrayList<>();
        pointer.setScreenCoordinateX(cellInformation.getFirstCellCoordinateX());
        pointer.setScreenCoordinateY(cellInformation.getFirstCellCoordinateY());
        pointer.setTargetWidth(cellInformation.getCellWidth());
        pointer.setTargetHeight(cellInformation.getCellHeight());
        setCells();
    }

    private void setCells() {
        int collumnCounter = 0;
        int rowCounter = 0;
        int currentX, currentY;
        Item currentItem = null;
        for (int i = 0; i < cellInformation.getNumberOfCells(); i++) {
            if (collumnCounter == cellInformation.getNumberOfCellsInRow()) {
                rowCounter++;
                collumnCounter = 0;
            }

            currentX = cellInformation.getFirstCellCoordinateX() + collumnCounter * (cellInformation.getCellWidth() + cellInformation.getGapBetweenCellsX());
            currentY = cellInformation.getFirstCellCoordinateY() + rowCounter * (cellInformation.getCellHeight() + cellInformation.getGapBetweenCellsY());

            if (items == null || i >= items.size())
                currentItem = null;
            else {
                currentItem = items.get(i);
                currentItem.setScreenCoordinateX(currentX + cellInformation.getItemCoordinateXRelativeToCell());
                currentItem.setScreenCoordinateY(currentY + cellInformation.getItemCoordinateYRelativeToCell());
                currentItem.setTargetWidth(cellInformation.getItemWidth());
                currentItem.setTargetHeight(cellInformation.getItemHeight());
            }


            cells.add(new InventoryCell(currentX, currentY, currentItem));
            collumnCounter++;
        }
    }

    public void movePointer(Directions direction) {
        if (cells.size() == 0)
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
                currentCell = (currentCell + cellInformation.getNumberOfCellsInRow()) % cells.size();
                break;
            case UP:
                currentCell = (currentCell - cellInformation.getNumberOfCellsInRow()) % cells.size();
                if (currentCell < 0)
                    currentCell = cells.size() +currentCell;
                break;

        }
        pointer.setScreenCoordinateX(cells.get(currentCell).getCoordinateX());
        pointer.setScreenCoordinateY(cells.get(currentCell).getCoordinateY());
    }

    public InventoryCell getSelectedInventoryCell(){
        return cells.get(currentCell);
    }

    public Pointer getPointer() { return pointer; }
    public List<InventoryCell> getCells() { return cells; }

}
