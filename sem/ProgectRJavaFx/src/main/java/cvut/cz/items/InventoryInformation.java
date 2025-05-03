package cvut.cz.items;


public record InventoryInformation (int gapBetweenCellsX, int gapBetweenCellsY, int numberOfCells,
                                    int numberOfCellsInRow, int firstCellCoordinateX, int firstCellCoordinateY) { }
