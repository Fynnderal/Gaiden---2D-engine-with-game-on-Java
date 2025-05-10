package cvut.cz.characters;

/**
 * Represents information about the action area of a character within which they can act.
 *
 * @param actionAreaX      The X coordinate (in pixels) of the action area relative to the map.
 * @param actionAreaY      The Y coordinate (in pixels) of the action area relative to the map.
 * @param actionAreaWidth  The width of the action area (in pixels).
 * @param actionAreaHeight The height of the action area (in pixels).
 * @param spottingRadius   The radius (in pixels) within which an NPC can detect the main character.
 */
public record ActionAreaInformation(int actionAreaX,
                                    int actionAreaY,
                                    int actionAreaWidth,
                                    int actionAreaHeight,
                                    int spottingRadius){}
