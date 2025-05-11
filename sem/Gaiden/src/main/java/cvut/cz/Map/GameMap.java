package cvut.cz.Map;


import cvut.cz.GameSprite.GameSprite;
import cvut.cz.characters.Directions;
import cvut.cz.characters.GameCharacter;
import cvut.cz.characters.NPC;
import cvut.cz.items.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game map, including its sections, items, characters, and other elements.
 * Provides methods for managing and interacting with the map's components.
 */
public class GameMap {

    private final List<Collision> collisions;

    // List of items that are displayed on the map
    private List<Item> itemsOnMap;
    // List of characters that are displayed on the map
    private List<NPC> charactersOnMap;

    // List of doors, ventilation and other points of interest on the map.
    private List<MapSpot> mapSpots;

    // List of sections in which map is divided
    private final List<MapSection> mapSections;
    // General information about the map
    private final MapInformation mapInformation;

    /**
     * Constructs a GameMap object with the specified collisions, map sections, and map information.
     *
     * @param collisions List of collision objects on the map.
     * @param mapSections List of sections into which the map is divided.
     * @param mapInformation General information about the map.
     */
    public GameMap(List<Collision> collisions, List<MapSection> mapSections, MapInformation mapInformation)
    {
        this.mapInformation = mapInformation;
        this.mapSections = mapSections;
        this.collisions = collisions;
    }

    /**
     * Checks if a game object is near a game character on the map.
     *
     * @param gameCharacter The game character to check proximity for.
     * @param object The game object to check proximity against.
     * @return True if the objects are near each other, false otherwise.
     */
    private boolean isObjectNear(GameCharacter gameCharacter, GameSprite object) {
        int gameCharacterX = gameCharacter.getGameSpriteRenderInformation().getScreenCoordinateX();
        int gameCharacterY = gameCharacter.getGameSpriteRenderInformation().getScreenCoordinateY();
        int gameCharacterWidth = gameCharacter.getCollisionWidth();
        int gameCharacterHeight = gameCharacter.getCollisionHeight();

        int currentObjectX = object.getGameSpriteRenderInformation().getScreenCoordinateX();
        int currentObjectY = object.getGameSpriteRenderInformation().getScreenCoordinateY();
        int currentObjectWidth = object.getGameSpriteRenderInformation().getTargetWidth();
        int currentObjectHeight = object.getGameSpriteRenderInformation().getTargetHeight();

        // Object is near if the distance between them is less than 15 pixels
        return  (gameCharacterX <= currentObjectX + currentObjectWidth + 15 &&
                gameCharacterX + gameCharacterWidth >= currentObjectX - 15 &&
                gameCharacterY <= currentObjectHeight + currentObjectY + 15 &&
                gameCharacterY + gameCharacterHeight >= currentObjectY - 15);
    }

    /**
     * Gets the nearest NPC to the specified game character.
     *
     * @param gameCharacter The game character to check proximity for.
     * @return The nearest NPC if found, null otherwise.
     */
    public NPC getNearestNPC(GameCharacter gameCharacter) {
        if (charactersOnMap == null || charactersOnMap.isEmpty())
            return null;

        for (NPC character : charactersOnMap){
            if (isObjectNear(gameCharacter, character))
                return  character;
        }

        return null;
    }

     /**
     * Gets the nearest available item to the specified game character.
     *
     * @param gameCharacter The game character to check proximity for.
     * @return The nearest available item if found, null otherwise.
     */
    public Item getNearestAvailableItem(GameCharacter gameCharacter) {
        if (itemsOnMap == null || itemsOnMap.isEmpty())
            return null;

        for(Item item: itemsOnMap){
            if (isObjectNear(gameCharacter, item))
                return item;
        }
        return null;
    }

    /**
     * Gets the nearest map spots to the specified game character.
     *
     * @param gameCharacter The game character to check proximity for.
     * @return A list of nearby map spots.
     */
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

    /**
     * Checks for collisions between a path and collision objects on the map.
     *
     * @param pathRectXLeft The left X-coordinate of the path rectangle.
     * @param pathRectYUp The upper Y-coordinate of the path rectangle.
     * @param pathRectXRight The right X-coordinate of the path rectangle.
     * @param pathRectYDown The lower Y-coordinate of the path rectangle.
     * @return List of collision with which collide was detected.
     */
    public List<Collision> checkCollisions(int pathRectXLeft, int pathRectYUp, int pathRectXRight, int pathRectYDown) {
        List<Collision> collided = new ArrayList<>();

        for (Collision collision : collisions) {
            if (!collision.isActive())
                continue;

            // Object collided if path of the object intersects with the collision rectangle
            if (pathRectXLeft < collision.getWorldCoordinateX() + collision.getWidth() &&
                pathRectXRight > collision.getWorldCoordinateX() &&
                pathRectYUp < collision.getWorldCoordinateY() + collision.getHeight() &&
                pathRectYDown > collision.getWorldCoordinateY())
            {
                collided.add(collision);
            }
        }
        return collided;
    }

    /**
     * Translates the map in the specified direction by the given offset.
     *
     * @param direction The direction to translate the map.
     * @param offset The offset value for translation.
     */
    public void translateMap(Directions direction, int offset) {
        switch (direction) {
            case UP:
                moveObjectsOnMap(0, -offset);
                break;

            case DOWN:
                moveObjectsOnMap(0, offset);
                break;

            case LEFT:
                moveObjectsOnMap(-offset, 0);
                break;

            case RIGHT:
                moveObjectsOnMap(offset, 0);
                break;

        }
    }

    /**
     * Moves all objects on the map by the specified offsets.
     *
     * @param offsetX The offset in the X direction.
     * @param offsetY The offset in the Y direction.
     */
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

    /**
     * Gets the list of collisions on the map.
     *
     * @return The list of collisions.
     */
    public List<Collision> getCollisions() { return collisions; }

    /**
     * Gets the information about the map.
     *
     * @return The map information.
     */
    public MapInformation getMapInformation() { return mapInformation; }

    /**
     * Gets the list of items displayed on the map.
     *
     * @return The list of items on the map.
     */
    public List<Item> getItemsOnMap() { return itemsOnMap; }

    /**
     * Gets the list of characters displayed on the map.
     *
     * @return The list of characters on the map.
     */
    public List<NPC> getCharactersOnMap() { return charactersOnMap; }

    /**
     * Gets the list of sections into which the map is divided.
     *
     * @return The list of map sections.
     */
    public List<MapSection> getMapSections() { return mapSections; }

    /**
     * Sets the list of items displayed on the map.
     *
     * @param itemsOnMap The list of items to set.
     */
    public void setItemsOnMap(List<Item> itemsOnMap) { this.itemsOnMap = itemsOnMap; }

    /**
     * Sets the list of characters displayed on the map.
     *
     * @param charactersOnMap The list of characters to set.
     */
    public void setCharactersOnMap(List<NPC> charactersOnMap) { this.charactersOnMap = charactersOnMap; }

    /**
     * Sets the list of map spots and updates their collisions.
     *
     * @param mapSpots The list of map spots to set.
     */
    public void setMapSpots(List<MapSpot> mapSpots) {
        this.mapSpots = mapSpots;
        for (MapSpot mapSpot: mapSpots) {
            if (mapSpot.getCollision() != null)
                collisions.add(mapSpot.getCollision());
        }
    }
}
