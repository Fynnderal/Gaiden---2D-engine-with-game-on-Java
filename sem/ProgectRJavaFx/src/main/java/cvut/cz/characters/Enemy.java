package cvut.cz.characters;

import cvut.cz.GameSpriteRenderInformation;
import cvut.cz.GameSpriteSourceInformation;

import java.net.URL;

public abstract class Enemy extends GameCharacter{

    protected Directions currentDirection;

    public Enemy(CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation);
    }

    protected abstract void spotPlayer();
    protected abstract void chooseDirection();
}
