package cvut.cz.characters;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

import java.util.Random;

public class WatchMan extends Enemy {

    private final Directions[] possibleHorizontalDirections;
    private final Directions[] possibleVerticalDirections;

    private long lastTimeChooseDirection;
    private final long timeBetweenChooseDirection = 1100 ;

    public WatchMan(CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation, PlayableCharacter mainPlayer, EnemyInformation enemyInformation) {
        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation, mainPlayer, enemyInformation);
        this.intervalBetweenAttacks = 700;
        this.possibleHorizontalDirections = new Directions[]{Directions.RIGHT, Directions.LEFT, null};
        this.possibleVerticalDirections = new Directions[]{Directions.UP, Directions.DOWN, null};
    }

    @Override
    public void update() {
        if (!isPlayerInActionArea())
            this.characterInformation.setCurrentState(States.IDLE);
        else if (this.characterInformation.getCurrentState() == States.IDLE)
            this.characterInformation.setCurrentState(States.ALARMED);

        super.update();

    }

    @Override
    protected Directions[] chooseDirection() {
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
        }else {
            return super.chooseDirection();
        }
    }

    @Override
    protected void Die() {

    }
}
