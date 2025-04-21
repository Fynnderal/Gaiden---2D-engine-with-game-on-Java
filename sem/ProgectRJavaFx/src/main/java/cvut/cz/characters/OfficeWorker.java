package cvut.cz.characters;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.Map.Door;
import cvut.cz.Map.MapSpot;
import cvut.cz.items.Item;
import cvut.cz.Map.Map;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class OfficeWorker extends PlayableCharacter{
    private static final Logger logger = Logger.getLogger(OfficeWorker.class.getName());

    private final Map map;
    public OfficeWorker(URL pathToItems, CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation,
                        GameSpriteRenderInformation gameSpriteRenderInformation, Map map) {

        super(pathToItems, characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation, map);
        this.map = map;
    }



    @Override
    public void shoot(Item weapon, List<GameCharacter> charactersOnMap) {
        if (weaponsAndGunSights == null){
            logger.severe("Can't shoot, I have no gunSights");
            return;
        }

        GunSight gunSight = weaponsAndGunSights.get(weapon.getItemInformation().name());

        List<int[]> shotPoints = new ArrayList<>();
        switch (weapon.getItemInformation().name()){
            case "shotgun":
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
                break;
            case "pistol":
                System.out.println("We are here");
                if (now - lastPistolShot >= timeBetweenPistolShots) {
                    shotPoints.add(new int[]{gunSight.getGameSpriteRenderInformation().getScreenCoordinateX() + gunSight.getCenterXRelativeToGunSight(), gunSight.getGameSpriteRenderInformation().getScreenCoordinateY() + gunSight.getCenterYRelativeToGunSight()});
                    lastPistolShot = now;
                }
                else
                    shotPoints = null;
                break;
            default:
                logger.warning("Can't use this weapon: " + weapon.getItemInformation().name());
                break;
        }
        if (shotPoints == null)
            return;

        for (GameCharacter character : charactersOnMap) {
            for (int[] shotPoint : shotPoints) {
                if (shotPoint[0] >= character.getGameSpriteRenderInformation().getScreenCoordinateX() &&
                        shotPoint[0] <= character.getGameSpriteRenderInformation().getScreenCoordinateX() + character.getGameSpriteRenderInformation().getTargetWidth() &&
                        shotPoint[1] >= character.getGameSpriteRenderInformation().getScreenCoordinateY() &&
                        shotPoint[1] <= character.getGameSpriteRenderInformation().getScreenCoordinateY() + character.getGameSpriteRenderInformation().getTargetHeight()
                ){
                    character.takeDamage(weapon.getItemInformation().damage());
                }
            }
        }
    }

    @Override
    protected void Die() {
        System.out.println("Not implemented yet");
    }

    @Override
    public void useItem(Item item) {
        String name = item.getItemInformation().name();
        switch (name) {
            case "tea":
                characterInformation.setCurrentHealth(characterInformation.getCurrentHealth() + 10);
                System.out.println(characterInformation.getCurrentHealth());
                break;
            case "water":
                characterInformation.setCurrentHealth(characterInformation.getCurrentHealth() + 3);
                System.out.println(characterInformation.getCurrentHealth());
                break;
            case "key":
                List<MapSpot> mapSpots = map.checkNearestMapSpots(this);
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
