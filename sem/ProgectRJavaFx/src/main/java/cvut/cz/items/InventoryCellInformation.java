package cvut.cz.items;

public class InventoryCellInformation {

    private int cellWidth;
    private int cellHeight;
    private int gapBetweenCellsX;
    private int gapBetweenCellsY;
    private int numberOfCells;
    private int numberofCellsInRow;
    private int firstCellCoordinateX;
    private int firstCellCoordinateY;
    private int itemWidth;
    private int itemHeight;
    private int itemCoordinateXRelativeToCell;
    private int itemCoordinateYRelativeToCell;


    public InventoryCellInformation(int cellWidth, int cellHeight, int gapBetweenCellsX, int gapBetweenCellsY, int numberOfCells, int numberOfCellsInRow, int firstCellCoordinateX, int firstCellCoordinateY, int itemCoordinateXRelativeToCell, int itemCoordinateYRelativeToCell, int itemWidth, int itemHeight) {
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.gapBetweenCellsX = gapBetweenCellsX;
        this.gapBetweenCellsY = gapBetweenCellsY;
        this.numberOfCells = numberOfCells;
        this.numberofCellsInRow = numberOfCellsInRow;
        this.firstCellCoordinateX = firstCellCoordinateX;
        this.firstCellCoordinateY = firstCellCoordinateY;
        this.itemCoordinateXRelativeToCell = itemCoordinateXRelativeToCell;
        this.itemCoordinateYRelativeToCell = itemCoordinateYRelativeToCell;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
    }

    public int getCellWidth() { return cellWidth; }
    public int getCellHeight() { return cellHeight; }
    public int getGapBetweenCellsX() { return gapBetweenCellsX; }
    public int getGapBetweenCellsY() { return gapBetweenCellsY; }
    public int getNumberOfCells() { return numberOfCells; }
    public int getNumberOfCellsInRow() { return numberofCellsInRow; }
    public int getFirstCellCoordinateX() { return firstCellCoordinateX; }
    public int getFirstCellCoordinateY() { return firstCellCoordinateY; }
    public int getItemCoordinateXRelativeToCell() { return itemCoordinateXRelativeToCell; }
    public int getItemCoordinateYRelativeToCell() { return itemCoordinateYRelativeToCell; }
    public int getItemWidth() { return itemWidth; }
    public int getItemHeight() { return itemHeight; }


}
