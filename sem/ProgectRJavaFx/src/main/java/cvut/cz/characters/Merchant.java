package cvut.cz.characters;

import java.util.Map;
import java.net.URL;

public class Merchant extends GameCharacter{
    private Map<String, Integer> itemsPrices;

    public Merchant(Map<String, Integer> itemsPrices, URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int screenCoordinateX, int screenCoordinateY, int targetWidth, int targetHeight, int worldCoordinateX, int worldCoordinateY) {
        super(0, States.IDLE, 0, 0, 0, pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, screenCoordinateX, screenCoordinateY, targetWidth, targetHeight, worldCoordinateX, worldCoordinateY);
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
