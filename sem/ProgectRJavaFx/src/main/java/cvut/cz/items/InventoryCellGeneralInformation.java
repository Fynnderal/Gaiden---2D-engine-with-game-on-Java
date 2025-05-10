package cvut.cz.items;

/**
 * Keeps information about cells of the inventory. This information is common for all cells.
 *
 * @param cellWidth The width of the cells.
 * @param cellHeight The height of the cells.
 * @param itemCoordinateXRelativeToCell The X-coordinate (in pixels) of an item sprite that is saved in the cell.
 *                                      This coordinate is relative to this cell.
 * @param itemCoordinateYRelativeToCell The Y-coordinate (in pixels) of an item sprite that is saved in the cell.
 *                                      This coordinate is relative to this cell.
 * @param itemWidthInCell The width of an item sprite that is saved in the cell.
 * @param itemHeightInCell The height of an item sprite that is saved in the cell.
 * @param amountLabelCoordinateXRelativeToCell The X-coordinate (in pixels) of a text label that represents the amount of the item saved in the cell.
 *                                             This coordinate is relative to this cell.
 * @param amountLabelCoordinateYRelativeToCell The Y-coordinate (in pixels) of a text label that represents the amount of the item saved in the cell.
 *                                             This coordinate is relative to this cell.
 */
public record InventoryCellGeneralInformation(int cellWidth,
                                              int cellHeight,
                                              int itemCoordinateXRelativeToCell,
                                              int itemCoordinateYRelativeToCell, int itemWidthInCell,
                                              int itemHeightInCell,
                                              int amountLabelCoordinateXRelativeToCell,
                                              int amountLabelCoordinateYRelativeToCell) { }
