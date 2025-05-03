package cvut.cz.Map;


import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

import java.net.URL;

public class Tile extends GameSprite implements Cloneable {
    private static int sourceTileSize = 16;

    public Tile(URL pathToImage, int sourceX, int sourceY) {
        super(new GameSpriteSourceInformation(pathToImage, sourceX, sourceY, sourceTileSize, sourceTileSize), new GameSpriteRenderInformation(0, 0, sourceTileSize, sourceTileSize, 0,0 ));
    }

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

    public static int getSourceTileSize() {  return Tile.sourceTileSize; }

    public static void setSourceTileSize(int sourceTileSize) {  Tile.sourceTileSize = sourceTileSize; }
}
