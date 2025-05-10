package cvut.cz.Model;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.Map.Collision;
import cvut.cz.characters.*;
import cvut.cz.items.Inventory;
import cvut.cz.items.InventoryCellGeneralInformation;
import cvut.cz.items.InventoryInformation;
import cvut.cz.items.Item;

import java.util.List;

/**
 * Represents the main player model in the game.
 * This class manages the player's character, inventory, interactions, and movement.
 */
public class MainPlayerModel {

    private static MainPlayerModel instance;

    private  PlayableCharacter mainPlayer;
    private Inventory inventory;

    // The current message in dialogue to be displayed
    private String[] currentMessage;


    private boolean isInventoryActive = false;

    private boolean isInteracting = false;

    private boolean isInMenu = false;

    private NPC currentNPCInteractingWith;
    private CrossHair currentCrossHair;

    private String pathToItems;

    /** Private constructor to enforce the singleton pattern. */
    private MainPlayerModel() {}

    /**
     * Interacts with the nearest NPC and retrieves the dialogue message.
     *
     * @return The dialogue message from the NPC, or null if no NPC is nearby.
     */
    public String[] interactWithNPC() {
        NPC currentNPC = MapModel.getMapModel().getMap().getNearestNPC(mainPlayer);
        if (currentNPC == null)
            return null;

        currentNPCInteractingWith = currentNPC;
        currentMessage = currentNPC.interact();
        return currentMessage;
    }

    /**
     * Translates the player upwards by the specified speed, handling collisions and map translation.
     *
     * @param currentSpeed The speed at which the player is moving upwards.
     * @param oldY The previous Y-coordinate of the player before movement.
     */
    private void translatePlayerUp(int currentSpeed, int oldY){
        mainPlayer.move(Directions.UP, currentSpeed);

        //Gets all the collisions that the player has with the map
        List<Collision> collisions = MapModel.getMapModel().getMap().checkCollisions(
                mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX(),
                mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY(),
                mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() + mainPlayer.getCollisionWidth(),
                oldY + mainPlayer.getCollisionHeight()
                );

        if (!collisions.isEmpty()) {
            // Finds the nearest collision
            Collision firstCollided = collisions.getFirst();
            for (Collision collision : collisions){
                if (firstCollided.getWorldCoordinateY() + firstCollided.getHeight() < collision.getWorldCoordinateY() + collision.getHeight())
                    firstCollided = collision;
            }

            mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateY(firstCollided.getWorldCoordinateY() + firstCollided.getHeight());
            MapModel.getMapModel().getMap().translateMap(Directions.DOWN, oldY - mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY());
            return;
        }

        MapModel.getMapModel().getMap().translateMap(Directions.DOWN, currentSpeed);
    }

    /**
     * Translates the player downwards by the specified speed, handling collisions and map translation.
     *
     * @param currentSpeed The speed at which the player is moving downwards.
     * @param oldY The previous Y-coordinate of the player before movement.
     */
    private void translatePlayerDown(int currentSpeed, int oldY){
        mainPlayer.move(Directions.DOWN, currentSpeed);

        // Gets all the collisions that the player has with the map
        List<Collision> collisions = MapModel.getMapModel().getMap().checkCollisions(
                                    mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX(),
                                    oldY,
                                    mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() + mainPlayer.getCollisionWidth(),
                                    mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() + mainPlayer.getCollisionHeight()
                                    );
        if (!collisions.isEmpty()) {
            // Finds the nearest collision
            Collision firstCollided = collisions.getFirst();
            for (Collision collision : collisions){
                if (firstCollided.getWorldCoordinateY() > collision.getWorldCoordinateY())
                    firstCollided = collision;
            }

            mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateY(firstCollided.getWorldCoordinateY() - mainPlayer.getCollisionHeight());
            MapModel.getMapModel().getMap().translateMap(Directions.UP, mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() - oldY);
            return;
        }

        MapModel.getMapModel().getMap().translateMap(Directions.UP, currentSpeed);
    }

    /**
     * Translates the player to the left by the specified speed, handling collisions and map translation.
     *
     * @param currentSpeed The speed at which the player is moving to the left.
     * @param oldX The previous X-coordinate of the player before movement.
     */
    private void translatePlayerLeft(int currentSpeed, int oldX){
        mainPlayer.move(Directions.LEFT, currentSpeed);

        // Gets all the collisions that the player has with the map
        List<Collision> collisions = MapModel.getMapModel().getMap().checkCollisions(
                mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX(),
                mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY(),
                oldX + mainPlayer.getCollisionWidth(),
                mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() + mainPlayer.getCollisionHeight()
                );

        if (!collisions.isEmpty()) {
            // Finds the nearest collision
            Collision firstCollided = collisions.getFirst();
            for (Collision collision : collisions){
                if (firstCollided.getWorldCoordinateX() + firstCollided.getWidth() < collision.getWorldCoordinateX() + collision.getWidth())
                    firstCollided = collision;
            }

            mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateX(firstCollided.getWorldCoordinateX() + firstCollided.getWidth());
            MapModel.getMapModel().getMap().translateMap(Directions.RIGHT, oldX - mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX());
            return;
        }

        MapModel.getMapModel().getMap().translateMap(Directions.RIGHT, currentSpeed);
    }

