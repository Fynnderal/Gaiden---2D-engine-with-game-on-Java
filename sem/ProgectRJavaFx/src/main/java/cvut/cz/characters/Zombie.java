package cvut.cz.characters;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

public class Zombie extends Enemy{

    public Zombie(CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation, PlayableCharacter mainPlayer, EnemyInformation enemyInformation) {
        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation, mainPlayer, enemyInformation);
    }


    @Override
    public void update() {
        super.update();
    }

    @Override
    protected void Die() {

    }

}
