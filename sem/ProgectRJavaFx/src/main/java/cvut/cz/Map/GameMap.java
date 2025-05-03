package cvut.cz.Map;


import cvut.cz.GameSprite.GameSprite;
import cvut.cz.characters.Directions;
import cvut.cz.characters.GameCharacter;
import cvut.cz.characters.NPC;
import cvut.cz.items.Item;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

    private final List<Collision> collisions;

    private List<Item> itemsOnMap;
    private List<NPC> charactersOnMap;

    private List<MapSpot> mapSpots;

    private final List<MapSection> mapSections;
    private final MapInformation mapInformation;

    public GameMap(List<Collision> collisions, List<MapSection> mapSections, MapInformation mapInformation)
    {
        this.mapInformation = mapInformation;
        this.mapSections = mapSections;
        this.collisions = collisions;
    }

    private boolean isObjectNear(GameCharacter gameCharacter, GameSprite object) {
        int gameCharacterX = gameCharacter.getGameSpriteRenderInformation().getScreenCoordinateX();
        int gameCharacterY = gameCharacter.getGameSpriteRenderInformation().getScreenCoordinateY();
        int gameCharacterWidth = gameCharacter.getGameSpriteRenderInformation().getTargetWidth();
        int gameCharacterHeight = gameCharacter.getGameSpriteRenderInformation().getTargetHeight();

        int currentObjectX = object.getGameSpriteRenderInformation().getScreenCoordinateX();
        int currentObjectY = object.getGameSpriteRenderInformation().getScreenCoordinateY();
        int currentObjectWidth = object.getGameSpriteRenderInformation().getTargetWidth();
        int currentObjectHeight = object.getGameSpriteRenderInformation().getTargetHeight();

        return  (gameCharacterX <= currentObjectX + currentObjectWidth + 5 &&
                gameCharacterX + gameCharacterWidth >= currentObjectX - 5 &&
                gameCharacterY <= currentObjectHeight + currentObjectY + 5 &&
                gameCharacterY + gameCharacterHeight >= currentObjectY - 5);
    }

    public NPC getNearestNPC(GameCharacter gameCharacter) {
        if (charactersOnMap == null || charactersOnMap.isEmpty())
            return null;

        for (NPC character : charactersOnMap){
            if (isObjectNear(gameCharacter, character))
                return  character;
        }

        return null;
    }

    public Item getNearestAvailableItem(GameCharacter gameCharacter) {
        if (itemsOnMap == null || itemsOnMap.isEmpty())
            return null;

        for(Item item: itemsOnMap){
            if (isObjectNear(gameCharacter, item))
                return item;
        }
        return null;
    }

    public List<MapSpot> getNearestMapSpots(GameCharacter gameCharacter) {
        if (mapSpots == null)
            return null;

        List<MapSpot> availableMapSpots = new ArrayList<>();

        for(MapSpot mapSpot: mapSpots){
            if (isObjectNear(gameCharacter, mapSpot))
                availableMapSpots.add(mapSpot);
        }
        return availableMapSpots;
    }

    public boolean checkCollisions(int pathRectXLeft, int pathRectYUp, int pathRectXRight, int pathRectYDown, Collision collidedWith) {
        for (Collision collision : collisions) {
            if (!collision.isActive())
                continue;

            if (pathRectXLeft < collision.getWorldCoordinateX() + collision.getWidth() &&
                pathRectXRight > collision.getWorldCoordinateX() &&
                pathRectYUp < collision.getWorldCoordinateY() + collision.getHeight() &&
                pathRectYDown > collision.getWorldCoordinateY())
            {

                collidedWith.setWorldCoordinateX(collision.getWorldCoordinateX());
                collidedWith.setWorldCoordinateY(collision.getWorldCoordinateY());
                collidedWith.setWidth(collision.getWidth());
                collidedWith.setHeight(collision.getHeight());
                return true;
            }
        }
        return false;
    }

    public void translateMap(Directions direction, int offset) {
        switch (direction) {
            case UP:
                moveObjectsOnMap(0, offset);
                break;

            case DOWN:
                moveObjectsOnMap(0, -offset);
                break;

            case LEFT:
                moveObjectsOnMap(offset, 0);
                break;

            case RIGHT:
                moveObjectsOnMap(-offset, 0);
                break;

        }
    }

    private void moveObjectsOnMap(int offsetX, int offsetY) {
        for (MapSection mapSection: mapSections){
            mapSection.getGameSpriteRenderInformation().setScreenCoordinateX(mapSection.getGameSpriteRenderInformation().getScreenCoordinateX() + offsetX);
            mapSection.getGameSpriteRenderInformation().setScreenCoordinateY(mapSection.getGameSpriteRenderInformation().getScreenCoordinateY() + offsetY);
        }

        if (itemsOnMap != null) {
            for (Item item: itemsOnMap) {
                item.getGameSpriteRenderInformation().setScreenCoordinateX(item.getGameSpriteRenderInformation().getScreenCoordinateX() + offsetX);
                item.getGameSpriteRenderInformation().setScreenCoordinateY(item.getGameSpriteRenderInformation().getScreenCoordinateY() + offsetY);
            }
        }
        if (charactersOnMap != null) {
            for (GameCharacter character: charactersOnMap) {
                character.getGameSpriteRenderInformation().setScreenCoordinateX(character.getGameSpriteRenderInformation().getScreenCoordinateX() + offsetX);
                character.getGameSpriteRenderInformation().setScreenCoordinateY(character.getGameSpriteRenderInformation().getScreenCoordinateY() + offsetY);
            }
        }
        if (mapSpots != null) {
            for (MapSpot mapSpot: mapSpots) {
                mapSpot.getGameSpriteRenderInformation().setScreenCoordinateX(mapSpot.getGameSpriteRenderInformation().getScreenCoordinateX() + offsetX);
                mapSpot.getGameSpriteRenderInformation().setScreenCoordinateY(mapSpot.getGameSpriteRenderInformation().getScreenCoordinateY() + offsetY);
            }
        }
    }

    public MapInformation getMapInformation() { return mapInformation; }
    public List<Item> getItemsOnMap() { return itemsOnMap; }
    public List<NPC> getCharactersOnMap() { return charactersOnMap; }
    public List<MapSection> getMapSections() { return mapSections; }

    public void setItemsOnMap(List<Item> itemsOnMap) { this.itemsOnMap = itemsOnMap; }
    public void setCharactersOnMap(List<NPC> charactersOnMap) { this.charactersOnMap = charactersOnMap; }

    public void setMapSpots(List<MapSpot> mapSpots) {
        this.mapSpots = mapSpots;
        for (MapSpot mapSpot: mapSpots) {
            if (mapSpot.getCollision() != null)
                collisions.add(mapSpot.getCollision());
        }
    }
}
