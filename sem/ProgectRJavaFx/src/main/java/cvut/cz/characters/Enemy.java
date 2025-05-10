package cvut.cz.characters;

import static cvut.cz.Animation.AnimationStates.*;

import cvut.cz.Animation.EnemyAnimation;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;


/**
 * Represents a hostile character. Manages update, movement, attacking and animation of an enemy.
 */
public abstract class Enemy extends NPC{
    // Time (in milliseconds) that must pass before the next attack can be performed.
    protected long intervalBetweenAttacks;
    // Time (in milliseconds) when the last attack was performed
    protected long lastTimeAttacked;

    // Horizontal movement direction at the moment
    protected Directions currentHorizontalDirection;
    // Vertical movement direction at the moment
    protected Directions currentVerticalDirection;

    /**
     * Initializes the enemy to specified values.
     * IsInteractable is set to false.
     * IntervalBetweenAttacks is set to 1000.
     * @param characterInformation - keeps information about the character
     * @param gameSpriteSourceInformation - keeps information about the character sprite
     * @param gameSpriteRenderInformation - keeps information about the final image of character on the screen
     * @param actionAreaInformation - keeps information about action area of the enemy
     * @param mainCharacter - keeps reference to the main character
     */
    public Enemy(CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation,
                 GameSpriteRenderInformation gameSpriteRenderInformation, ActionAreaInformation actionAreaInformation, PlayableCharacter mainCharacter)
    {
        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation, actionAreaInformation, mainCharacter);
        isInteractable = false;
        intervalBetweenAttacks = 1000;
    }

    /**
     * Updates state of character. Should be executed every frame.
     */
    @Override
    public void update() {
        if (isDead)
            return;

        if (mainCharacter == null)
            return;

        now = System.currentTimeMillis();

        /*
        Check if character has to be still blocked.
        True if not enough time has passed since performing an attack, receiving damage, or starting dying animation .
         */
        isBlocked = ( (now - lastTimeAttacked < intervalBetweenAttacks) || (now - startedDyingTime < dyingTime) ||
                        (now - startedGettingDamage < gettingDamageTime));

        spotPlayer();

        Directions[] direction = chooseDirection();
        if (direction != null) {
            currentVerticalDirection = direction[1];
            currentHorizontalDirection = direction[0];
        }

        /*
        if currentHorizontalDirection and currentVerticalDirection are not null, character moves diagonally.
        To prevent an increase in movement speed speed is divided by 1.414
         */
        if (currentHorizontalDirection != null && currentVerticalDirection != null) {
            move(currentHorizontalDirection,(int) Math.ceil(characterInformation.getSpeed() / 1.414));
            move(currentVerticalDirection,(int) Math.ceil(characterInformation.getSpeed() / 1.414));

        }
        /*
        if character moves either horizontally or vertically, no modification of speed is needed
         */
        else {
            if (currentHorizontalDirection != null)
                move(currentHorizontalDirection, characterInformation.getSpeed());
            if (currentVerticalDirection != null)
                move(currentVerticalDirection, characterInformation.getSpeed());
        }

        attack();

        super.update();
    }


    /**
     * Decides whether the enemy must move horizontally or not and in what direction to reach the main character.
     *
     * @return Direction of moving (Right or Left). Null if character must not move horizontally.
     */
    protected Directions chooseHorizontalDirection() {
        int dx = mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateX() - this.gameSpriteRenderInformation.getWorldCoordinateX();

        if (Math.abs(dx) > this.getCharacterInformation().getSpeed()){
            if (dx > 0) {
                return Directions.RIGHT;
            }
            if (dx < 0) {
                return Directions.LEFT;
            }
        }
        /*
        if horizontal distance between the enemy and the main character is less that speed of the enemy,
        teleport the enemy to the main character's position
         */
        else{
            this.getGameSpriteRenderInformation().setWorldCoordinateX(this.getGameSpriteRenderInformation().getWorldCoordinateX());
            this.getGameSpriteRenderInformation().setScreenCoordinateX(this.getGameSpriteRenderInformation().getScreenCoordinateX());
            return null;
        }

        return null;
    }

    /**
     * Decides whether the enemy must move vertically or not and in what direction to reach the main character.
     *
     * @return Direction of moving (Up or Down). Null if character must not move horizontally.
     */
    protected Directions chooseVerticalDirection() {
        int dy = mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateY() - this.gameSpriteRenderInformation.getWorldCoordinateY();

        if (Math.abs(dy) > this.getCharacterInformation().getSpeed()){
            if (dy > 0) {
                return Directions.DOWN;
            }
            if (dy < 0) {
                return  Directions.UP;
            }
        }
        /*
        if vertical distance between the enemy and the main character is less that speed of the enemy,
        teleport the enemy to the main character's position
         */
        else{
            this.getGameSpriteRenderInformation().setWorldCoordinateY(this.getGameSpriteRenderInformation().getWorldCoordinateY());
            this.getGameSpriteRenderInformation().setScreenCoordinateY(this.getGameSpriteRenderInformation().getScreenCoordinateY());
            return null;
        }

        return null;
    }

    /**
     * Decides in which direction the enemy must move
     *
     * @return Array that consists of two directions: vertical and horizontal. Both elements equal to null if the enemy must not move.
     */
    protected Directions[] chooseDirection(){
        if (isBlocked)
            return new Directions[]{null, null};

        Directions[] directions = {null, null};
        if (this.characterInformation.getCurrentState() == States.CHASING) {
            directions[0] = chooseHorizontalDirection();
            directions[1] = chooseVerticalDirection();
        }

        return directions;
    }

    /**
     * Extends method animate() from class GameCharacter. Adds logic for handling unique animations of enemy.
     */
    @Override
    protected void animate() {
        super.animate();

        EnemyAnimation enemyAnimation = (EnemyAnimation) characterAnimation;
        switch (characterAnimation.currentAnimationState) {
            case AttackingUp:
                applyAnimation(enemyAnimation.attackUpAnimation);
                break;

            case AttackingDown:
                applyAnimation(enemyAnimation.attackDownAnimation);
                break;

            case AttackingLeft:
                applyAnimation(enemyAnimation.attackLeftAnimation);
                break;

            case AttackingRight:
                applyAnimation(enemyAnimation.attackRightAnimation);
                break;
            case ChasingUp:
                applyAnimation(enemyAnimation.chasingUpAnimation);
                break;

            case ChasingDown:
                applyAnimation(enemyAnimation.chasingDownAnimation);
                break;

            case ChasingLeft:
                applyAnimation(enemyAnimation.chasingLeftAnimation);
                break;

            case ChasingRight:
                applyAnimation(enemyAnimation.chasingRightAnimation);
                break;
        }
    }

    /**
     * Decides whether the main character is close enough to attack him.
     *
     * @return true if the main character is reachable for the attack, false otherwise
     */
    public boolean isPlayerWithinAttackZone(){
        int mainCharacterX = mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateX();
        int mainCharacterY = mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateY();
        int mainCharacterWidth = mainCharacter.getCollisionWidth();
        int mainCharacterHeight = mainCharacter.getCollisionHeight();

        //To be reachable for an attack the main character sprite must cover a half of the enemy sprite
        return (mainCharacterX < gameSpriteRenderInformation.getWorldCoordinateX() + collisionWidth / 2 &&
                mainCharacterX + mainCharacterWidth / 2 >= gameSpriteRenderInformation.getWorldCoordinateX() &&
                mainCharacterY < collisionHeight / 2 + gameSpriteRenderInformation.getWorldCoordinateY() &&
                mainCharacterY + mainCharacterHeight / 2 >= gameSpriteRenderInformation.getWorldCoordinateY());
    }

    /**
     * Implements abstract method attack from GameCharacter. It handles simple inflicting damage and animations.
     */
    @Override
    protected void attack() {
        if (isBlocked)
            return;

        if (isPlayerWithinAttackZone()) {
            mainCharacter.takeDamage(characterInformation.getAttackPower());
            lastTimeAttacked = now;
            if (characterAnimation != null) {
                isBlocked = true;
                switch (characterAnimation.currentAnimationState) {
                    case IdleUp, WalkingUp:
                        characterAnimation.currentAnimationState = AttackingUp;
                        break;

                    case IdleDown, WalkingDown:
                        characterAnimation.currentAnimationState = AttackingDown;
                        break;

                    case IdleLeft, WalkingLeft:
                        characterAnimation.currentAnimationState = AttackingLeft;
                        break;

                    case IdleRight, WalkingRight:
                        characterAnimation.currentAnimationState = AttackingRight;
                        break;
                }
            }
        }
    }

    /**
     * Player cannot interact with an enemy.
     *
     * @return null as interaction is not allowed.
     */
    @Override
    public String[] interact() {
        return null;
    }

    /**
     * Player cannot have a conversation with an enemy.
     *
     * @param direction The direction of the player's answer (not used).
     */    @Override
    public void changePlayersAnswer(Directions direction) {}

    /**
     * Sets the character animation for the enemy.
     *
     * @param characterAnimation The animation to set.
     */
    public void setCharacterAnimation(EnemyAnimation characterAnimation) {
        super.setCharacterAnimation(characterAnimation);
    }

}
