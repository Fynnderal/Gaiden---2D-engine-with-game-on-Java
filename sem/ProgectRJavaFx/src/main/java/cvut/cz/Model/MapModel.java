package cvut.cz.Model;

import cvut.cz.GameSprite.GameSprite;
import cvut.cz.Map.*;
import cvut.cz.characters.NPC;
import cvut.cz.items.Item;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents the model of the game map, managing its creation, objects, and interactions.
 * This class follows the singleton pattern to ensure a single instance is used throughout the application.
 */
public class MapModel {
    private static MapModel instance;

    private GameMap gameMap;

    // List of objects to draw
    private final List<GameSprite> drawableObjects = new LinkedList<>();
    // List of objects to update every frame
    private final List<Updatable> updatableObjects = new LinkedList<>();

    private int currentLevel = 1;

    // Path file where current level id is saved
    private String pathToFileWithLevel;

    private PassageBetweenLevels passageBetweenLevels;

    private MapModel() {}

    /**
     * Creates the game map using the provided parameters and initializes drawable objects.
     *
     * @param mapSlicer The MapSlicer used to slice the map into tiles.
     * @param pathToCollisions The URL to the file containing collision data.
     * @param pathsToSections A list of URLs to the map sections.
     * @param mapInformation Information about the map, such as dimensions and coordinates.
     */
    public void createMap(MapSlicer mapSlicer, URL pathToCollisions, List<URL> pathsToSections, MapInformation mapInformation) {
        MapConstructor mapConstructor = new MapConstructor(mapSlicer, pathToCollisions, pathsToSections,mapInformation);
        gameMap = mapConstructor.createMap();
        drawableObjects.addAll(gameMap.getMapSections());
    }

    /**
     * Sets the current level of the map.
     *
     * @param currentLevel The new level to set.
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * Sets the items in the world and updates their screen coordinates.
     *
     * @param itemsInWorld A list of items to be placed in the world.
     */
    public void setItemsInWorld(List<Item> itemsInWorld) {
        for (Item item : itemsInWorld) {
            item.getGameSpriteRenderInformation().setScreenCoordinateX(item.getGameSpriteRenderInformation().getWorldCoordinateX() + gameMap.getMapInformation().mapCoordinateX());
            item.getGameSpriteRenderInformation().setScreenCoordinateY(item.getGameSpriteRenderInformation().getWorldCoordinateY() + gameMap.getMapInformation().mapCoordinateY());
            drawableObjects.add(item);
        }
        gameMap.setItemsOnMap(itemsInWorld);

    }

    /**
     * Sets the map spots and updates their screen coordinates.
     *
     * @param mapSpots A list of map spots to be added to the map.
     */
    public void setMapSpots(List<MapSpot> mapSpots) {
        for (MapSpot mapSpot : mapSpots) {
            mapSpot.getGameSpriteRenderInformation().setScreenCoordinateX(mapSpot.getGameSpriteRenderInformation().getWorldCoordinateX() + gameMap.getMapInformation().mapCoordinateX());
            mapSpot.getGameSpriteRenderInformation().setScreenCoordinateY(mapSpot.getGameSpriteRenderInformation().getWorldCoordinateY() + gameMap.getMapInformation().mapCoordinateY());
            drawableObjects.add(mapSpot);
        }
        gameMap.setMapSpots(mapSpots);
    }

    /**
     * Sets the characters on the map, updates their screen coordinates, and adds them to drawable and updatable objects.
     *
     * @param charactersInWorld A list of NPCs to be added to the world.
     */
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

    /**
     * Retrieves the singleton instance of the MapModel.
     *
     * @return The singleton instance of MapModel.
     */
    public static MapModel getMapModel() {
        if (instance == null)
            instance = new MapModel();

        return instance;
    }

    /**
     * Gets the game map managed by this model.
     *
     * @return The game map.
     */
    public  GameMap getMap() { return gameMap; }

    /**
     * Gets the list of drawable objects on the map.
     *
     * @return The list of drawable objects.
     */
    public List<GameSprite> getDrawableObjects() { return drawableObjects; }

    /**
     * Gets the list of updatable objects on the map.
     *
     * @return The list of updatable objects.
     */
    public List<Updatable> getUpdatableObjects() { return updatableObjects; }

    /**
     * Gets the current level of the map.
     *
     * @return The current level.
     */
    public int getCurrentLevel() { return currentLevel; }

    /**
     * Gets the path to the file where the current level ID is saved.
     *
     * @return The path to the file with the current level ID.
     */
    public String getPathToFileWithLevel() {
        return pathToFileWithLevel;
    }

    /**
     * Gets the passage between levels.
     *
     * @return The passage between levels.
     */
    public PassageBetweenLevels getPassageBetweenLevels() {
        return passageBetweenLevels;
    }

    /**
     * Sets passage between levels.
     */
    public void setPassageBetweenLevels(PassageBetweenLevels passageBetweenLevels) {
        this.passageBetweenLevels = passageBetweenLevels;
    }

    /**
     * Sets the path to the file where the current level ID is saved.
     *
     * @param pathToFileWithLevel The path to the file with the current level ID.
     */
    public void setPathToFileWithLevel(String pathToFileWithLevel) {
        this.pathToFileWithLevel = pathToFileWithLevel;
    }
}
