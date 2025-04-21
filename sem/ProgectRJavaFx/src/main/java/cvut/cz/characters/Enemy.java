package cvut.cz.characters;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;


public abstract class Enemy extends GameCharacter{

    protected PlayableCharacter mainCharacter;
    protected EnemyInformation enemyInformation;

    protected long intervalBetweenAttacks = 1200;
    protected long lastTimeAttacked = 0;
    protected long now;
    protected Directions currentHorizontalDirection;
    protected Directions currentVerticalDirection;

    public Enemy(CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation,
                 GameSpriteRenderInformation gameSpriteRenderInformation, PlayableCharacter mainCharacter, EnemyInformation enemyInformation) {
        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.mainCharacter = mainCharacter;
        this.enemyInformation = enemyInformation;
    }

    @Override
    public void update() {
        now = System.currentTimeMillis();
        spotPlayer();
        attack();
        Directions[] direction = chooseDirection();
        if (direction != null) {
            currentVerticalDirection = direction[1];
            currentHorizontalDirection = direction[0];
        }

        if (currentHorizontalDirection != null)
            move(currentHorizontalDirection);
        if (currentVerticalDirection != null)
            move(currentVerticalDirection);
    }

    protected void spotPlayer() {
        int mainCharacterX = mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateX();
        int mainCharacterY = mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateY();
        int mainCharacterWidth = mainCharacter.getGameSpriteRenderInformation().getTargetWidth();
        int mainCharacterHeight = mainCharacter.getGameSpriteRenderInformation().getTargetHeight();


        if (mainCharacterX < enemyInformation.actionAreaX() + enemyInformation.actionAreaWidth() &&
            mainCharacterX + mainCharacterWidth >= enemyInformation.actionAreaX() &&
            mainCharacterY < enemyInformation.actionAreaY() + enemyInformation.actionAreaHeight() &&
            mainCharacterY + mainCharacterHeight >= enemyInformation.actionAreaY()
        ){
            int distance = calculateLengthOfVector(this.gameSpriteRenderInformation.getWorldCoordinateX(), this.gameSpriteRenderInformation.getWorldCoordinateY(), mainCharacterX, mainCharacterY);

            if (distance <= enemyInformation.spottingRadius())
                this.characterInformation.setCurrentState(States.CHASING);
        }
    }

    protected boolean isPlayerInActionArea() {
        return (
            mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateX() < enemyInformation.actionAreaX() + enemyInformation.actionAreaWidth() &&
            mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateX() + mainCharacter.getGameSpriteRenderInformation().getTargetWidth() >= enemyInformation.actionAreaX() &&
            mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateY() < enemyInformation.actionAreaY() + enemyInformation.actionAreaHeight() &&
            mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateY() + mainCharacter.getGameSpriteRenderInformation().getTargetHeight() >= enemyInformation.actionAreaY()
        );
    }

    protected Directions[] chooseDirection(){
        Directions[] directions = {null, null};
        if (this.characterInformation.getCurrentState() == States.CHASING) {
            int dx = mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateX() - this.gameSpriteRenderInformation.getWorldCoordinateX();
            int dy = mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateY() - this.gameSpriteRenderInformation.getWorldCoordinateY();
            if (Math.abs(dx) > this.getCharacterInformation().getSpeed()){
                if (dx > 0) {
                    directions[0] = (Directions.RIGHT);
                }
                if (dx < 0) {
                    directions[0] = (Directions.LEFT);
                }
            }
            else{
                this.getGameSpriteRenderInformation().setWorldCoordinateX(this.getGameSpriteRenderInformation().getWorldCoordinateX());
                this.getGameSpriteRenderInformation().setScreenCoordinateX(this.getGameSpriteRenderInformation().getScreenCoordinateX());
                directions[0] = null;
            }

            if (Math.abs(dy) > this.getCharacterInformation().getSpeed()){
                if (dy > 0) {
                    directions[1] =  (Directions.DOWN);
                }
                if (dy < 0) {
                    directions[1] =  (Directions.UP);
                }
            }
            else{
                this.getGameSpriteRenderInformation().setWorldCoordinateY(this.getGameSpriteRenderInformation().getWorldCoordinateY());
                this.getGameSpriteRenderInformation().setScreenCoordinateY(this.getGameSpriteRenderInformation().getScreenCoordinateY());
                directions[1] = null;
            }
        }
        return directions;
    }

    @Override
    protected void move(Directions direction) {
        int currentWorldCoordinateY;
        int currentWorldCoordinateX;
        switch (direction) {
            case UP:
                currentWorldCoordinateY = gameSpriteRenderInformation.getWorldCoordinateY() - characterInformation.getSpeed();

                if (currentWorldCoordinateY >= enemyInformation.actionAreaY()){
                    this.gameSpriteRenderInformation.setWorldCoordinateY(currentWorldCoordinateY);
                    this.gameSpriteRenderInformation.setScreenCoordinateY(gameSpriteRenderInformation.getScreenCoordinateY() - characterInformation.getSpeed());
                }

                break;

            case DOWN:
                currentWorldCoordinateY = gameSpriteRenderInformation.getWorldCoordinateY() + characterInformation.getSpeed();

                if (currentWorldCoordinateY <= enemyInformation.actionAreaY() + enemyInformation.actionAreaHeight()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateY(currentWorldCoordinateY);
                    this.gameSpriteRenderInformation.setScreenCoordinateY(gameSpriteRenderInformation.getScreenCoordinateY() + characterInformation.getSpeed());
                }

                break;

            case RIGHT:
                currentWorldCoordinateX = gameSpriteRenderInformation.getWorldCoordinateX() + characterInformation.getSpeed();

                if (currentWorldCoordinateX <= enemyInformation.actionAreaX() + enemyInformation.actionAreaWidth()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateX(currentWorldCoordinateX);
                    this.gameSpriteRenderInformation.setScreenCoordinateX(gameSpriteRenderInformation.getScreenCoordinateX() + characterInformation.getSpeed());
                }

                break;

            case LEFT:
                currentWorldCoordinateX = gameSpriteRenderInformation.getWorldCoordinateX() - characterInformation.getSpeed();

                if (currentWorldCoordinateX >= enemyInformation.actionAreaX()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateX(currentWorldCoordinateX);
                    this.gameSpriteRenderInformation.setScreenCoordinateX(gameSpriteRenderInformation.getScreenCoordinateX() - characterInformation.getSpeed());
                }

                break;
        }
    }

    @Override
    public void takeDamage(int damage) {
        characterInformation.setCurrentHealth(characterInformation.getCurrentHealth() - damage);
    }

    protected void attack() {
        if (now - lastTimeAttacked >= intervalBetweenAttacks ) {
            int mainCharacterX = mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateX();
            int mainCharacterY = mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateY();
            int mainCharacterWidth = mainCharacter.getGameSpriteRenderInformation().getTargetWidth();
            int mainCharacterHeight = mainCharacter.getGameSpriteRenderInformation().getTargetHeight();

            if (mainCharacterX < gameSpriteRenderInformation.getWorldCoordinateX() + gameSpriteRenderInformation.getTargetWidth() &&
                    mainCharacterX + mainCharacterWidth >= gameSpriteRenderInformation.getWorldCoordinateX() &&
                    mainCharacterY < gameSpriteRenderInformation.getTargetHeight() + gameSpriteRenderInformation.getWorldCoordinateY() &&
                    mainCharacterY + mainCharacterHeight >= gameSpriteRenderInformation.getWorldCoordinateY()
            ) {
                mainCharacter.takeDamage(characterInformation.attackPower);
                lastTimeAttacked = now;
            }
        }

    }
}
