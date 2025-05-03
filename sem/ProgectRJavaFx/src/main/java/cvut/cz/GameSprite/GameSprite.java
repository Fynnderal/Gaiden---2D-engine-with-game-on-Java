package cvut.cz.GameSprite;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class GameSprite {
    protected GameSpriteSourceInformation gameSpriteSourceInformation;
    protected GameSpriteRenderInformation gameSpriteRenderInformation;

    @JsonCreator
    public GameSprite(@JsonProperty("gameSpriteSourceInformation") GameSpriteSourceInformation gameSpriteSourceInformation, @JsonProperty("gameSpriteRenderInformation") GameSpriteRenderInformation gameSpriteRenderInformation)
    {
        this.gameSpriteSourceInformation = gameSpriteSourceInformation.clone();
        this.gameSpriteRenderInformation = gameSpriteRenderInformation.clone();
    }

    public GameSpriteSourceInformation getGameSpriteSourceInformation() { return gameSpriteSourceInformation; }
    public GameSpriteRenderInformation getGameSpriteRenderInformation() { return gameSpriteRenderInformation; }

    public void setGameSpriteSourceInformation(GameSpriteSourceInformation gameSpriteSourceInformation) { this.gameSpriteSourceInformation = gameSpriteSourceInformation; }
    public void setGameSpriteRenderInformation(GameSpriteRenderInformation gameSpriteRenderInformation) { this.gameSpriteRenderInformation = gameSpriteRenderInformation; }
}

