package cvut.cz.items;

/**
 * Represents information about the inventory layout and properties.
 *
 * @param gapBetweenCellsX The horizontal gap (in pixels) between inventory cells.
 * @param gapBetweenCellsY The vertical gap (in pixels) between inventory cells.
 * @param numberOfCells The total number of cells in the inventory.
 * @param numberOfCellsInRow The number of cells in a single row of the inventory.
 * @param firstCellCoordinateX The X-coordinate (in pixels) of the first cell in the inventory.
 * @param firstCellCoordinateY The Y-coordinate (in pixels) of the first cell in the inventory.
 */
public record InventoryInformation (int gapBetweenCellsX,
                                    int gapBetweenCellsY,
                                    int numberOfCells,
                                    int numberOfCellsInRow,
                                    int firstCellCoordinateX,
                                    int firstCellCoordinateY) { }
