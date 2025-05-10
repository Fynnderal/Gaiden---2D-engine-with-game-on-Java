package cvut.cz.characters;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

import java.util.Random;

import static cvut.cz.Animation.AnimationStates.*;
import static cvut.cz.Animation.AnimationStates.WalkingLeft;

/**
 * Represents one type of enemy - Watch Man. It manages movement, animation, taking damage, and updating state.
 * This type of enemy is faster and stronger. It can patrol the area and look for the player.
 */
public class WatchMan extends Enemy {
    //Array of possible horizontal directions in which a watchman can move
    private final Directions[] possibleHorizontalDirections;
    //Array of possible vertical directions in which a watchman can move
    private final Directions[] possibleVerticalDirections;

    // Time (in milliseconds) when the last changing of the direction was performed
    private long lastTimeChooseDirection;
    // Time (in milliseconds) that must pass before the next changing of the direction can be performed.
    private final long timeBetweenChooseDirection;

    private int currentSpeed;
    private final int maxSpeed;

    /**
     * Constructs a WatchMan character with the specified character information, sprite details, and interaction details.
     *
     * @param characterInformation Information about the character's attributes.
     * @param gameSpriteSourceInformation Information about the source image for the character's sprite.
     * @param gameSpriteRenderInformation Information about how the sprite is rendered on the screen.
     * @param actionAreaInformation Information about the area where the WatchMan can act.
     * @param mainCharacter The main playable character in the game.
     */
    public WatchMan(CharacterInformation characterInformation,
                    GameSpriteSourceInformation gameSpriteSourceInformation,
                    GameSpriteRenderInformation gameSpriteRenderInformation,
                    ActionAreaInformation actionAreaInformation,
                    PlayableCharacter mainCharacter) {

        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation, actionAreaInformation, mainCharacter);
        this.intervalBetweenAttacks = 700;
        this.timeBetweenChooseDirection = 1100;
        this.possibleHorizontalDirections = new Directions[]{Directions.RIGHT, Directions.LEFT, null};
        this.possibleVerticalDirections = new Directions[]{Directions.UP, Directions.DOWN, null};
        this.currentSpeed = characterInformation.getSpeed();
        this.maxSpeed = characterInformation.getSpeed();
    }

    /**
     * Updates the state of the WatchMan. Should be executed every frame.
     * Handles transitions between states such as IDLE, ALARMED, and CHASING.
     * Also manages the speed of the WatchMan based on its state.
     * Then calls the update method from the superclass.
     */
    @Override
    public void update() {
        if (!isPlayerInActionArea())
            this.characterInformation.setCurrentState(States.IDLE);
        //starts patrolling
        else if (this.characterInformation.getCurrentState() == States.IDLE)
            this.characterInformation.setCurrentState(States.ALARMED);


        //if player is in action area but watchman has not detected him, he moves at half of the maximum speed
        if (characterInformation.getCurrentState() == States.ALARMED)
            currentSpeed = maxSpeed / 2;
        else
            currentSpeed = maxSpeed;

        characterInformation.setSpeed(currentSpeed);

        super.update();
    }

    /**
     * Implements movement for the WatchMan.
     *
     * @param direction The direction of movement.
     * @param speed The speed of movement.
     */
    @Override
    protected void move(Directions direction, int speed) {
        int currentWorldCoordinateY;
        int currentWorldCoordinateX;
        switch (direction) {
            case UP:
                currentWorldCoordinateY = gameSpriteRenderInformation.getWorldCoordinateY() - speed;

                if (characterAnimation != null)
                    characterAnimation.currentAnimationState = characterInformation.getCurrentState() == States.CHASING ? ChasingUp : WalkingUp;

                //Watchman can't leave his action area
                if (currentWorldCoordinateY >= actionAreaInformation.actionAreaY()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateY(currentWorldCoordinateY);
                    this.gameSpriteRenderInformation.setScreenCoordinateY(gameSpriteRenderInformation.getScreenCoordinateY() - speed);
                }
                break;

            case DOWN:
                currentWorldCoordinateY = gameSpriteRenderInformation.getWorldCoordinateY() + speed;

                if (characterAnimation != null)
                    characterAnimation.currentAnimationState = characterInformation.getCurrentState() == States.CHASING ? ChasingDown : WalkingDown;

                //Watchman can't leave his action area
                if (currentWorldCoordinateY + collisionHeight <= actionAreaInformation.actionAreaY() + actionAreaInformation.actionAreaHeight()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateY(currentWorldCoordinateY);
                    this.gameSpriteRenderInformation.setScreenCoordinateY(gameSpriteRenderInformation.getScreenCoordinateY() + speed);
                }

                break;

            case RIGHT:
                currentWorldCoordinateX = gameSpriteRenderInformation.getWorldCoordinateX() + speed;

                if (characterAnimation != null)
                    characterAnimation.currentAnimationState = characterInformation.getCurrentState() == States.CHASING ? ChasingRight : WalkingRight;

                //Watchman can't leave his action area
                if (currentWorldCoordinateX + collisionWidth <= actionAreaInformation.actionAreaX() + actionAreaInformation.actionAreaWidth()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateX(currentWorldCoordinateX);
                    this.gameSpriteRenderInformation.setScreenCoordinateX(gameSpriteRenderInformation.getScreenCoordinateX() + speed );
                }

                break;

            case LEFT:
                currentWorldCoordinateX = gameSpriteRenderInformation.getWorldCoordinateX() - speed;

                if (characterAnimation != null)
                    characterAnimation.currentAnimationState = characterInformation.getCurrentState() == States.CHASING ? ChasingLeft : WalkingLeft;

                //Watchman can't leave his action area
                if (currentWorldCoordinateX >= actionAreaInformation.actionAreaX()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateX(currentWorldCoordinateX);
                    this.gameSpriteRenderInformation.setScreenCoordinateX(gameSpriteRenderInformation.getScreenCoordinateX() -speed);
                }

                break;
        }
    }

    /**
     * Selects the direction of movement.
     * If the WatchMan is patrolling, the direction is chosen randomly.
     * If the WatchMan has detected the player, the direction is determined using the `chooseDirection` method from the Enemy class.
     *
     * @return An array of directions where the first direction is horizontal and the second is vertical.
     */
    @Override
    protected Directions[] chooseDirection() {
        if (isBlocked)
            return new Directions[]{null, null};

        if (characterInformation.getCurrentState() == States.ALARMED) {
            if (now - lastTimeChooseDirection >= timeBetweenChooseDirection) {
                Random rand = new Random();
                int xDirection = rand.nextInt(3);
                int yDirection = rand.nextInt(3);

                Directions[] directions = {null, null};
                directions[0] = this.possibleHorizontalDirections[xDirection];
                directions[1] = this.possibleVerticalDirections[yDirection];

                lastTimeChooseDirection = now;
                return directions;
            }
            return null;
        } else {
            return super.chooseDirection();
        }
    }

    /**
     * Implements the method for receiving damage.
     * If the WatchMan receives damage, it always starts chasing the player.
     *
     * @param damage The amount of damage to be taken.
     */
    @Override
    public void takeDamage(int damage) {
        characterInformation.setCurrentState(States.CHASING);
        super.takeDamage(damage);
    }
}
