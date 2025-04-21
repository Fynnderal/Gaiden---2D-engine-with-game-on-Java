package cvut.cz.characters;


import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.Updatable;


public abstract class GameCharacter extends GameSprite implements Updatable {

    CharacterInformation characterInformation;

    public GameCharacter(CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.characterInformation = characterInformation.clone();
    }
    public CharacterInformation getCharacterInformation() { return characterInformation; }

    protected int calculateLengthOfVector(int x1, int y1, int x2, int y2) {
        return (int) Math.ceil(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
    }

    protected abstract void move(Directions direction);
    protected abstract void Die();
    public abstract void takeDamage(int damage);
}
