package cvut.cz.characters;

import java.net.URL;

public abstract class Enemy extends GameCharacter{

    protected Directions currentDirection;

    public Enemy(int attackPower, States currentState, int currentHealth, int maxHelath, double speed, URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int screenCoordinateX, int screenCoordinateY, int targetWidth, int targetHeight, int worldCoordinateX, int worldCoordinateY) {
        super(attackPower, currentState, currentHealth, maxHelath, speed, pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, screenCoordinateX, screenCoordinateY, targetWidth, targetHeight, worldCoordinateX, worldCoordinateY);

    }

    protected abstract void spotPlayer();
    protected abstract void chooseDirection();
}
