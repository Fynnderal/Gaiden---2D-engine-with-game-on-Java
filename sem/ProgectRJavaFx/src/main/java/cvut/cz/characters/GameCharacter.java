package cvut.cz.characters;


import cvut.cz.GameSprite;
import cvut.cz.GameSpriteRenderInformation;
import cvut.cz.GameSpriteSourceInformation;
import cvut.cz.Updatable;


public abstract class GameCharacter extends GameSprite implements Updatable {

    CharacterInformation characterInformation;

    public GameCharacter(CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.characterInformation = characterInformation;
    }

    protected abstract void Attack();
    protected abstract void Move(Directions direction);
    protected abstract void Die();
    protected abstract void takeDamage(int damage);

    public CharacterInformation getCharacterInformation() { return characterInformation; }

}