    /**
     * Translates the player to the right by the specified speed, handling collisions and map translation.
     *
     * @param currentSpeed The speed at which the player is moving to the right.
     * @param oldX The previous X-coordinate of the player before movement.
     */
    private void translatePlayerRight(int currentSpeed, int oldX){
        mainPlayer.move(Directions.RIGHT, currentSpeed);

        // Gets all the collisions that the player has with the map
        List<Collision> collisions =  MapModel.getMapModel().getMap().checkCollisions(
                                        oldX,
                                        mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY(),
                                        mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() + mainPlayer.getCollisionWidth(),
                                        mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() + mainPlayer.getCollisionHeight()
                                        );
        if (!collisions.isEmpty()) {
            Collision firstCollided = collisions.getFirst();
            // Finds the nearest collision
            for (Collision collision : collisions){
                if (firstCollided.getWorldCoordinateX() > collision.getWorldCoordinateX())
                    firstCollided = collision;
            }

            mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateX(firstCollided.getWorldCoordinateX() - mainPlayer.getCollisionWidth());
            MapModel.getMapModel().getMap().translateMap(Directions.LEFT, mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() - oldX);
            return;
        }
        MapModel.getMapModel().getMap().translateMap(Directions.LEFT, currentSpeed);
    }


    /**
     * Moves the player in the specified directions, handling collisions and map translation.
     *
     * @param horizontalDirection The horizontal direction to move (LEFT or RIGHT).
     * @param verticalDirection The vertical direction to move (UP or DOWN).
     */
    public void movePlayer(Directions horizontalDirection, Directions verticalDirection) {
        if (mainPlayer.isBlocked())
            return;

        // Saves the old coordinates of the player to construct the path
        int oldX = mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX();
        int oldY = mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY();

        // If the player is moving diagonally, we need to divide the speed by sqrt(2) to keep the same speed
        int currentSpeed = (horizontalDirection != null && verticalDirection != null) ? (int) Math.ceil(mainPlayer.getCharacterInformation().getSpeed() / 1.414) : mainPlayer.getCharacterInformation().getSpeed();

        Directions[] directions = {horizontalDirection, verticalDirection};
        for (Directions direction : directions) {
            if (direction == null)
                continue;

            switch (direction) {
                case UP:
                    translatePlayerUp(currentSpeed, oldY);
                    break;

                case DOWN:
                    translatePlayerDown(currentSpeed, oldY);
                    break;

                case LEFT:
                    translatePlayerLeft(currentSpeed, oldX);
                    break;

                case RIGHT:
                    translatePlayerRight(currentSpeed, oldX);
                    break;
            }
        }
    }

    /**
     * Shoots using the currently equipped weapon from the player's inventory.
     * Does nothing if no item is equipped.
     */
    public void shoot() {
        if (inventory.getEquippedCell() == null)
            return;

        mainPlayer.shoot(inventory.getEquippedCell().getItem());
    }

    /**
     * Creates the player's inventory with the specified parameters.
     *
     * @param inventoryInformation Information about the inventory.
     * @param inventoryCellGeneralInformation General information about inventory cells.
     * @param gameSpriteSourceInformation Source information for the inventory's sprite.
     * @param gameSpriteRenderInformation Render information for the inventory's sprite.
     */
    public void createInventory(InventoryInformation inventoryInformation, InventoryCellGeneralInformation inventoryCellGeneralInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        inventory = new Inventory(inventoryInformation, inventoryCellGeneralInformation, gameSpriteSourceInformation, gameSpriteRenderInformation);
    }

    /**
     * Picks up the nearest item and adds it to the player's inventory.
     * Removes the item from the map and drawable objects.
     */
    public void takeItem(){
        if (mainPlayer.isBlocked())
            return;

        Item item = MapModel.getMapModel().getMap().getNearestAvailableItem(mainPlayer);
        inventory.addItemToInventory(item);
        MapModel.getMapModel().getMap().getItemsOnMap().remove(item);
        MapModel.getMapModel().getDrawableObjects().remove(item);
    }

    /**
     * Retrieves the singleton instance of the MainPlayerModel.
     *
     * @return The singleton instance of MainPlayerModel.
     */
    public static MainPlayerModel getMainPlayerModel() {
        if (instance == null)
            instance = new MainPlayerModel();
        return instance;
    }

    public  Inventory getInventory() { return inventory; }
    public  boolean isInventoryActive() { return isInventoryActive; }
    public  boolean isInteracting() { return isInteracting; }
    public  String[] getCurrentMessage() { return currentMessage; }
    public  PlayableCharacter getMainPlayer() { return mainPlayer; }
    public  NPC getCurrentNPCInteractingWith() { return currentNPCInteractingWith; }
    public CrossHair getCurrentGunSight() { return currentCrossHair;}
    public String getPathToItems() { return pathToItems; }

    public void setPathToItems(String pathToItems) { this.pathToItems = pathToItems; }
    public void setMainPlayer(PlayableCharacter mainPlayer){
        this.mainPlayer = mainPlayer;
        this.mainPlayer.setMap(MapModel.getMapModel().getMap());

        //Sets the main player's world coordinates based on the screen coordinates of the player and the coordinates of the map.
        this.mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateX(mainPlayer.getGameSpriteRenderInformation().getScreenCoordinateX() - MapModel.getMapModel().getMap().getMapInformation().mapCoordinateX());
        this.mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateY(mainPlayer.getGameSpriteRenderInformation().getScreenCoordinateY() - MapModel.getMapModel().getMap().getMapInformation().mapCoordinateY());

        MapModel.getMapModel().getDrawableObjects().add(mainPlayer);
        MapModel.getMapModel().getUpdatableObjects().add(mainPlayer);
    }

    public  boolean isInMenu() { return isInMenu; }


    public  void setIsInventoryActive(boolean isInventoryActive) {this.isInventoryActive = isInventoryActive;}
    public  void setIsInteracting(boolean isInteracting) {this.isInteracting = isInteracting;}
    public  void setCurrentGunSight(CrossHair crossHair) { currentCrossHair = crossHair; }
    public  void setIsInMenu(boolean isInMenu) { this.isInMenu = isInMenu;}
}
