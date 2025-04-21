package cvut.cz.characters;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

import java.util.Map;

public class Merchant extends GameCharacter{
    private Map<String, Integer> itemsPrices;

    public Merchant(Map<String, Integer> itemsPrices, CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation){
        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.itemsPrices = itemsPrices;
    }

    private void sellItem(){

    }

    private void sortItems(){
    }

    @Override
    protected void Die() {

    }


    @Override
    protected void move(Directions direction) {

    }

    @Override
    public void takeDamage(int damage) {

    }

    @Override
    public void update() {

    }
}
