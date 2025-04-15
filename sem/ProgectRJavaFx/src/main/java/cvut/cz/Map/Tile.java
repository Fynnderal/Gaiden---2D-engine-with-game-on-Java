package cvut.cz.Map;

import cvut.cz.GameSprite;
import cvut.cz.GameSpriteRenderInformation;
import cvut.cz.GameSpriteSourceInformation;

import java.net.URL;

public class Tile extends GameSprite {
    private static int sourceTileSize;

    public Tile(URL pathToImage, int sourceX, int sourceY, int sourceTileSize) {
        super(new GameSpriteSourceInformation(pathToImage, sourceX, sourceY, sourceTileSize, sourceTileSize), new GameSpriteRenderInformation(0, 0, sourceTileSize, sourceTileSize, 0,0 ));
        Tile.sourceTileSize = sourceTileSize;
    }

    public static int getSourceTileSize() {  return Tile.sourceTileSize; }
}
