package cvut.cz.characters;

import static cvut.cz.Animation.AnimationStates.*;

import cvut.cz.Animation.EnemyAnimation;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;



public abstract class Enemy extends NPC{
    protected long intervalBetweenAttacks;
    protected long lastTimeAttacked;

    protected Directions currentHorizontalDirection;
    protected Directions currentVerticalDirection;

    public Enemy(CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation,
                 GameSpriteRenderInformation gameSpriteRenderInformation, ActionAreaInformation actionAreaInformation, PlayableCharacter mainCharacter)
    {
        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation, actionAreaInformation, mainCharacter);
        isInteractable = false;
        intervalBetweenAttacks = 1000;
    }

    @Override
    public void update() {
        if (isDead)
            return;

        if (mainCharacter == null)
            return;

        now = System.currentTimeMillis();
        isBlocked = ( (now - lastTimeAttacked < intervalBetweenAttacks) || (now - startedDyingTime < dyingTime) ||
                        (now - startedGettingDamage < gettingDamageTime));

        spotPlayer();
        Directions[] direction = chooseDirection();
        if (direction != null) {
            currentVerticalDirection = direction[1];
            currentHorizontalDirection = direction[0];
        }

        if (currentHorizontalDirection != null && currentVerticalDirection != null) {
            move(currentHorizontalDirection,(int) Math.ceil(characterInformation.getSpeed() / 1.414));
            move(currentVerticalDirection,(int) Math.ceil(characterInformation.getSpeed() / 1.414));

        }
        else {
            if (currentHorizontalDirection != null)
                move(currentHorizontalDirection, characterInformation.getSpeed());
            if (currentVerticalDirection != null)
                move(currentVerticalDirection, characterInformation.getSpeed());
        }

        attack();

        super.update();
    }



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
        else{
            this.getGameSpriteRenderInformation().setWorldCoordinateX(this.getGameSpriteRenderInformation().getWorldCoordinateX());
            this.getGameSpriteRenderInformation().setScreenCoordinateX(this.getGameSpriteRenderInformation().getScreenCoordinateX());
            return null;
        }

        return null;
    }

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
        else{
            this.getGameSpriteRenderInformation().setWorldCoordinateY(this.getGameSpriteRenderInformation().getWorldCoordinateY());
            this.getGameSpriteRenderInformation().setScreenCoordinateY(this.getGameSpriteRenderInformation().getScreenCoordinateY());
            return null;
        }

        return null;
    }


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

    @Override
    protected void animate() {
        super.animate();
        switch (characterAnimation.currentAnimationState) {
            case AttackingUp:
                applyAnimation(((EnemyAnimation) characterAnimation).attackUpAnimation);
                break;

            case AttackingDown:
                applyAnimation(((EnemyAnimation) characterAnimation).attackDownAnimation);
                break;

            case AttackingLeft:
                applyAnimation(((EnemyAnimation) characterAnimation).attackLeftAnimation);
                break;

            case AttackingRight:
                applyAnimation(((EnemyAnimation) characterAnimation).attackRightAnimation);
                break;
            case ChasingUp:
                applyAnimation(((EnemyAnimation) characterAnimation).chasingUpAnimation);
                break;

            case ChasingDown:
                applyAnimation(((EnemyAnimation) characterAnimation).chasingDownAnimation);
                break;

            case ChasingLeft:
                applyAnimation(((EnemyAnimation) characterAnimation).chasingLeftAnimation);
                break;

            case ChasingRight:
                applyAnimation(((EnemyAnimation) characterAnimation).chasingRightAnimation);
                break;
        }
    }

    public boolean isPlayerWithinAttackZone(){
        int mainCharacterX = mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateX();
        int mainCharacterY = mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateY();
        int mainCharacterWidth = mainCharacter.getGameSpriteRenderInformation().getTargetWidth();
        int mainCharacterHeight = mainCharacter.getGameSpriteRenderInformation().getTargetHeight();

        return (mainCharacterX < gameSpriteRenderInformation.getWorldCoordinateX() + gameSpriteRenderInformation.getTargetWidth() / 2 &&
                mainCharacterX + mainCharacterWidth / 2 >= gameSpriteRenderInformation.getWorldCoordinateX() &&
                mainCharacterY < gameSpriteRenderInformation.getTargetHeight() / 2 + gameSpriteRenderInformation.getWorldCoordinateY() &&
                mainCharacterY + mainCharacterHeight / 2 >= gameSpriteRenderInformation.getWorldCoordinateY());
    }

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
                    case IdleUP, WalkingUP:
                        characterAnimation.currentAnimationState = AttackingUp;
                        break;

                    case IdleDOWN, WalkingDOWN:
                        characterAnimation.currentAnimationState = AttackingDown;
                        break;

                    case IdleLEFT, WalkingLEFT:
                        characterAnimation.currentAnimationState = AttackingLeft;
                        break;

                    case IdleRIGHT, WalkingRIGHT:
                        characterAnimation.currentAnimationState = AttackingRight;
                        break;
                }
            }
        }
    }

    @Override
    public String[] interact() {
        return null;
    }

    @Override
    public void changePlayersAnswer(Directions direction) {}

    public void setCharacterAnimation(EnemyAnimation characterAnimation) {
        super.setCharacterAnimation(characterAnimation);
    }

}
