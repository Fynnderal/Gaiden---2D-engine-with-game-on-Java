package cvut.cz.characters;

/**
 * Saves information about action area of character within which he can act
 *
 * @param actionAreaX
 * @param actionAreaY
 * @param actionAreaWidth
 * @param actionAreaHeight
 * @param spottingRadius
 */
public record ActionAreaInformation(int actionAreaX, int actionAreaY, int actionAreaWidth, int actionAreaHeight, int spottingRadius){}
