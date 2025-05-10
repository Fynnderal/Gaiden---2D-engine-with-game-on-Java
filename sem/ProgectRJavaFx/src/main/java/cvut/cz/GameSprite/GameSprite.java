package cvut.cz.GameSprite;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a sprite in the game.
 * This abstract class contains all the necessary information for rendering and managing a game sprite.
 */
public abstract class GameSprite {
    protected GameSpriteSourceInformation gameSpriteSourceInformation;
    protected GameSpriteRenderInformation gameSpriteRenderInformation;

    /**
     * Constructs a GameSprite object with the specified source and render information.
     *
     * @param gameSpriteSourceInformation Information about the source of the sprite.
     * @param gameSpriteRenderInformation Information about how the sprite is rendered on the screen.
     */
    @JsonCreator
    public GameSprite(@JsonProperty("gameSpriteSourceInformation") GameSpriteSourceInformation gameSpriteSourceInformation, @JsonProperty("gameSpriteRenderInformation") GameSpriteRenderInformation gameSpriteRenderInformation)
    {
        if (gameSpriteSourceInformation != null)
            this.gameSpriteSourceInformation = gameSpriteSourceInformation.clone();
        if (gameSpriteRenderInformation != null)
            this.gameSpriteRenderInformation = gameSpriteRenderInformation.clone();
    }
    /**
     * Gets the source information of the sprite.
     *
     * @return The source information of the sprite.
     */
    public GameSpriteSourceInformation getGameSpriteSourceInformation() { return gameSpriteSourceInformation; }

    /**
     * Gets the render information of the sprite.
     *
     * @return The render information of the sprite.
     */
    public GameSpriteRenderInformation getGameSpriteRenderInformation() { return gameSpriteRenderInformation; }

    /**
     * Sets the source information of the sprite.
     *
     * @param gameSpriteSourceInformation The new source information of the sprite.
     */
    public void setGameSpriteSourceInformation(GameSpriteSourceInformation gameSpriteSourceInformation) { this.gameSpriteSourceInformation = gameSpriteSourceInformation; }

    /**
     * Sets the render information of the sprite.
     *
     * @param gameSpriteRenderInformation The new render information of the sprite.
     */
    public void setGameSpriteRenderInformation(GameSpriteRenderInformation gameSpriteRenderInformation) { this.gameSpriteRenderInformation = gameSpriteRenderInformation; }
}

