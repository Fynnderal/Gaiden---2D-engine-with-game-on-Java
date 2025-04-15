package cvut.cz;

import java.net.URL;

public abstract class GameSprite {
    protected GameSpriteSourceInformation gameSpriteSourceInformation;
    protected GameSpriteRenderInformation gameSpriteRenderInformation;

    public GameSprite(GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation)
    {
        this.gameSpriteSourceInformation = gameSpriteSourceInformation;
        this.gameSpriteRenderInformation = gameSpriteRenderInformation;
    }

    public GameSpriteSourceInformation getGameSpriteSourceInformation() { return gameSpriteSourceInformation; }
    public GameSpriteRenderInformation getGameSpriteRenderInformation() { return gameSpriteRenderInformation; }

}

