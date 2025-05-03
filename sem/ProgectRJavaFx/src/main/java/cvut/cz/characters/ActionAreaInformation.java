package cvut.cz.characters;

/**
 * Saves information about action area of character within which he can act
 *
 * @param actionAreaX - X coordinate (in pixels) of action area relative to map
 * @param actionAreaY - Y coordinate (in pixels) of action area relative to map
 * @param actionAreaWidth - width of action area
 * @param actionAreaHeight - height of action area
 * @param spottingRadius - radius within which NPC can detect the main character
 */
public record ActionAreaInformation(int actionAreaX, int actionAreaY, int actionAreaWidth, int actionAreaHeight, int spottingRadius){}
