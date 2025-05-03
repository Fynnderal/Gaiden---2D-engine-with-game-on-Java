package cvut.cz.items;


import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

public class Pointer extends GameSprite {
    public Pointer(GameSpriteSourceInformation gameSpriteSourceInformation){
        super(gameSpriteSourceInformation, new GameSpriteRenderInformation(0, 0, 0, 0, 0, 0));
    }
}
