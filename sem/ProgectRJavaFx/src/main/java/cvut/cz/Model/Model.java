package cvut.cz.Model;

import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.Map.*;
import cvut.cz.Updatable;
import cvut.cz.characters.*;
import cvut.cz.items.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.net.URL;
import java.util.List;

public class Model {
    private static final Logger logger = Logger.getLogger(Model.class.getName());

    private static final List<GameSprite> drawableObjects = new ArrayList<>();
    private static final List<Updatable> updatableObjects = new ArrayList<>();

    private static Map map;
    private static List<GameCharacter> charactersInWorld;


    private static PlayableCharacter mainPlayer;
    private static Inventory inventory;



    private Model() {}

    public static void createMainPlayer(URL pathToItems, CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation, Map map){
        mainPlayer = MainPlayerModel.getMainPlayerModel().createMainPlayer(pathToItems, characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation, map);
    }

    public static void movePlayer(Directions direction){
        MainPlayerModel.getMainPlayerModel().movePlayer(direction);
    }

    public static void shoot() {
        MainPlayerModel.getMainPlayerModel().shoot();
    }

    public static void createInventory(InventoryInformation inventoryInformation, PlayableCharacter character, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        inventory = MainPlayerModel.getMainPlayerModel().createInventory(inventoryInformation, character, gameSpriteSourceInformation, gameSpriteRenderInformation);
    }

    public static void takeItem() {
        MainPlayerModel.getMainPlayerModel().takeItem();
    }

    public static void createMap(MapSlicer mapSlicer, URL pathToCollisions, List<URL> pathsToSections, MapInformation mapInformation) {
        map = MapModel.getMapModel().createMap(mapSlicer, pathToCollisions, pathsToSections, mapInformation);
    }

    public static void setItemsInWorld(List<Item> itemsInWorld) {
        MapModel.getMapModel().setItemsInWorld(itemsInWorld);
    }

    public static void setMapSpots(List<MapSpot> mapSpots) {
        MapModel.getMapModel().setMapSpots(mapSpots);
    }

    public static void setCharactersInWorld(List<GameCharacter> charactersInWorld) {
        MapModel.getMapModel().setCharactersInWorld(charactersInWorld);
    }

    private static void offLoggers() {
    }


    public static Inventory getInventory() { return inventory; }
    public static MapConstructor getMapConstructor() { return MapModel.getMapModel().getMapConstructor();}
    public static PlayableCharacter getMainPlayer() { return mainPlayer; }
    public static List<GameCharacter> getCharactersInWorld() { return charactersInWorld; }
    public static Map getMap() { return map; }
    public static List<GameSprite> getDrawableObjects() { return drawableObjects; }
    public static List<Updatable> getUpdatableObjects() { return updatableObjects; }
}
