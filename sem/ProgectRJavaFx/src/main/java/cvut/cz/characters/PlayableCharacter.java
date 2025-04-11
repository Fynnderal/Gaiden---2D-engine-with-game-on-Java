package cvut.cz.characters;

import java.util.logging.Logger;
import java.net.URL;


public abstract class PlayableCharacter extends GameCharacter{
    private static final Logger logger = Logger.getLogger(PlayableCharacter.class.getName());

    public PlayableCharacter(URL pathToInventory, URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int screenCoordinateX, int screenCoordinateY, int targetWidth, int targetHeight, int worldCoordinateX, int worldCoordinateY ) {
        super(pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, screenCoordinateX, screenCoordinateY, targetWidth, targetHeight, worldCoordinateX, worldCoordinateY);
    }

    public PlayableCharacter(int attackPower, States currentState, int currentHealth, int maxHealth, double speed, URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int screenCoordinateX, int screenCoordinateY, int targetWidth, int targetHeight, int worldCoordinateX, int worldCoordinateY) {
        super(attackPower, currentState, currentHealth, maxHealth, speed, pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, screenCoordinateX, screenCoordinateY, targetWidth, targetHeight, worldCoordinateX, worldCoordinateY);
    }

    public void Move(Directions direction) {
        currentState = States.WALKING;
        switch (direction) {
            case UP:
                this.worldCoordinateY -= speed;
                break;
            case DOWN:
                this.worldCoordinateY += speed;
                break;
            case RIGHT:
                this.worldCoordinateX += speed;
                break;
            case LEFT:
                this.worldCoordinateX -= speed;
                break;
        }
        currentState = States.IDLE;
    }
}
