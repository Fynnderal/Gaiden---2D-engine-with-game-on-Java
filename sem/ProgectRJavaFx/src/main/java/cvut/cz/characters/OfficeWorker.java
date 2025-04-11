package cvut.cz.characters;

import java.net.URL;

public class OfficeWorker extends PlayableCharacter{
    public OfficeWorker(URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int targetCoordinateX, int targetCoordinateY, int targetWidth, int targetHeight, int worldCoordinateX, int worldCoordinateY){
        this(0, States.IDLE, 0, 0, 0, pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, targetCoordinateX, targetCoordinateY, targetWidth, targetHeight, worldCoordinateX, worldCoordinateY);
    }

    public OfficeWorker(int attackPower, States currentState, int currentHealth, int maxHealth, double speed, URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int targetCoordinateX, int targetCoordinateY, int targetWidth, int targetHeight, int worldCoordinateX, int worldCoordinateY) {
        super(attackPower, currentState, currentHealth, maxHealth, speed, pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, targetCoordinateX, targetCoordinateY, targetWidth, targetHeight, worldCoordinateX, worldCoordinateY);
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
