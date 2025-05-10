package cvut.cz.characters;

import static cvut.cz.Animation.AnimationStates.*;

import cvut.cz.Animation.PlayerAnimation;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.items.Inventory;
import cvut.cz.items.Item;
import cvut.cz.Map.GameMap;

import java.util.Map;

/**
 * Represents playable characters. It manages updating the state, movement, and animation.
 * This abstract class provides the base functionality for all playable characters in the game.
 */
public abstract class PlayableCharacter extends GameCharacter {
    protected Inventory inventory;

    protected GameMap gameMap;

    // Time (in milliseconds) that must pass before the next pistol shot.
    protected final long timeBetweenPistolShots = 400;
    // Time (in milliseconds) when the last pistol shot was performed
    protected long lastPistolShot;

    // Time (in milliseconds) that must pass before the next shotgun shot.
    protected final long timeBetweenShotgunShots = 600;
    // Time (in milliseconds) when the last shotgun shot was performed
    protected long lastShotgunShot;

    //Contains pairs where key is a name of weapon and value is cross hair that matches that weapon.
    protected Map<String, CrossHair> weaponsAndCrossHairs;



    public boolean isAiming;

    /**
     * Constructs a playable character with the specified character information and sprite details.
     *
     * @param characterInformation Information about the character's attributes.
     * @param gameSpriteSourceInformation Information about the source image for the character's sprite.
     * @param gameSpriteRenderInformation Information about how the sprite is rendered on the screen.
     */
    public PlayableCharacter(CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation);

