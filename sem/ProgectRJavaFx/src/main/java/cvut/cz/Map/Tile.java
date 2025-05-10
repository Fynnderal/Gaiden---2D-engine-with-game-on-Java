package cvut.cz.Map;


import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

import java.net.URL;

/**
 * Represents a single tile in the game map.
 */
public class Tile extends GameSprite implements Cloneable {
    private static int sourceTileSize = 16;

    /**
     * Constructs a Tile object with the specified image path and source coordinates.
     *
     * @param pathToImage The URL of the image containing the tile.
     * @param sourceX The X-coordinate of the tile in the source image.
     * @param sourceY The Y-coordinate of the tile in the source image.
     */
    public Tile(URL pathToImage, int sourceX, int sourceY) {
        super(new GameSpriteSourceInformation(pathToImage, sourceX, sourceY, sourceTileSize, sourceTileSize), new GameSpriteRenderInformation(0, 0, sourceTileSize, sourceTileSize, 0,0 ));
    }

    /**
     * Creates and returns a copy of this Tile object.
     *
     * @return A cloned Tile object, or null if cloning is not supported.
     */
    @Override
    public Tile clone() {
        try{
            Tile cloned = (Tile) super.clone();
            cloned.setGameSpriteSourceInformation(gameSpriteSourceInformation.clone());
            cloned.setGameSpriteRenderInformation(gameSpriteRenderInformation.clone());
            return cloned;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * Gets the size of the tile in the source image.
     *
     * @return The size of the tile in the source image.
     */
    public static int getSourceTileSize() {  return Tile.sourceTileSize; }

    /**
     * Sets the size of the tile in the source image.
     *
     * @param sourceTileSize The new size of the tile in the source image.
     */
    public static void setSourceTileSize(int sourceTileSize) {  Tile.sourceTileSize = sourceTileSize; }
}
