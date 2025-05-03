package cvut.cz.Model;

import cvut.cz.GameSprite.GameSprite;
import cvut.cz.Map.*;
import cvut.cz.characters.NPC;
import cvut.cz.items.Item;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class MapModel {
    private static MapModel instance;

    private GameMap gameMap;

    private final List<GameSprite> drawableObjects = new LinkedList<>();
    private final List<Updatable> updatableObjects = new LinkedList<>();

    private MapModel() {}

    public void createMap(MapSlicer mapSlicer, URL pathToCollisions, List<URL> pathsToSections, MapInformation mapInformation) {
        MapConstructor mapConstructor = new MapConstructor(mapSlicer, pathToCollisions, pathsToSections,mapInformation);
        gameMap = mapConstructor.createMap();
        drawableObjects.addAll(gameMap.getMapSections());
    }

    public void setItemsInWorld(List<Item> itemsInWorld) {
        for (Item item : itemsInWorld) {
            item.getGameSpriteRenderInformation().setScreenCoordinateX(item.getGameSpriteRenderInformation().getWorldCoordinateX() + gameMap.getMapInformation().mapCoordinateX());
            item.getGameSpriteRenderInformation().setScreenCoordinateY(item.getGameSpriteRenderInformation().getWorldCoordinateY() + gameMap.getMapInformation().mapCoordinateY());
            drawableObjects.add(item);
        }
        gameMap.setItemsOnMap(itemsInWorld);

    }

    public void setMapSpots(List<MapSpot> mapSpots) {
        for (MapSpot mapSpot : mapSpots) {
            mapSpot.getGameSpriteRenderInformation().setScreenCoordinateX(mapSpot.getGameSpriteRenderInformation().getWorldCoordinateX() + gameMap.getMapInformation().mapCoordinateX());
            mapSpot.getGameSpriteRenderInformation().setScreenCoordinateY(mapSpot.getGameSpriteRenderInformation().getWorldCoordinateY() + gameMap.getMapInformation().mapCoordinateY());
            drawableObjects.add(mapSpot);
        }
        gameMap.setMapSpots(mapSpots);
    }

   public void setCharactersInWorld(List<NPC> charactersInWorld) {
        for (NPC character : charactersInWorld) {
            character.getGameSpriteRenderInformation().setScreenCoordinateX(character.getGameSpriteRenderInformation().getWorldCoordinateX() + gameMap.getMapInformation().mapCoordinateX());
            character.getGameSpriteRenderInformation().setScreenCoordinateY(character.getGameSpriteRenderInformation().getWorldCoordinateY() + gameMap.getMapInformation().mapCoordinateY());
            drawableObjects.add(character);
            updatableObjects.add(character);
        }

        if (gameMap != null) {
            gameMap.setCharactersOnMap(charactersInWorld);
        }
    }

    public static MapModel getMapModel() {
        if (instance == null)
            instance = new MapModel();

        return instance;
    }

    public  GameMap getMap() { return gameMap; }
    public List<GameSprite> getDrawableObjects() { return drawableObjects; }
    public List<Updatable> getUpdatableObjects() { return updatableObjects; }
}
