package cvut.cz.items;

import java.util.Map;

public record InventoryInformation (InventoryCellGeneralInformation inventoryCellGeneralInformation, Pointer pointer, Map<String, Item> possibleItems, int gapBetweenCellsX, int gapBetweenCellsY, int numberOfCells, int numberOfCellsInRow, int firstCellCoordinateX, int firstCellCoordinateY) {

    public InventoryInformation{
        pointer.getGameSpriteRenderInformation().setScreenCoordinateX(firstCellCoordinateX);
        pointer.getGameSpriteRenderInformation().setScreenCoordinateY(firstCellCoordinateY);
        pointer.getGameSpriteRenderInformation().setTargetWidth(inventoryCellGeneralInformation.cellWidth());
        pointer.getGameSpriteRenderInformation().setTargetHeight(inventoryCellGeneralInformation.cellHeight());
    }


}
