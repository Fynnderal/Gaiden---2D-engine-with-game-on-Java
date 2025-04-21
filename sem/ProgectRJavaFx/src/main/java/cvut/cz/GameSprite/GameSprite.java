package cvut.cz.GameSprite;

public abstract class GameSprite {
    protected GameSpriteSourceInformation gameSpriteSourceInformation;
    protected GameSpriteRenderInformation gameSpriteRenderInformation;

    public GameSprite(GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation)
    {
        this.gameSpriteSourceInformation = gameSpriteSourceInformation.clone();
        this.gameSpriteRenderInformation = gameSpriteRenderInformation.clone();
    }

    public GameSpriteSourceInformation getGameSpriteSourceInformation() { return gameSpriteSourceInformation; }
    public GameSpriteRenderInformation getGameSpriteRenderInformation() { return gameSpriteRenderInformation; }

    public void setGameSpriteSourceInformation(GameSpriteSourceInformation gameSpriteSourceInformation) { this.gameSpriteSourceInformation = gameSpriteSourceInformation; }
    public void setGameSpriteRenderInformation(GameSpriteRenderInformation gameSpriteRenderInformation) { this.gameSpriteRenderInformation = gameSpriteRenderInformation; }
}

