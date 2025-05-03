package cvut.cz.characters;


import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.Map.Door;
import cvut.cz.Map.MapSpot;
import cvut.cz.items.InventoryCell;
import cvut.cz.items.Item;
import static cvut.cz.Animation.AnimationStates.*;

import java.util.*;
import java.util.logging.Logger;

public class OfficeWorker extends PlayableCharacter{
    private static final Logger logger = Logger.getLogger(OfficeWorker.class.getName());

    private List<int[]> shotPoints;
    private Item currentWeapon;

    public OfficeWorker(CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation,
                        GameSpriteRenderInformation gameSpriteRenderInformation) {

        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation);
        isAiming = false;
    }


    @Override
    public void update() {
        super.update();

        if (isAiming) {
            aim(inventory.getEquippedCell().getItem());
        }
    }

    @Override
    protected void attack() {
        if (gameMap.getCharactersOnMap() == null)
            return;

        for (NPC character : gameMap.getCharactersOnMap()) {
            if(!character.isPlayerInActionArea())
                continue;
            for (int[] shotPoint : shotPoints) {
                if (shotPoint[0] >= character.getGameSpriteRenderInformation().getScreenCoordinateX() &&
                        shotPoint[0] <= character.getGameSpriteRenderInformation().getScreenCoordinateX() + character.getGameSpriteRenderInformation().getTargetWidth() &&
                        shotPoint[1] >= character.getGameSpriteRenderInformation().getScreenCoordinateY() &&
                        shotPoint[1] <= character.getGameSpriteRenderInformation().getScreenCoordinateY() + character.getGameSpriteRenderInformation().getTargetHeight()
                ){

                    character.takeDamage(currentWeapon.getItemInformation().damage());
                }
            }
        }
    }

    private void chooseActionDirection(Directions direction, boolean aim) {
        switch(direction){
            case UP:
                if (currentWeapon.getItemInformation().name().equals("pistol"))
                    characterAnimation.currentAnimationState = !aim ? ShootingPistolUp : AimingPistolUp;
                if (currentWeapon.getItemInformation().name().equals("shotgun"))
                    characterAnimation.currentAnimationState = !aim ? ShootingShotGunUp : AimingShotGunUp ;
                break;

            case DOWN:
                if (currentWeapon.getItemInformation().name().equals("pistol"))
                    characterAnimation.currentAnimationState = !aim ? ShootingPistolDown : AimingPistolDown;
                if (currentWeapon.getItemInformation().name().equals("shotgun"))
                    characterAnimation.currentAnimationState = !aim ? ShootingShotGunDown : AimingShotGunDown;
                break;

            case LEFT:
                if (currentWeapon.getItemInformation().name().equals("pistol"))
                    characterAnimation.currentAnimationState = !aim ? ShootingPistolLeft : AimingPistolLeft;

                if (currentWeapon.getItemInformation().name().equals("shotgun"))
                    characterAnimation.currentAnimationState = !aim ? ShootingShotGunLeft : AimingShotGunLeft;
                break;

            case RIGHT:
                if (currentWeapon.getItemInformation().name().equals("pistol"))
                    characterAnimation.currentAnimationState = !aim ? ShootingPistolRight : AimingPistolRight;
                if (currentWeapon.getItemInformation().name().equals("shotgun"))
                    characterAnimation.currentAnimationState = !aim ? ShootingShotGunRight : AimingShotGunRight;
                 break;
        }
    }

    @Override
    public void aim(Item weapon){
        if (isBlocked)
            return;

        this.currentWeapon = weapon;

        if (weaponsAndGunSights == null){
            logger.severe("Can't aim: I hava no gun-sights");
            return;
        }
        chooseActionDirection(chooseWeaponDirection(weaponsAndGunSights.get(weapon.getItemInformation().name())), true);
    }

    @Override
    public void shoot(Item weapon) {
        if (isBlocked)
            return;
        if (!isAiming)
            return;

        this.currentWeapon = weapon;

        if (weaponsAndGunSights == null){
            logger.severe("Can't shoot, I have no gunSights");
            return;
        }

        GunSight gunSight = weaponsAndGunSights.get(weapon.getItemInformation().name());

        shotPoints = new ArrayList<>();
        InventoryCell inventoryCell;
        switch (weapon.getItemInformation().name()){
            case "shotgun":
                inventoryCell = inventory.getInventoryCellByName("shotgunshell");
                if (inventoryCell != null){
                    getShotPointsForShotGun(gunSight);
                    inventoryCell.setItemAmount(inventoryCell.getItemAmount() - 1);
                }

                break;
            case "pistol":
                inventoryCell = inventory.getInventoryCellByName("pistolbullet");
                if (inventoryCell != null) {
                    getShotPointsForPistol(gunSight);
                    inventoryCell.setItemAmount(inventoryCell.getItemAmount() - 1);
                }

                break;
            default:
                logger.warning("Can't use this weapon: " + weapon.getItemInformation().name());
                break;
        }
        if (shotPoints == null)
            return;
        chooseActionDirection(chooseWeaponDirection(weaponsAndGunSights.get(weapon.getItemInformation().name())), false);
        attack();
    }


    private void getShotPointsForShotGun(GunSight gunSight){
        if (now - lastShotgunShot >= timeBetweenShotgunShots) {
            int shotPointX;
            int shotPointY;
            for (int i = 0; i < 7; i++) {
                Random rand = new Random();
                shotPointX = rand.nextInt(gunSight.getGameSpriteRenderInformation().getTargetWidth() + 1) + gunSight.getGameSpriteRenderInformation().getScreenCoordinateX();
                shotPointY = rand.nextInt(gunSight.getGameSpriteRenderInformation().getTargetHeight() + 1) + gunSight.getGameSpriteRenderInformation().getScreenCoordinateY();
                shotPoints.add(new int[]{shotPointX, shotPointY});
            }
            shotPoints.add(new int[]{gunSight.getGameSpriteRenderInformation().getScreenCoordinateX() + gunSight.getCenterXRelativeToGunSight(), gunSight.getGameSpriteRenderInformation().getScreenCoordinateY() + gunSight.getCenterYRelativeToGunSight()});

            lastShotgunShot = now;
        }
        else{
            shotPoints = null;
        }
    }

    private void getShotPointsForPistol(GunSight gunSight){
        if (now - lastPistolShot >= timeBetweenPistolShots) {
            shotPoints.add(new int[]{gunSight.getGameSpriteRenderInformation().getScreenCoordinateX() + gunSight.getCenterXRelativeToGunSight(), gunSight.getGameSpriteRenderInformation().getScreenCoordinateY() + gunSight.getCenterYRelativeToGunSight()});
            lastPistolShot = now;
        }
        else
            shotPoints = null;
    }


    @Override
    public void useItem(Item item) {
        String name = item.getItemInformation().name();
        switch (name) {
            case "tea", "water", "chocolatebar", "medkit":
                characterInformation.setCurrentHealth(characterInformation.getCurrentHealth() - item.getItemInformation().damage());
                break;

            case "key":
                List<MapSpot> mapSpots = gameMap.getNearestMapSpots(this);
                for (MapSpot mapSpot : mapSpots) {
                    if (!(mapSpot instanceof Door))
                        continue;
                    if (((Door) mapSpot).openDoor(item))
                        break;
                }
                break;
            default:
                logger.warning("I can't use item: " + name);
                break;
        }
    }
}
