package cvut.cz.Map;


import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

import java.util.List;

public class MapSection extends GameSprite {

    private final List<Tile> tiles;

    public MapSection(GameSpriteSourceInformation gameSpriteSourceInformation,
                      GameSpriteRenderInformation gameSpriteRenderInformation,
                      List<Tile> tiles){

        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.tiles = tiles;
    }

    public List<Tile> getTiles() { return tiles; }
}
