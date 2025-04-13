package cvut.cz.items;

public class InventoryCellInformation {
    private static InventoryCellInformation instance;

    private static int cellWidth;
    private static int cellHeight;
    private static int gapBetweenCellsX;
    private static int gapBetweenCellsY;
    private static int numberOfCells;
    private static int numberOfCellsInRow;
    private static int firstCellCoordinateX;
    private static int firstCellCoordinateY;
    private static int itemWidth;
    private static int itemHeight;
    private static int itemCoordinateXRelativeToCell;
    private static int itemCoordinateYRelativeToCell;
    private static int amountLabelCoordinateXRelativeToCell;
    private static int amountLabelCoordinateYRelativeToCell;


    public InventoryCellInformation(int cellWidth, int cellHeight, int gapBetweenCellsX, int gapBetweenCellsY, int numberOfCells, int numberOfCellsInRow, int firstCellCoordinateX, int firstCellCoordinateY, int itemCoordinateXRelativeToCell, int itemCoordinateYRelativeToCell, int itemWidth, int itemHeight, int amountLabelCoordinateXRelativeToCell, int amountLabelCoordinateYRelativeToCell) {
        if (InventoryCellInformation.instance != null)
            return;

        InventoryCellInformation.instance = this;
        InventoryCellInformation.cellWidth = cellWidth;
        InventoryCellInformation.cellHeight = cellHeight;
        InventoryCellInformation.gapBetweenCellsX = gapBetweenCellsX;
        InventoryCellInformation.gapBetweenCellsY = gapBetweenCellsY;
        InventoryCellInformation.numberOfCells = numberOfCells;
        InventoryCellInformation.numberOfCellsInRow = numberOfCellsInRow;
        InventoryCellInformation.firstCellCoordinateX = firstCellCoordinateX;
        InventoryCellInformation.firstCellCoordinateY = firstCellCoordinateY;
        InventoryCellInformation.itemCoordinateXRelativeToCell = itemCoordinateXRelativeToCell;
        InventoryCellInformation.itemCoordinateYRelativeToCell = itemCoordinateYRelativeToCell;
        InventoryCellInformation.itemWidth = itemWidth;
        InventoryCellInformation.itemHeight = itemHeight;
        InventoryCellInformation.amountLabelCoordinateXRelativeToCell = amountLabelCoordinateXRelativeToCell;
        InventoryCellInformation.amountLabelCoordinateYRelativeToCell = amountLabelCoordinateYRelativeToCell;
    }

    public int getCellWidth() { return cellWidth; }
    public int getCellHeight() { return cellHeight; }
    public int getGapBetweenCellsX() { return gapBetweenCellsX; }
    public int getGapBetweenCellsY() { return gapBetweenCellsY; }
    public int getNumberOfCells() { return numberOfCells; }
    public int getNumberOfCellsInRow() { return numberOfCellsInRow; }
    public int getFirstCellCoordinateX() { return firstCellCoordinateX; }
    public int getFirstCellCoordinateY() { return firstCellCoordinateY; }
    public int getItemCoordinateXRelativeToCell() { return itemCoordinateXRelativeToCell; }
    public int getItemCoordinateYRelativeToCell() { return itemCoordinateYRelativeToCell; }
    public int getItemWidth() { return itemWidth; }
    public int getItemHeight() { return itemHeight; }
    public int getAmountLabelCoordinateX() { return amountLabelCoordinateXRelativeToCell; }
    public int getAmountLabelCoordinateY() { return amountLabelCoordinateYRelativeToCell; }


}
