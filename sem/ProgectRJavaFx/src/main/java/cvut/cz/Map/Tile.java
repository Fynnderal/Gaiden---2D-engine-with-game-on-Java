package cvut.cz.Map;

import cvut.cz.GameSprite;

import java.net.URL;

public class Tile extends GameSprite {
    private static int sourceTileSize;
    public Tile(URL pathToImage, int sourceX, int sourceY, int sourceTileSize) {
        super(pathToImage, sourceX, sourceY, sourceTileSize, sourceTileSize, 0, 0, sourceTileSize, sourceTileSize, 0, 0);
        Tile.sourceTileSize = sourceTileSize;
    }

    public void setWorldCoordinateX(int worldCoordinateX) { this.worldCoordinateX = worldCoordinateX; }
    public void setWorldCoordinateY(int worldCoordinateY) { this.worldCoordinateY = worldCoordinateY; }
    public static int getSourceTileSize() {  return Tile.sourceTileSize; }
}
