package cvut.cz.characters;

import java.net.URL;

public class WatchMan extends Enemy {
    public WatchMan(int attackPower, States currentState, int currentHealth, int maxHelath, double speed, URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int screenCoordinateX, int screenCoordinateY, int targetWidth, int targetHeight, int worldCoordinateX, int worldCoordinateY) {
        super(attackPower, currentState, currentHealth, maxHelath, speed, pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, screenCoordinateX, screenCoordinateY, targetWidth, targetHeight, worldCoordinateX, worldCoordinateY);
    }

    @Override
    protected void spotPlayer() {

    }

    @Override
    protected void Die() {

    }

    @Override
    protected void chooseDirection() {

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
