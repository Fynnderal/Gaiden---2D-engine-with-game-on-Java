package cvut.cz.characters;

import com.fasterxml.jackson.databind.ObjectMapper;

import static cvut.cz.Animation.AnimationStates.*;

import cvut.cz.Animation.PlayerAnimation;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.items.Inventory;
import cvut.cz.items.Item;
import cvut.cz.Map.GameMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.net.URL;
import java.io.FileReader;


public abstract class PlayableCharacter extends GameCharacter {
    private static final Logger logger = Logger.getLogger(PlayableCharacter.class.getName());

    protected List<Item> items;

    protected Inventory inventory;

    protected GameMap gameMap;

    protected final long timeBetweenPistolShots = 400;
    protected final long timeBetweenShotgunShots = 600;
    protected long lastPistolShot;
    protected long lastShotgunShot;

    protected Map<String, GunSight> weaponsAndGunSights;

    public boolean isAiming;


    public PlayableCharacter(CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation);
        isBlocked = false;
    }

    @Override
    public void update() {
        now = System.currentTimeMillis();

        isBlocked = ((now - lastShotgunShot < timeBetweenShotgunShots) || (now - lastPistolShot < timeBetweenPistolShots) ||
                (now - startedDyingTime < dyingTime) || (now - startedGettingDamage < gettingDamageTime));

        super.update();
    }

    protected Directions chooseWeaponDirection(GunSight gunSight){
        double angle = getAngleBetweenPlayerAndGunSight(gunSight);

        if (angle <= 2.36 && angle >= 0.79)
            return Directions.UP;
        else if (angle < 0.79 && angle >= -0.79)
            return Directions.RIGHT;
        else if (angle < -0.79 && angle >= -2.36)
            return Directions.DOWN;
        else
            return Directions.LEFT;
    }

    protected double getAngleBetweenPlayerAndGunSight(GunSight gunSight){
        int playerCenterX = this.getGameSpriteRenderInformation().getScreenCoordinateX() + this.getGameSpriteRenderInformation().getTargetWidth() / 2;
        int playerCenterY = this.getGameSpriteRenderInformation().getScreenCoordinateY() + this.getGameSpriteRenderInformation().getTargetHeight() / 2;

        int gunSightCenterXRelativeToPlayerCenter = gunSight.getCenterXRelativeToGunSight() + gunSight.getGameSpriteRenderInformation().getScreenCoordinateX() - playerCenterX;
        int gunSightCenterYRelativeToPlayerCenter = gunSight.getCenterYRelativeToGunSight() + gunSight.getGameSpriteRenderInformation().getScreenCoordinateY() - playerCenterY;

        return Math.atan2(-gunSightCenterYRelativeToPlayerCenter, gunSightCenterXRelativeToPlayerCenter);
    }

    @Override
    public void move(Directions direction, int speed) {
        if (isBlocked)
            return;

        characterInformation.setCurrentState(States.WALKING);
        switch (direction) {
            case UP:
                this.gameSpriteRenderInformation.setWorldCoordinateY(gameSpriteRenderInformation.getWorldCoordinateY() - speed);
                characterAnimation.currentAnimationState = WalkingUP;
                break;
            case DOWN:
                this.gameSpriteRenderInformation.setWorldCoordinateY(gameSpriteRenderInformation.getWorldCoordinateY() + speed);
                characterAnimation.currentAnimationState = WalkingDOWN;
                break;
            case RIGHT:
                this.gameSpriteRenderInformation.setWorldCoordinateX(gameSpriteRenderInformation.getWorldCoordinateX() + speed);
                characterAnimation.currentAnimationState = WalkingRIGHT;
                break;
            case LEFT:
                this.gameSpriteRenderInformation.setWorldCoordinateX(gameSpriteRenderInformation.getWorldCoordinateX() - speed);
                characterAnimation.currentAnimationState = WalkingLEFT;
                break;
        }
        characterInformation.setCurrentState(States.IDLE);
    }

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

    public Map<String, GunSight> getWeaponsAndGunSights() {
        return weaponsAndGunSights;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setWeaponsAndGunSights(Map<String, GunSight> weaponsAndGunSights) {
        this.weaponsAndGunSights = weaponsAndGunSights;
    }

    public void setMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        inventory.setItems(items);
    }

    public void setCharacterAnimation(PlayerAnimation playerAnimation) {
        super.setCharacterAnimation(playerAnimation);
    }

    public abstract void shoot(Item weapon);

    public abstract void useItem(Item item);

    public abstract void aim(Item weapon);
}
