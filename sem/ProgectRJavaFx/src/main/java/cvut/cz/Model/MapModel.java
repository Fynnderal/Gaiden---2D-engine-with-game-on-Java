package cvut.cz.Model;

import cvut.cz.Map.*;
import cvut.cz.characters.GameCharacter;
import cvut.cz.items.Item;

import java.net.URL;
import java.util.List;

class MapModel {
    private static MapModel instance;

    private static MapConstructor mapConstructor;
    private static Map map;
    private static List<GameCharacter> charactersInWorld;

    private MapModel() {}

    Map createMap(MapSlicer mapSlicer, URL pathToCollisions, List<URL> pathsToSections, MapInformation mapInformation) {
        mapConstructor = new MapConstructor(mapSlicer, pathToCollisions, pathsToSections,mapInformation);
        map = mapConstructor.createMap();
        Model.getDrawableObjects().addAll(mapConstructor.getMapSections());
        return map;
    }

    void setItemsInWorld(List<Item> itemsInWorld) {
        for (Item item : itemsInWorld) {
            item.getGameSpriteRenderInformation().setScreenCoordinateX(item.getGameSpriteRenderInformation().getWorldCoordinateX() + map.getMapInformation().mapCoordinateX());
            item.getGameSpriteRenderInformation().setScreenCoordinateY(item.getGameSpriteRenderInformation().getWorldCoordinateY() + map.getMapInformation().mapCoordinateY());
            Model.getDrawableObjects().add(item);
        }
        map.setItemsOnMap(itemsInWorld);

    }

    void setMapSpots(List<MapSpot> mapSpots) {
        for (MapSpot mapSpot : mapSpots) {
            mapSpot.getGameSpriteRenderInformation().setScreenCoordinateX(mapSpot.getGameSpriteRenderInformation().getWorldCoordinateX() + map.getMapInformation().mapCoordinateX());
            mapSpot.getGameSpriteRenderInformation().setScreenCoordinateY(mapSpot.getGameSpriteRenderInformation().getWorldCoordinateY() + map.getMapInformation().mapCoordinateY());
            Model.getDrawableObjects().add(mapSpot);
        }
        map.setMapSpots(mapSpots);
    }

    void setCharactersInWorld(List<GameCharacter> charactersInWorld) {
        for (GameCharacter character : charactersInWorld) {
            character.getGameSpriteRenderInformation().setScreenCoordinateX(character.getGameSpriteRenderInformation().getWorldCoordinateX() + map.getMapInformation().mapCoordinateX());
            character.getGameSpriteRenderInformation().setScreenCoordinateY(character.getGameSpriteRenderInformation().getWorldCoordinateY() + map.getMapInformation().mapCoordinateY());
            Model.getDrawableObjects().add(character);
            Model.getUpdatableObjects().add(character);
        }
        MapModel.charactersInWorld = charactersInWorld;
        if (map != null) {
            map.setCharactersOnMap(charactersInWorld);
        }
    }

    MapConstructor getMapConstructor() { return mapConstructor; }

    static MapModel getMapModel() {
        if (instance == null)
            instance = new MapModel();

        return instance;
    }
}
