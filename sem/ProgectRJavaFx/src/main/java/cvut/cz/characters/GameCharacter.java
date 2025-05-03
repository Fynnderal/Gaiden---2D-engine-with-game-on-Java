package cvut.cz.characters;


import cvut.cz.Animation.AnimationInformation;
import static cvut.cz.Animation.AnimationStates.*;

import cvut.cz.Animation.AnimationStates;
import cvut.cz.Animation.CharacterAnimation;
import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.Model.Updatable;


public abstract class GameCharacter extends GameSprite implements Updatable {

    protected CharacterInformation characterInformation;
    protected CharacterAnimation characterAnimation;

    protected long startedDyingTime;
    protected long dyingTime;

    protected long startedGettingDamage;
    protected long gettingDamageTime;

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

    public CharacterInformation getCharacterInformation() { return characterInformation; }

    protected int calculateLengthOfVector(int x1, int y1, int x2, int y2) {
        return (int) Math.ceil(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
    }

    protected void animate(){
        if (characterAnimation.previousAnimationState != characterAnimation.currentAnimationState) {
            characterAnimation.previousAnimationState = characterAnimation.currentAnimationState;
            characterAnimation.currentAnimation = 0;
        }

        switch (characterAnimation.currentAnimationState){
            case IdleUP:
                applyAnimation(characterAnimation.idleUpAnimation);
                break;

            case IdleDOWN:
                applyAnimation(characterAnimation.idleDownAnimation);
                break;

            case IdleLEFT:
                applyAnimation(characterAnimation.idleLeftAnimation);
                break;

            case IdleRIGHT:
                applyAnimation(characterAnimation.idleRightAnimation);
                break;

            case WalkingUP:
                applyAnimation(characterAnimation.moveUpAnimation);
                break;

            case WalkingDOWN:
                applyAnimation(characterAnimation.moveDownAnimation);
                break;

            case WalkingLEFT:
                applyAnimation(characterAnimation.moveLeftAnimation);
                break;

            case WalkingRIGHT:
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

    protected void applyAnimation(AnimationInformation[] animation) {
        if (animation == null)
            return;

        this.gameSpriteSourceInformation.setSourceCoordinateX(animation[characterAnimation.currentAnimation].sourceX());
        this.gameSpriteSourceInformation.setSourceCoordinateY(animation[characterAnimation.currentAnimation].sourceY());
        this.gameSpriteSourceInformation.setSourceWidth(animation[characterAnimation.currentAnimation].sourceWidth());
        this.gameSpriteSourceInformation.setSourceHeight(animation[characterAnimation.currentAnimation].sourceHeight());
        this.gameSpriteRenderInformation.setTargetHeight(animation[characterAnimation.currentAnimation].targetHeight());
        this.gameSpriteRenderInformation.setTargetWidth(animation[characterAnimation.currentAnimation].targetWidth());

        characterAnimation.currentAnimation++;
        characterAnimation.currentAnimation %= animation.length;
    }

    protected void idle(){
        setAnimationStateDirection(new AnimationStates[]{IdleUP, IdleDOWN, IdleLEFT, IdleRIGHT});
    }

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

    protected void setAnimationStateDirection(AnimationStates[] animationStates){
        switch (characterAnimation.currentAnimationState) {
            case IdleUP, WalkingUP, AttackingUp, ShootingPistolUp, ShootingShotGunUp, TakingDamageUp, DyingUp, AimingPistolUp, AimingShotGunUp, ChasingUp:
                characterAnimation.currentAnimationState = animationStates[0];
                break;
            case IdleDOWN, WalkingDOWN, AttackingDown, ShootingPistolDown, ShootingShotGunDown, TakingDamageDown, DyingDown, AimingPistolDown, AimingShotGunDown, ChasingDown:
                characterAnimation.currentAnimationState = animationStates[1];
                break;
            case IdleLEFT, WalkingLEFT, AttackingLeft, ShootingPistolLeft, ShootingShotGunLeft, TakingDamageLeft, DyingLeft, AimingPistolLeft, AimingShotGunLeft, ChasingLeft:
                characterAnimation.currentAnimationState = animationStates[2];
                break;
            case IdleRIGHT, WalkingRIGHT, AttackingRight, ShootingPistolRight, ShootingShotGunRight, TakingDamageRight, DyingRight, AimingPistolRight, AimingShotGunRight, ChasingRight:
                characterAnimation.currentAnimationState = animationStates[3];
                break;
        }
    }

    public void setCharacterAnimation(CharacterAnimation characterAnimation) {
        this.characterAnimation = characterAnimation;
        this.characterAnimation.previousAnimationState = null;
        this.characterAnimation.currentAnimationState = IdleDOWN;
    }

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

    public void die(){
        isBlocked = true;
        startedDyingTime = now;
        setAnimationStateDirection(new AnimationStates[]{DyingUp, DyingDown, DyingLeft, DyingRight});
    }

    public boolean isDead() {return isDead; }
    public boolean isBlocked(){ return isBlocked; }
    public CharacterAnimation getCharacterAnimation() { return characterAnimation; }


    protected abstract void move(Directions direction, int speed);
    protected  abstract void attack();
}