        isBlocked = false;
    }

    /**
     * Updates the state of the character. Should be executed every frame.
     * This method checks if the character should remain blocked based on recent actions or animations.
     * Then call the update method of the superclass.
     */
    @Override
    public void update() {
        now = System.currentTimeMillis();

        /*
        Check if character has to be still blocked.
        True if not enough time has passed since performing a shot, receiving damage, or starting dying animation .
         */
        isBlocked = ((now - lastShotgunShot < timeBetweenShotgunShots) || (now - lastPistolShot < timeBetweenPistolShots) ||
                (now - startedDyingTime < dyingTime) || (now - startedGettingDamage < gettingDamageTime));

        super.update();
    }

    /**
     * Chooses the direction in which the weapon and the player should look based on the position of the crosshair.
     *
     * @param crossHair The crosshair that determines the direction.
     * @return The direction in which the player should look.
     */
    protected Directions chooseWeaponDirection(CrossHair crossHair){
        double angle = getAngleBetweenPlayerAndGunSight(crossHair);

        if (angle <= 2.36 && angle >= 0.79)
            return Directions.UP;
        else if (angle < 0.79 && angle >= -0.79)
            return Directions.RIGHT;
        else if (angle < -0.79 && angle >= -2.36)
            return Directions.DOWN;
        else
            return Directions.LEFT;
    }

    /**
     * Calculates the angle on the screen between the player and the crosshair.
     *
     * @param crossHair The crosshair for which the angle should be calculated.
     * @return The angle in radians.
     */
    protected double getAngleBetweenPlayerAndGunSight(CrossHair crossHair){
        int playerCenterX = this.getGameSpriteRenderInformation().getScreenCoordinateX() + this.getCollisionWidth() / 2;
        int playerCenterY = this.getGameSpriteRenderInformation().getScreenCoordinateY() + this.getCollisionHeight() / 2;

        //calculates X coordinate (in pixels) of the crosshair center relative to the player center
        int crossHairCenterXRelativeToPlayerCenter = crossHair.getCenterXRelativeToGunSight() + crossHair.getGameSpriteRenderInformation().getScreenCoordinateX() - playerCenterX;

        //calculates Y coordinate (in pixels) of the crosshair center relative to the player center
        int crossHairCenterYRelativeToPlayerCenter = crossHair.getCenterYRelativeToGunSight() + crossHair.getGameSpriteRenderInformation().getScreenCoordinateY() - playerCenterY;

        return Math.atan2(-crossHairCenterYRelativeToPlayerCenter, crossHairCenterXRelativeToPlayerCenter);
    }

    /**
     * Implements the movement of the player.
     *
     * @param direction The direction in which the player must move.
     * @param speed The speed with which the player must move.
     */
    @Override
    public void move(Directions direction, int speed) {
        if (isBlocked)
            return;

        characterInformation.setCurrentState(States.WALKING);
        switch (direction) {
            case UP:
                this.gameSpriteRenderInformation.setWorldCoordinateY(gameSpriteRenderInformation.getWorldCoordinateY() - speed);
                characterAnimation.currentAnimationState = WalkingUp;
                break;
            case DOWN:
                this.gameSpriteRenderInformation.setWorldCoordinateY(gameSpriteRenderInformation.getWorldCoordinateY() + speed);
                characterAnimation.currentAnimationState = WalkingDown;
                break;
            case RIGHT:
                this.gameSpriteRenderInformation.setWorldCoordinateX(gameSpriteRenderInformation.getWorldCoordinateX() + speed);
                characterAnimation.currentAnimationState = WalkingRight;
                break;
            case LEFT:
                this.gameSpriteRenderInformation.setWorldCoordinateX(gameSpriteRenderInformation.getWorldCoordinateX() - speed);
                characterAnimation.currentAnimationState = WalkingLeft;
                break;
        }

        characterInformation.setCurrentState(States.IDLE);
    }

    /**
     * Manages animations.
     * This method uses the animate() method of the GameCharacter class and extends it by handling unique player animations.
     */
    @Override
    protected void animate() {
        super.animate();

        PlayerAnimation animation = (PlayerAnimation) characterAnimation;
        switch (animation.currentAnimationState) {
            case ShootingPistolUp:
                applyAnimation(animation.shootingPistolUpAnimation);
                break;

            case ShootingPistolDown:
                applyAnimation(animation.shootingPistolDownAnimation);
                break;

            case ShootingPistolLeft:
                applyAnimation(animation.shootingPistolLeftAnimation);
                break;

            case ShootingPistolRight:
                applyAnimation(animation.shootingPistolRightAnimation);
                break;

            case ShootingShotGunDown:
                applyAnimation(animation.shootingShotGunDownAnimation);
                break;

            case ShootingShotGunLeft:
                applyAnimation(animation.shootingShotGunLeftAnimation);
                break;

            case ShootingShotGunRight:
                applyAnimation(animation.shootingShotGunRightAnimation);
                break;
            case ShootingShotGunUp:
                applyAnimation(animation.shootingShotGunUpAnimation);
                break;

            case AimingPistolDown:
                applyAnimation(animation.aimingPistolDownAnimation);
                break;

            case AimingPistolLeft:
                applyAnimation(animation.aimingPistolLeftAnimation);
                break;

            case AimingPistolRight:
                applyAnimation(animation.aimingPistolRightAnimation);
                break;

            case AimingPistolUp:
                applyAnimation(animation.aimingPistolUpAnimation);
                break;

            case AimingShotGunDown:
                applyAnimation(animation.aimingShotGunDownAnimation);
                break;

            case AimingShotGunLeft:
                applyAnimation(animation.aimingShotGunLeftAnimation);
                break;

            case AimingShotGunRight:
                applyAnimation(animation.aimingShotGunRightAnimation);
                break;

            case AimingShotGunUp:
                applyAnimation(animation.aimingShotGunUpAnimation);
                break;
        }
    }


    /**
     * Gets the map of weapons and their corresponding crosshairs.
     *
     * @return A map where the key is the weapon name and the value is the crosshair.
     */
    public Map<String, CrossHair> getWeaponsAndCrossHairs() {
        return weaponsAndCrossHairs;
    }

    /**
     * Gets the inventory of the playable character.
     *
     * @return The inventory object.
     */
    public Inventory getInventory() {
        return inventory;
    }



    /**
     * Sets the map of weapons and their corresponding crosshairs.
     *
     * @param weaponsAndCrossHairs A map where the key is the weapon name and the value is the crosshair.
     */
    public void setWeaponsAndCrossHairs(Map<String, CrossHair> weaponsAndCrossHairs) { this.weaponsAndCrossHairs = weaponsAndCrossHairs; }

    /**
     * Sets the game map where the character is located.
     *
     * @param gameMap The game map object.
     */
    public void setMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    /**
     * Sets the inventory of the playable character.
     *
     * @param inventory The inventory object.
     */
    public void setInventory(Inventory inventory) {this.inventory = inventory; }

    /**
     * Sets the character animation for the playable character.
     *
     * @param playerAnimation The player animation object.
     */
    public void setCharacterAnimation(PlayerAnimation playerAnimation) {
        super.setCharacterAnimation(playerAnimation);
    }

    /**
     * Manages shooting for the playable character.
     *
     * @param weapon The weapon to be used for shooting.
     */
    public abstract void shoot(Item weapon);

    /**
     * Manages using items for the playable character.
     *
     * @param item The item to be used.
     */
    public abstract void useItem(Item item);

    /**
     * Manages aiming for the playable character.
     *
     * @param weapon The weapon to be used for aiming.
     */
    public abstract void aim(Item weapon);
}
