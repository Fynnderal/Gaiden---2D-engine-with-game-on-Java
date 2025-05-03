package cvut.cz.items;

public record InventoryCellGeneralInformation(int cellWidth, int cellHeight, int itemCoordinateXRelativeToCell,
                                              int itemCoordinateYRelativeToCell, int itemWidthInCell,
                                              int itemHeightInCell, int amountLabelCoordinateXRelativeToCell,
                                              int amountLabelCoordinateYRelativeToCell) { }
