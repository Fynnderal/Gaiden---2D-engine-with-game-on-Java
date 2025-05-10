package cvut.cz.characters;


import com.sun.security.jgss.GSSUtil;
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

    //Used to define if the character is ready to perform action or he is blocked by previous action.
    protected boolean isBlocked;
    protected boolean isDead;

    // Size of collision of the player
    protected int collisionWidth;
    protected  int collisionHeight;

    /**
     * Initializes the GameCharacter with the specified parameters.
     *
     * @param characterInformation Information about the character.
     * @param gameSpriteSourceInformation Information about the sprite source.
     * @param gameSpriteRenderInformation Information about the sprite rendering.
     * This information is used to set the size of the collision box.
     */
    public GameCharacter(CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
        if (characterInformation !=null)
            this.characterInformation = characterInformation.clone();

        if (gameSpriteRenderInformation != null) {
            this.collisionWidth = gameSpriteRenderInformation.getTargetWidth();
            this.collisionHeight = gameSpriteRenderInformation.getTargetHeight();
        }

        this.isDead = false;
        this.dyingTime = 10000;
        this.gettingDamageTime = 200;
        this.now = System.currentTimeMillis();
    }

    /**
     * Calculates the length of a vector between two points.
     *
     * @param x1 X coordinate of the first point.
     * @param y1 Y coordinate of the first point.
     * @param x2 X coordinate of the second point.
     * @param y2 Y coordinate of the second point.
     * @return The length of the vector.
     */
    protected int calculateLengthOfVector(int x1, int y1, int x2, int y2) {
        return (int) Math.ceil(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
    }

    /**
     * Selects animation based on the current state of the animations
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
     * Applies the specified animation to the character.
     *
     * @param animation The animation to apply.
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

        // Moves to the next frame of the animation.
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

            if (!isBlocked) {
                idle();
            }
        }
    }

    /**
     * Sets the animation state based on the direction of the previous animation.
     *
     * @param animationStates Array of animation states to apply. Must contain four elements, one for each direction.
     */
    protected void setAnimationStateDirection(AnimationStates[] animationStates){
        if (characterAnimation == null)
            return;

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
     * Handles the character taking damage.
     *
     * @param damage The amount of damage to take.
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
     * Handles the character's death.
     */
    public void die(){
        isBlocked = true;
        startedDyingTime = now;
        if (characterAnimation == null)
            isDead = true;

        setAnimationStateDirection(new AnimationStates[]{DyingUp, DyingDown, DyingLeft, DyingRight});
    }
    /**
     * Gets the character's information.
     *
     * @return The character's information.
     */
    public CharacterInformation getCharacterInformation() { return characterInformation; }

    /**
     * Gets the character's animation.
     *
     * @return The character's animation.
     */
    public CharacterAnimation getCharacterAnimation() { return characterAnimation; }

    /**
     * Gets the width of collision for the playable character.
     * @return The width of collision.
     */
    public int getCollisionWidth() {
        return collisionWidth;
    }

    /**
     * Gets the height of collision for the playable character.
     * @return The height of collision.
     */
    public int getCollisionHeight() {
        return collisionHeight;
    }

    /**
     * Checks if the character is dead.
     *
     * @return True if the character is dead, false otherwise.
     */
    public boolean isDead() {return isDead; }

    /**
     * Checks if the character is blocked from performing actions.
     *
     * @return True if the character is blocked, false otherwise.
     */
    public boolean isBlocked(){ return isBlocked; }

    /**
     * Sets the character's animation.
     *
     * @param characterAnimation The animation to set.
     */
    public void setCharacterAnimation(CharacterAnimation characterAnimation) {
        this.characterAnimation = characterAnimation;
        if (characterAnimation == null)
            return;

        this.characterAnimation.previousAnimationState = null;
        this.characterAnimation.currentAnimationState = IdleDown;
    }

    /**
     * Moves the character in the specified direction with the given speed.
     *
     * @param direction The direction to move.
     * @param speed The speed of movement.
     */
    protected abstract void move(Directions direction, int speed);

    /**
     * Handles the character's attack logic.
     */
    protected  abstract void attack();
}
