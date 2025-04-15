package cvut.cz;

import cvut.cz.Map.Collision;
import cvut.cz.Map.Map;
import cvut.cz.Map.MapConstructor;
import cvut.cz.Map.MapSlicer;
import cvut.cz.characters.*;
import cvut.cz.items.*;

import java.util.logging.Logger;
import java.net.URL;

public class Model {
    private static final Logger logger = Logger.getLogger(Model.class.getName());

    //map
    private static MapConstructor mapConstructor;
    private static Map map;

    //player
    private static PlayableCharacter mainPlayer;
    private static Inventory inventory;

    public Model() {
    }

    public void createMap(MapSlicer mapSlicer, URL pathToCollisions, URL pathToMap, int mapCoordinateX, int mapCoordinateY, int mapTargetScaleFactorX, int mapTargetScaleFactorY) {
        mapConstructor = new MapConstructor(mapSlicer, pathToCollisions, pathToMap, mapCoordinateX, mapCoordinateY, mapTargetScaleFactorX, mapTargetScaleFactorY);
        map = mapConstructor.createMap();
    }

    public void movePlayer(Directions direction) {
        int oldX = mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX();
        int oldY = mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY();
        Collision collidedWith = new Collision(0, 0, 0, 0);
        switch (direction) {
            case UP:
                mainPlayer.Move(Directions.UP);

                if (map.checkCollisions(mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX(), mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY(),mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() + mainPlayer.getGameSpriteRenderInformation().getTargetWidth(), oldY + mainPlayer.getGameSpriteRenderInformation().getTargetHeight(), collidedWith)) {
                    mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateY(collidedWith.getWorldCoordinateY() + collidedWith.getHeight());
                    map.translateMap(Directions.UP, oldY - mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY());
                    break;
                }
                map.translateMap(Directions.UP, mainPlayer.getCharacterInformation().getSpeed());

                break;
            case DOWN:
                mainPlayer.Move(Directions.DOWN);

                if (map.checkCollisions(mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX(), oldY, mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() + mainPlayer.getGameSpriteRenderInformation().getTargetWidth(), mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() + mainPlayer.getGameSpriteRenderInformation().getTargetHeight(), collidedWith)) {
                    mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateY(collidedWith.getWorldCoordinateY() - mainPlayer.getGameSpriteRenderInformation().getTargetHeight());
                    map.translateMap(Directions.DOWN, mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() - oldY);
                    break;
                }
                map.translateMap(Directions.DOWN, mainPlayer.getCharacterInformation().getSpeed());
                break;
            case LEFT:
                mainPlayer.Move(Directions.LEFT);

                if (map.checkCollisions(mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX(), mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY(), oldX + mainPlayer.getGameSpriteRenderInformation().getTargetWidth(), mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() + mainPlayer.getGameSpriteRenderInformation().getTargetHeight(), collidedWith)) {
                    mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateX(collidedWith.getWorldCoordinateX() + collidedWith.getWidth());
                    map.translateMap(Directions.LEFT, oldX - mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX());
                    break;
                }
                map.translateMap(Directions.LEFT, mainPlayer.getCharacterInformation().getSpeed());
                break;
            case RIGHT:

                mainPlayer.Move(Directions.RIGHT);

                if (map.checkCollisions(oldX, mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY(), mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() + mainPlayer.getGameSpriteRenderInformation().getTargetWidth(), mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() + mainPlayer.getGameSpriteRenderInformation().getTargetHeight(), collidedWith)) {
                    mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateX(collidedWith.getWorldCoordinateX() - mainPlayer.getGameSpriteRenderInformation().getTargetWidth());
                    map.translateMap(Directions.RIGHT, mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() - oldX);
                    break;
                }
                map.translateMap(Directions.RIGHT, mainPlayer.getCharacterInformation().getSpeed());
                break;
        }

    }

    public void createMainPlayer(URL pathToItems, CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        mainPlayer = new OfficeWorker(pathToItems, characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation);
        mainPlayer.getGameSpriteRenderInformation().setScreenCoordinateX(mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() + map.getGameSpriteRenderInformation().getScreenCoordinateX());
        mainPlayer.getGameSpriteRenderInformation().setScreenCoordinateY(mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() + map.getGameSpriteRenderInformation().getScreenCoordinateY());
    }

    public void createInventory(InventoryInformation inventoryInformation, PlayableCharacter character, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        inventory = new Inventory(inventoryInformation, character, gameSpriteSourceInformation, gameSpriteRenderInformation);
    }

    private void offLoggers() {
    }

    public PlayableCharacter getMainPlayer() { return mainPlayer; }
    public MapConstructor getMapConstructor() { return mapConstructor; }
    public Map getMap() { return map; }
    public Inventory getInventory() { return inventory; }
}
