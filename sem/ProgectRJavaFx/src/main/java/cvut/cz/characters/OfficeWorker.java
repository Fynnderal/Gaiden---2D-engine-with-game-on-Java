package cvut.cz.characters;


import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.Map.Door;
import cvut.cz.Map.MapSpot;
import cvut.cz.Map.VendingMachine;
import cvut.cz.Map.Ventilation;
import cvut.cz.items.InventoryCell;
import cvut.cz.items.Item;
import static cvut.cz.Animation.AnimationStates.*;

import java.util.*;
import java.util.logging.Logger;

/**
 * Represents one of the main characters - an office worker.
 * This class manages the state updates, attacking, shooting, aiming, and item usage.
 * It extends the PlayableCharacter class and provides specific behavior for the office worker.
 */
public class OfficeWorker extends PlayableCharacter{
    private static final Logger logger = Logger.getLogger(OfficeWorker.class.getName());

    // Points of the screen where bullets goes
    private List<int[]> shotPoints;

    // Weapon that player has equipped in the moment
    private Item currentWeapon;

    /**
     * Constructs an OfficeWorker character with the specified character information and sprite details.
     *
     * @param characterInformation Information about the character's attributes.
     * @param gameSpriteSourceInformation Information about the source image for the character's sprite.
     * @param gameSpriteRenderInformation Information about how the sprite is rendered on the screen.
     */
    public OfficeWorker(CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation,
                        GameSpriteRenderInformation gameSpriteRenderInformation) {

        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation);
        isAiming = false;
    }

    /**
     * Updates the state of the office worker.
     * This method extends the update logic from PlayableCharacter and adds logic for aiming.
     */
    @Override
    public void update() {
        super.update();

        if (isAiming) {
            aim(inventory.getEquippedCell().getItem());
        }
    }

    /**
     * Implements the attack method.
     * Determines which characters will take damage based on their position and the shot points.
     */
    @Override
    protected void attack() {
        if (gameMap == null || gameMap.getCharactersOnMap() == null)
            return;

        for (NPC character : gameMap.getCharactersOnMap()) {
            //if the player is not in the character's action area, he can't inflict damage to him.
            if(!character.isPlayerInActionArea())
                continue;

            //checks if shot point is within the character's sprite. If it is, the character gets damage
            for (int[] shotPoint : shotPoints) {
                if (shotPoint[0] >= character.getGameSpriteRenderInformation().getScreenCoordinateX() &&
                    shotPoint[0] <= character.getGameSpriteRenderInformation().getScreenCoordinateX() + character.getCollisionWidth() &&
                    shotPoint[1] >= character.getGameSpriteRenderInformation().getScreenCoordinateY() &&
                    shotPoint[1] <= character.getGameSpriteRenderInformation().getScreenCoordinateY() + character.getCollisionHeight()
                ){

                    character.takeDamage(currentWeapon.getItemInformation().damage());
                }
            }
        }
    }

    /**
     * Determines the direction in which the player performs an action.
     * Updates the animation state based on the direction and whether the player is aiming or shooting.
     *
     * @param direction The direction in which the action is performed.
     * @param aim True if the player is aiming, false if shooting.
     */
    private void chooseActionDirection(Directions direction, boolean aim) {
        if (characterAnimation == null)
            return;

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

    /**
     * Implements the aim method from PlayableCharacter.
     * Determines the direction and updates the animation state for aiming.
     *
     * @param weapon The weapon being used for aiming.
     */
    @Override
    public void aim(Item weapon){
        if (isBlocked)
            return;

        this.currentWeapon = weapon;

        if (weaponsAndCrossHairs == null){
            logger.severe("Can't aim: I hava no gun-sights");
            return;
        }
        chooseActionDirection(chooseWeaponDirection(weaponsAndCrossHairs.get(weapon.getItemInformation().name())), true);
    }

    /**
     * Implements the shoot method from PlayableCharacter.
     *
     * @param weapon The weapon being used for shooting.
     */
    @Override
    public void shoot(Item weapon) {
        if (isBlocked)
            return;

        //Can't shoot if the player had not been aiming before
        if (!isAiming)
            return;

        this.currentWeapon = weapon;

        if (weaponsAndCrossHairs == null){
            logger.severe("Can't shoot, I have no gunSights");
            return;
        }

        CrossHair crossHair = weaponsAndCrossHairs.get(weapon.getItemInformation().name());

        shotPoints = new ArrayList<>();

        InventoryCell inventoryCell;
        switch (weapon.getItemInformation().name()){
            case "shotgun":
                //consuming ammo for shotgun. If there is no ammo, the player can't shoot
                inventoryCell = inventory.getInventoryCellByName("shotgunshell");
                if (inventoryCell != null){
                    getShotPointsForShotGun(crossHair);
                    inventoryCell.setItemAmount(inventoryCell.getItemAmount() - 1);
                }

                break;
            case "pistol":
                //consuming ammo for pistol. If there is no ammo, the player can't shoot
                inventoryCell = inventory.getInventoryCellByName("pistolbullet");
                if (inventoryCell != null) {
                    getShotPointsForPistol(crossHair);
                    inventoryCell.setItemAmount(inventoryCell.getItemAmount() - 1);
                }

                break;


            default:
                logger.warning("Can't use this weapon: " + weapon.getItemInformation().name());
                break;
        }
        if (shotPoints == null)
            return;

        chooseActionDirection(chooseWeaponDirection(weaponsAndCrossHairs.get(weapon.getItemInformation().name())), false);
        attack();
    }

    /**
     * Determines the shot points for a shotgun.
     * Generates multiple random points within the crosshair area.
     *
     * @param crossHair The crosshair used for determining shot points.
     */
    private void getShotPointsForShotGun(CrossHair crossHair){
        if (now - lastShotgunShot >= timeBetweenShotgunShots) {
            int shotPointX;
            int shotPointY;

            //Shotgun fires seven pellets at once
            for (int i = 0; i < 7; i++) {
                Random rand = new Random();

                //ShotPoint must be within cross hair area.
                shotPointX = rand.nextInt(crossHair.getGameSpriteRenderInformation().getTargetWidth() + 1) + crossHair.getGameSpriteRenderInformation().getScreenCoordinateX();
                shotPointY = rand.nextInt(crossHair.getGameSpriteRenderInformation().getTargetHeight() + 1) + crossHair.getGameSpriteRenderInformation().getScreenCoordinateY();
                shotPoints.add(new int[]{shotPointX, shotPointY});
            }
            shotPoints.add(new int[]{crossHair.getGameSpriteRenderInformation().getScreenCoordinateX() + crossHair.getCenterXRelativeToGunSight(), crossHair.getGameSpriteRenderInformation().getScreenCoordinateY() + crossHair.getCenterYRelativeToGunSight()});

            lastShotgunShot = now;
        }
        // If not enough time has passed between shots, player can't shoot yet.
        else{
            shotPoints = null;
        }
    }

    /**
     * Determines the shot points for a pistol.
     * Generates a single point at the center of the crosshair.
     *
     * @param crossHair The crosshair used for determining the shot point.
     */
    private void getShotPointsForPistol(CrossHair crossHair){
        if (now - lastPistolShot >= timeBetweenPistolShots) {
            //Pistol fires only one bullet at once. It goes in the center of cross hair.
            shotPoints.add(new int[]{crossHair.getGameSpriteRenderInformation().getScreenCoordinateX() + crossHair.getCenterXRelativeToGunSight(), crossHair.getGameSpriteRenderInformation().getScreenCoordinateY() + crossHair.getCenterYRelativeToGunSight()});
            lastPistolShot = now;
        }
        else
            shotPoints = null;
    }

    /**
     * Implements the useItem method from PlayableCharacter.
     * Determines how the office worker uses specific items.
     *
     * @param item The item to be used.
     */
    @Override
    public void useItem(Item item) {
        String name = item.getItemInformation().name();
        switch (name) {
            case "tea", "water", "chocolatebar", "medkit":
                characterInformation.setCurrentHealth(characterInformation.getCurrentHealth() - item.getItemInformation().damage());
                item.setAmount(item.getAmount() - 1);
                break;

            case "key", "crowbar":
                List<MapSpot> mapSpots = gameMap.getNearestMapSpots(this);
                for (MapSpot mapSpot : mapSpots) {
                    if (!(mapSpot instanceof Door))
                        continue;

                    if (((Door) mapSpot).openDoor(item)) {
                        if (name.equals("key"))
                            item.setAmount(item.getAmount() - 1);
                        break;
                    }
                }

                break;

            case "screwdriver":
                for (MapSpot mapSpot : gameMap.getNearestMapSpots(this)){
                    if (mapSpot instanceof VendingMachine){
                        ((VendingMachine) mapSpot).giveItem(inventory);
                    }

                    if (mapSpot instanceof Ventilation)
                        ((Ventilation) mapSpot).teleportPlayer(this, gameMap);
                }
                break;
            default:
                logger.warning("I can't use item: " + name);
                break;
        }
    }
}
