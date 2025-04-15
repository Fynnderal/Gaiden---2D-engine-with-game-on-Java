package cvut.cz.characters;

import cvut.cz.GameSpriteRenderInformation;
import cvut.cz.GameSpriteSourceInformation;

import java.net.URL;

public class OfficeWorker extends PlayableCharacter{

    public OfficeWorker(URL pathToItems, CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        super(pathToItems, characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation);
    }

    @Override
    protected void Attack() {
        System.out.println("Not implemented yet");
    }

    @Override
    protected void takeDamage(int damage) {
        System.out.println("Not implemented yet");
    }

    @Override
    protected void Die() {
        System.out.println("Not implemented yet");
    }

    @Override
    public void update(){
        return;
    }

}
