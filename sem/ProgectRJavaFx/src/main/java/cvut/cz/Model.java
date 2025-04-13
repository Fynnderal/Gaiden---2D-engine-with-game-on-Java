package cvut.cz;

import cvut.cz.Map.Collision;
import cvut.cz.Map.Map;
import cvut.cz.Map.MapConstructor;
import cvut.cz.Map.MapSlicer;
import cvut.cz.characters.*;
import cvut.cz.items.*;

import java.util.HashMap;
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

    public void createMap(MapSlicer mapSlicer, URL pathToMap, int mapCoordinateX, int mapCoordinateY, int mapTargetScaleFactorX, int mapTargetScaleFactorY, URL pathToCollisions) {
        mapConstructor = new MapConstructor(mapSlicer, pathToMap, mapCoordinateX, mapCoordinateY, mapTargetScaleFactorX, mapTargetScaleFactorY, pathToCollisions);
        map = mapConstructor.createMap();
    }

    public void movePlayer(Directions direction) {
        int oldX = mainPlayer.worldCoordinateX;
        int oldY = mainPlayer.worldCoordinateY;
        Collision collidedWith = new Collision(0, 0, 0, 0);
        switch (direction) {
            case UP:
                mainPlayer.Move(Directions.UP);

                if (map.checkCollisions(mainPlayer.getWorldCoordinateX(), mainPlayer.getWorldCoordinateY(),mainPlayer.getWorldCoordinateX() + mainPlayer.getTargetWidth(), oldY + mainPlayer.getTargetHeight(), collidedWith)) {
                    mainPlayer.setWorldCoordinateY(collidedWith.getWorldCoordinateY() + collidedWith.getHeight());
                    map.translateMap(Directions.UP, oldY - mainPlayer.getWorldCoordinateY());
                    break;
                }
                map.translateMap(Directions.UP, mainPlayer.getSpeed());

                break;
            case DOWN:
                mainPlayer.Move(Directions.DOWN);

                if (map.checkCollisions(mainPlayer.getWorldCoordinateX(), oldY, mainPlayer.getWorldCoordinateX() + mainPlayer.getTargetWidth(), mainPlayer.getWorldCoordinateY() + mainPlayer.getTargetHeight(), collidedWith)) {
                    mainPlayer.setWorldCoordinateY(collidedWith.getWorldCoordinateY() - mainPlayer.getTargetHeight());
                    map.translateMap(Directions.DOWN, mainPlayer.getWorldCoordinateY() - oldY);
                    break;
                }
                map.translateMap(Directions.DOWN, mainPlayer.getSpeed());
                break;
            case LEFT:
                mainPlayer.Move(Directions.LEFT);

                if (map.checkCollisions(mainPlayer.getWorldCoordinateX(), mainPlayer.getWorldCoordinateY(), oldX + mainPlayer.getTargetWidth(), mainPlayer.getWorldCoordinateY() + mainPlayer.getTargetHeight(), collidedWith)) {
                    mainPlayer.setWorldCoordinateX(collidedWith.getWorldCoordinateX() + collidedWith.getWidth());
                    map.translateMap(Directions.LEFT, oldX - mainPlayer.getWorldCoordinateX());
                    break;
                }
                map.translateMap(Directions.LEFT, mainPlayer.getSpeed());
                break;
            case RIGHT:

                mainPlayer.Move(Directions.RIGHT);

                if (map.checkCollisions(oldX, mainPlayer.getWorldCoordinateY(), mainPlayer.getWorldCoordinateX() + mainPlayer.getTargetWidth(), mainPlayer.getWorldCoordinateY() + mainPlayer.getTargetHeight(), collidedWith)) {
                    mainPlayer.setWorldCoordinateX(collidedWith.getWorldCoordinateX() - mainPlayer.getTargetWidth());
                    map.translateMap(Directions.RIGHT, mainPlayer.getWorldCoordinateX() - oldX);
                    break;
                }
                map.translateMap(Directions.RIGHT, mainPlayer.getSpeed());
                break;
        }

    }


    public void setMainPlayer(URL pathToItems, URL pathToSprite, int sourceX, int sourceY, int sourceWidth, int sourceHeight, int screenWidth, int screenHeight, int worldX, int worldY, int attackPower, States currentState, int currentHealth, int maxHeath, double speed) {
        mainPlayer = new OfficeWorker(attackPower, currentState, currentHealth, maxHeath, speed, pathToItems, pathToSprite, sourceX, sourceY, sourceWidth, sourceHeight, worldX + map.getScreenCoordinateX(), worldY + map.getScreenCoordinateY(), screenWidth, screenHeight, worldX, worldY);

    }

    public void createInventory(HashMap<String, ItemInformation> possibleItems, InventoryCellInformation inventoryCellInformation, Pointer pointer, GameCharacter character, URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int targetCoordinateX, int targetCoordinateY, int targetWidth, int targetHeight) {
        inventory = new Inventory(possibleItems, inventoryCellInformation, pointer, character, pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, targetCoordinateX, targetCoordinateY, targetWidth, targetHeight);
    }

    private void offLoggers() {
    }

    public PlayableCharacter getMainPlayer() { return mainPlayer; }
    public MapConstructor getMapConstructor() { return mapConstructor; }
    public Map getMap() { return map; }
    public Inventory getInventory() { return inventory; }
}
