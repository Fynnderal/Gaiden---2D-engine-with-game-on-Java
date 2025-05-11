package cvut.cz.Map;

/**
 * Represents general information about the map.
 * This record holds the coordinates and scaling factors for the map in the game world.
 *
 * @param mapCoordinateX The X-coordinate of the map in the game world.
 * @param mapCoordinateY The Y-coordinate of the map in the game world.
 * @param mapTargetScaleFactorX The scale factor for the map's width in the game world.
 * @param mapTargetScaleFactorY The scale factor for the map's height in the game world.
 */
public record MapInformation (int mapCoordinateX,
                              int mapCoordinateY,
                              int mapTargetScaleFactorX,
                              int mapTargetScaleFactorY) {}
