package cvut.cz.Map;

import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.characters.Directions;
import cvut.cz.characters.GameCharacter;
import cvut.cz.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private final List<Collision> collisions;
    private List<Item> itemsOnMap;
    private List<GameCharacter> charactersOnMap;
    private List<MapSpot> mapSpots;
    private List<MapSection> mapSections;
    private MapInformation mapInformation;

    public Map(List<Collision> collisions, List<MapSection> mapSections, MapInformation mapInformation)
    {
        this.mapInformation = mapInformation;
        this.mapSections = mapSections;
        this.collisions = collisions;
    }

    public Item checkNearestAvailableItems(GameCharacter gameCharacter) {
        int gameCharacterX = gameCharacter.getGameSpriteRenderInformation().getScreenCoordinateX();
        int gameCharacterY = gameCharacter.getGameSpriteRenderInformation().getScreenCoordinateY();
        int gameCharacterWidth = gameCharacter.getGameSpriteRenderInformation().getTargetWidth();
        int gameCharacterHeight = gameCharacter.getGameSpriteRenderInformation().getTargetHeight();

        int currentItemX, currentItemY, currentItemWidth, currentItemHeight;
        for(Item item: itemsOnMap){
            currentItemX = item.getGameSpriteRenderInformation().getScreenCoordinateX();
            currentItemY = item.getGameSpriteRenderInformation().getScreenCoordinateY();
            currentItemWidth = item.getGameSpriteRenderInformation().getTargetWidth();
            currentItemHeight = item.getGameSpriteRenderInformation().getTargetHeight();

            if (gameCharacterX <= currentItemX + currentItemWidth + 5 &&
                gameCharacterX + gameCharacterWidth >= currentItemX - 5 &&
                gameCharacterY <= currentItemHeight + currentItemY + 5 &&
                gameCharacterY + gameCharacterHeight >= currentItemHeight - 5)
            {

                return item;
            }
        }
        return null;
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

    public List<MapSpot> checkNearestMapSpots(GameCharacter gameCharacter) {
        if (mapSpots == null)
            return null;

        List<MapSpot> availableMapSpots = new ArrayList<>();

        int gameCharacterX = gameCharacter.getGameSpriteRenderInformation().getScreenCoordinateX();
        int gameCharacterY = gameCharacter.getGameSpriteRenderInformation().getScreenCoordinateY();
        int gameCharacterWidth = gameCharacter.getGameSpriteRenderInformation().getTargetWidth();
        int gameCharacterHeight = gameCharacter.getGameSpriteRenderInformation().getTargetHeight();

        int currentItemX, currentItemY, currentItemWidth, currentItemHeight;
        for(MapSpot mapSpot: mapSpots){
            currentItemX = mapSpot.getGameSpriteRenderInformation().getScreenCoordinateX();
            currentItemY = mapSpot.getGameSpriteRenderInformation().getScreenCoordinateY();
            currentItemWidth = mapSpot.getGameSpriteRenderInformation().getTargetWidth();
            currentItemHeight = mapSpot.getGameSpriteRenderInformation().getTargetHeight();

            if (gameCharacterX <= currentItemX + currentItemWidth + 5 &&
                gameCharacterX + gameCharacterWidth >= currentItemX - 5 &&
                gameCharacterY <= currentItemHeight + currentItemY + 5 &&
                gameCharacterY + gameCharacterHeight >= currentItemHeight - 5)
            {
                availableMapSpots.add(mapSpot);
            }
        }
        return availableMapSpots;
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

    private void moveSections(int offsetX, int offsetY) {

    }

    public void addCollision(Collision collision) { collisions.add(collision);}

    public MapInformation getMapInformation() { return mapInformation; }
    public List<Collision> getCollisions() { return collisions; }
    public List<GameCharacter> getCharactersOnMap() { return charactersOnMap; }
    public List<Item> getItemsOnMap() { return itemsOnMap; }

    public void setItemsOnMap(List<Item> itemsOnMap) { this.itemsOnMap = itemsOnMap; }
    public void setCharactersOnMap(List<GameCharacter> charactersOnMap) { this.charactersOnMap = charactersOnMap; }

    public void setMapSpots(List<MapSpot> mapSpots) {
        this.mapSpots = mapSpots;
        for (MapSpot mapSpot: mapSpots) {
            if (mapSpot.getCollision() != null)
                collisions.add(mapSpot.getCollision());
        }
    }
}
