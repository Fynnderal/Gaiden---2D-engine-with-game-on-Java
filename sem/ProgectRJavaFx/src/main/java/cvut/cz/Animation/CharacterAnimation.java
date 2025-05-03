package cvut.cz.Animation;

/**
 * Saves animations for game characters
 */
public class CharacterAnimation {
    public AnimationStates previousAnimationState;
    public AnimationStates currentAnimationState;

    //Saves index of current image to draw in animation
    public int currentAnimation;


    public CharacterAnimation(){
        previousAnimationState = null;
        currentAnimationState = AnimationStates.IdleDOWN;
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
