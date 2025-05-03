package cvut.cz.characters;


import cvut.cz.Animation.AnimationInformation;
import static cvut.cz.Animation.AnimationStates.*;

import cvut.cz.Animation.AnimationStates;
import cvut.cz.Animation.CharacterAnimation;
import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.Model.Updatable;


/**
 * Represents a character in the game. Manages animation, taking damage and dying of a character.
 */
public abstract class GameCharacter extends GameSprite implements Updatable {

    protected CharacterInformation characterInformation;
    protected CharacterAnimation characterAnimation;

    // Time (in milliseconds) when method die() was called
    protected long startedDyingTime;
    // Time (in milliseconds) for which the character is dying
    protected long dyingTime;

    // Time (in milliseconds) when method takeDamage() was called
    protected long startedGettingDamage;
    // Time (in milliseconds) for which the character is taking damage
    protected long gettingDamageTime;

    // Current time (in milliseconds)
    protected long now;

    protected boolean isBlocked;
    protected boolean isDead;

    public GameCharacter(CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.characterInformation = characterInformation.clone();
        this.isDead = false;
        this.dyingTime = 10000;
        this.gettingDamageTime = 200;
        this.now = System.currentTimeMillis();
    }


    protected int calculateLengthOfVector(int x1, int y1, int x2, int y2) {
        return (int) Math.ceil(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
    }

    /**
     * Selects animation based on the current state of the animation
     */
    protected void animate(){
        if (characterAnimation.previousAnimationState != characterAnimation.currentAnimationState) {
            characterAnimation.previousAnimationState = characterAnimation.currentAnimationState;
            characterAnimation.currentAnimation = 0;
        }

        switch (characterAnimation.currentAnimationState){
            case IdleUp:
                applyAnimation(characterAnimation.idleUpAnimation);
                break;

            case IdleDown:
                applyAnimation(characterAnimation.idleDownAnimation);
                break;

            case IdleLeft:
                applyAnimation(characterAnimation.idleLeftAnimation);
                break;

            case IdleRight:
                applyAnimation(characterAnimation.idleRightAnimation);
                break;

            case WalkingUp:
                applyAnimation(characterAnimation.moveUpAnimation);
                break;

            case WalkingDown:
                applyAnimation(characterAnimation.moveDownAnimation);
                break;

            case WalkingLeft:
                applyAnimation(characterAnimation.moveLeftAnimation);
                break;

            case WalkingRight:
                applyAnimation(characterAnimation.moveRightAnimation);
                break;

            case DyingUp:
                applyAnimation(characterAnimation.dyingUpAnimation);
                if (characterAnimation.currentAnimation == 0)
                    isDead = true;
                break;

            case DyingDown:
                applyAnimation(characterAnimation.dyingDownAnimation);
                if (characterAnimation.currentAnimation == 0)
                    isDead = true;
                break;

            case DyingLeft:
                applyAnimation(characterAnimation.dyingLeftAnimation);
                if (characterAnimation.currentAnimation == 0)
                    isDead = true;
                break;

            case DyingRight:
                applyAnimation(characterAnimation.dyingRightAnimation);
                if (characterAnimation.currentAnimation == 0)
                    isDead = true;
                break;

            case TakingDamageUp:
                applyAnimation(characterAnimation.takingDamageUpAnimation);
                break;

            case TakingDamageDown:
                applyAnimation(characterAnimation.takingDamageDownAnimation);
                break;

            case TakingDamageLeft:
                applyAnimation(characterAnimation.takingDamageLeftAnimation);
                break;

            case TakingDamageRight:
                applyAnimation(characterAnimation.takingDamageRightAnimation);
                break;
        }
    }

    /**
     * Manages animation and apply it to the character
     *
     * @param animation - animation to be applied
     */
    protected void applyAnimation(AnimationInformation[] animation) {
        if (animation == null)
            return;

        this.gameSpriteSourceInformation.setSourceCoordinateX(animation[characterAnimation.currentAnimation].sourceX());
        this.gameSpriteSourceInformation.setSourceCoordinateY(animation[characterAnimation.currentAnimation].sourceY());

        this.gameSpriteSourceInformation.setSourceWidth(animation[characterAnimation.currentAnimation].sourceWidth());
        this.gameSpriteSourceInformation.setSourceHeight(animation[characterAnimation.currentAnimation].sourceHeight());

        this.gameSpriteRenderInformation.setTargetHeight(animation[characterAnimation.currentAnimation].targetHeight());
        this.gameSpriteRenderInformation.setTargetWidth(animation[characterAnimation.currentAnimation].targetWidth());

        //next frame is ready to be drawn
        characterAnimation.currentAnimation++;
        characterAnimation.currentAnimation %= animation.length;
    }

    /**
     * Resets state of the player animation to idle
     */
    protected void idle(){
        setAnimationStateDirection(new AnimationStates[]{IdleUp, IdleDown, IdleLeft, IdleRight});
    }

    /**
     * Updates state of the character. Should be called every frame.
     */
    @Override
    public void update() {
        if (isDead)
            return;

        if (characterAnimation != null) {
            animate();

            if (!isBlocked)
                idle();
        }
    }

    /**
     * Set the animation state based on the direction of the previous animation.
     *
     * @param animationStates - Array of animation states that should be applied. it must consist of 4 elements, one for each direction.
     */
    protected void setAnimationStateDirection(AnimationStates[] animationStates){
        switch (characterAnimation.currentAnimationState) {
            case IdleUp, WalkingUp, AttackingUp, ShootingPistolUp, ShootingShotGunUp, TakingDamageUp, DyingUp, AimingPistolUp, AimingShotGunUp, ChasingUp:
                characterAnimation.currentAnimationState = animationStates[0];
                break;
            case IdleDown, WalkingDown, AttackingDown, ShootingPistolDown, ShootingShotGunDown, TakingDamageDown, DyingDown, AimingPistolDown, AimingShotGunDown, ChasingDown:
                characterAnimation.currentAnimationState = animationStates[1];
                break;
            case IdleLeft, WalkingLeft, AttackingLeft, ShootingPistolLeft, ShootingShotGunLeft, TakingDamageLeft, DyingLeft, AimingPistolLeft, AimingShotGunLeft, ChasingLeft:
                characterAnimation.currentAnimationState = animationStates[2];
                break;
            case IdleRight, WalkingRight, AttackingRight, ShootingPistolRight, ShootingShotGunRight, TakingDamageRight, DyingRight, AimingPistolRight, AimingShotGunRight, ChasingRight:
                characterAnimation.currentAnimationState = animationStates[3];
                break;
        }
    }



    /**
     * Manages taking damage by the character
     *
     * @param damage - amount of damage to be taken
     */
    public void takeDamage(int damage) {
        characterInformation.setCurrentHealth(characterInformation.getCurrentHealth() - damage);

        if (characterInformation.getCurrentHealth() <= 0) {
            die();
            return;
        }
        isBlocked = true;
        startedGettingDamage = now;
        setAnimationStateDirection(new AnimationStates[]{TakingDamageUp, TakingDamageDown, TakingDamageLeft, TakingDamageRight});
    }

    /**
     * Manages dying of the character
     */
    public void die(){
        isBlocked = true;
        startedDyingTime = now;
        setAnimationStateDirection(new AnimationStates[]{DyingUp, DyingDown, DyingLeft, DyingRight});
    }

    public CharacterInformation getCharacterInformation() { return characterInformation; }
    public CharacterAnimation getCharacterAnimation() { return characterAnimation; }

    public boolean isDead() {return isDead; }
    public boolean isBlocked(){ return isBlocked; }

    public void setCharacterAnimation(CharacterAnimation characterAnimation) {
        this.characterAnimation = characterAnimation;
        this.characterAnimation.previousAnimationState = null;
        this.characterAnimation.currentAnimationState = IdleDown;
    }

    protected abstract void move(Directions direction, int speed);
    protected  abstract void attack();
}
