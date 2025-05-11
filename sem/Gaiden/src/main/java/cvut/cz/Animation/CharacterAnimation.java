package cvut.cz.Animation;

/**
 * Keeps the animations for the game characters. Saves idle, movement, dying and receiving damage animation.
 */
public class CharacterAnimation {
    //Keeps animation state that was performed before current state
    public AnimationStates previousAnimationState;
    public AnimationStates currentAnimationState;

    //Saves the index of the current animation frame to draw
    public int currentAnimation;

    /**
     * Initializes the character animation with default values:
     * - currentAnimationState is set to IdleDown
     * - previousAnimationState is set to null
     * - currentAnimation is set to 0 (the first frame)
     */
    public CharacterAnimation(){
        previousAnimationState = null;
        currentAnimationState = AnimationStates.IdleDown;
        currentAnimation = 0;
    }

    public AnimationInformation[] idleUpAnimation;
    public AnimationInformation[] idleDownAnimation;
    public AnimationInformation[] idleLeftAnimation;
    public AnimationInformation[] idleRightAnimation;

    public AnimationInformation[] moveRightAnimation;
    public AnimationInformation[] moveLeftAnimation;
    public AnimationInformation[] moveUpAnimation;
    public AnimationInformation[] moveDownAnimation;

    public AnimationInformation[] dyingUpAnimation;
    public AnimationInformation[] dyingDownAnimation;
    public AnimationInformation[] dyingRightAnimation;
    public AnimationInformation[] dyingLeftAnimation;

    public AnimationInformation[] takingDamageUpAnimation;
    public AnimationInformation[] takingDamageDownAnimation;
    public AnimationInformation[] takingDamageLeftAnimation;
    public AnimationInformation[] takingDamageRightAnimation;
}
