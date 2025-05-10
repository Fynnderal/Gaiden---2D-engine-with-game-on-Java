package cvut.cz.Map;


import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

import java.util.List;

/**
 * Represents a section of the game map.
 * A map section consists of a collection of tiles
 */
public class MapSection extends GameSprite {

    // The tiles that make up this map section
    private final List<Tile> tiles;

    /**
     * Constructs a MapSection object with the specified source information, render information, and tiles.
     *
     * @param gameSpriteSourceInformation The source information for the map section's sprite.
     * @param gameSpriteRenderInformation The render information for the map section's sprite.
     * @param tiles The list of tiles that make up this map section.
     */
    public MapSection(GameSpriteSourceInformation gameSpriteSourceInformation,
                      GameSpriteRenderInformation gameSpriteRenderInformation,
                      List<Tile> tiles){

        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.tiles = tiles;
    }

    /**
     * Gets the list of tiles that make up this map section.
     *
     * @return The list of tiles in this map section.
     */
    public List<Tile> getTiles() { return tiles; }
}
