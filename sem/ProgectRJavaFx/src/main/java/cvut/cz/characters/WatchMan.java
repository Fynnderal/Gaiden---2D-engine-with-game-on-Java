package cvut.cz.characters;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

import java.util.Random;

import static cvut.cz.Animation.AnimationStates.*;
import static cvut.cz.Animation.AnimationStates.WalkingLEFT;

public class WatchMan extends Enemy {

    private final Directions[] possibleHorizontalDirections;
    private final Directions[] possibleVerticalDirections;

    private long lastTimeChooseDirection;
    private final long timeBetweenChooseDirection;

    private int currentSpeed;
    private final int maxSpeed;

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

    @Override
    public void update() {
        if (!isPlayerInActionArea())
            this.characterInformation.setCurrentState(States.IDLE);
        else if (this.characterInformation.getCurrentState() == States.IDLE)
            this.characterInformation.setCurrentState(States.ALARMED);

        if (characterInformation.getCurrentState() == States.ALARMED)
            currentSpeed = maxSpeed / 2;
        else
            currentSpeed = maxSpeed;

        characterInformation.setSpeed(currentSpeed);

        super.update();

    }

    @Override
    protected void move(Directions direction, int speed) {
        int currentWorldCoordinateY;
        int currentWorldCoordinateX;
        switch (direction) {
            case UP:
                currentWorldCoordinateY = gameSpriteRenderInformation.getWorldCoordinateY() - speed;

                characterAnimation.currentAnimationState = characterInformation.getCurrentState() == States.CHASING ? ChasingUp : WalkingUP;


                if (currentWorldCoordinateY >= actionAreaInformation.actionAreaY()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateY(currentWorldCoordinateY);
                    this.gameSpriteRenderInformation.setScreenCoordinateY(gameSpriteRenderInformation.getScreenCoordinateY() - speed);
                }
                break;

            case DOWN:
                currentWorldCoordinateY = gameSpriteRenderInformation.getWorldCoordinateY() + speed;
                characterAnimation.currentAnimationState = characterInformation.getCurrentState() == States.CHASING ? ChasingDown : WalkingDOWN;

                if (currentWorldCoordinateY <= actionAreaInformation.actionAreaY() + actionAreaInformation.actionAreaHeight()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateY(currentWorldCoordinateY);
                    this.gameSpriteRenderInformation.setScreenCoordinateY(gameSpriteRenderInformation.getScreenCoordinateY() + speed);
                }

                break;

            case RIGHT:
                currentWorldCoordinateX = gameSpriteRenderInformation.getWorldCoordinateX() + speed;

                characterAnimation.currentAnimationState = characterInformation.getCurrentState() == States.CHASING ? ChasingRight : WalkingRIGHT;


                if (currentWorldCoordinateX <= actionAreaInformation.actionAreaX() + actionAreaInformation.actionAreaWidth()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateX(currentWorldCoordinateX);
                    this.gameSpriteRenderInformation.setScreenCoordinateX(gameSpriteRenderInformation.getScreenCoordinateX() + speed );
                }

                break;

            case LEFT:
                currentWorldCoordinateX = gameSpriteRenderInformation.getWorldCoordinateX() - speed;
                characterAnimation.currentAnimationState = characterInformation.getCurrentState() == States.CHASING ? ChasingLeft : WalkingLEFT;

                if (currentWorldCoordinateX >= actionAreaInformation.actionAreaX()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateX(currentWorldCoordinateX);
                    this.gameSpriteRenderInformation.setScreenCoordinateX(gameSpriteRenderInformation.getScreenCoordinateX() -speed);
                }

                break;
        }
    }

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

    @Override
    public void takeDamage(int damage) {
        characterInformation.setCurrentState(States.CHASING);
        super.takeDamage(damage);
    }
}
