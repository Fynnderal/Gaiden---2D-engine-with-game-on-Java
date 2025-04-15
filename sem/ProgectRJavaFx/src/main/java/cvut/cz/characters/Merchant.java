package cvut.cz.characters;

import cvut.cz.GameSpriteRenderInformation;
import cvut.cz.GameSpriteSourceInformation;

import java.util.Map;
import java.net.URL;

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
    protected void Attack() {

    }

    @Override
    protected void Move(Directions direction) {

    }

    @Override
    protected void takeDamage(int damage) {

    }

    @Override
    public void update() {

    }
}
