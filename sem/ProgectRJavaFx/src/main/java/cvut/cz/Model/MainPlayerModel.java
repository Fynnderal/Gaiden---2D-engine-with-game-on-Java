package cvut.cz.Model;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.Map.Collision;
import cvut.cz.Map.MapSpot;
import cvut.cz.Map.Ventilation;
import cvut.cz.characters.*;
import cvut.cz.items.Inventory;
import cvut.cz.items.InventoryCellGeneralInformation;
import cvut.cz.items.InventoryInformation;
import cvut.cz.items.Item;


public class MainPlayerModel {

    private static MainPlayerModel instance;

    private  PlayableCharacter mainPlayer;
    private Inventory inventory;

    private String[] currentMessage;

    private boolean isInventoryActive = false;
    private boolean isInteracting = false;
    private boolean isInMenu = false;

    private NPC currentNPCInteractingWith;
    private GunSight currentGunSight;


    private MainPlayerModel() {}

    public String[] interactWithNPC() {
        NPC currentNPC = MapModel.getMapModel().getMap().getNearestNPC(mainPlayer);
        if (currentNPC == null)
            return null;

        currentNPCInteractingWith = currentNPC;
        currentMessage = currentNPC.interact();
        return currentMessage;
    }

    public void teleportMainPlayer(){
        int oldWorldX = mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX();
        int oldWorldY = mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY();

        for (MapSpot mapSpot: MapModel.getMapModel().getMap().getNearestMapSpots(mainPlayer)){
            if (mapSpot instanceof Ventilation){
                ((Ventilation) mapSpot).teleportPlayer(mainPlayer);
            }
        }

        int dy = mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() - oldWorldY;
        int dx = mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() - oldWorldX;

        if (dx > 0)
            MapModel.getMapModel().getMap().translateMap(Directions.RIGHT, dx);
        else
            MapModel.getMapModel().getMap().translateMap(Directions.LEFT, -dx);
        if (dy > 0)
            MapModel.getMapModel().getMap().translateMap(Directions.DOWN, dy);
        else
            MapModel.getMapModel().getMap().translateMap(Directions.UP, -dy);
    }


    private void translatePlayerUp(int currentSpeed, int oldY, Collision collidedWith){
        mainPlayer.move(Directions.UP, currentSpeed);

        if (MapModel.getMapModel().getMap().checkCollisions(mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX(),
                mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY(),
                mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() + mainPlayer.getGameSpriteRenderInformation().getTargetWidth(),
                oldY + mainPlayer.getGameSpriteRenderInformation().getTargetHeight(),
                collidedWith)
        ) {

            mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateY(collidedWith.getWorldCoordinateY() + collidedWith.getHeight());
            MapModel.getMapModel().getMap().translateMap(Directions.UP, oldY - mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY());
            return;
        }

        MapModel.getMapModel().getMap().translateMap(Directions.UP, currentSpeed);
    }

    private void translatePlayerDown(int currentSpeed, int oldY, Collision collidedWith){
        mainPlayer.move(Directions.DOWN, currentSpeed);

        if (MapModel.getMapModel().getMap().checkCollisions(mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX(),
                oldY,
                mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() + mainPlayer.getGameSpriteRenderInformation().getTargetWidth(),
                mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() + mainPlayer.getGameSpriteRenderInformation().getTargetHeight(),
                collidedWith)
        ) {

            mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateY(collidedWith.getWorldCoordinateY() - mainPlayer.getGameSpriteRenderInformation().getTargetHeight());
            MapModel.getMapModel().getMap().translateMap(Directions.DOWN, mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() - oldY);
            return;
        }

        MapModel.getMapModel().getMap().translateMap(Directions.DOWN, currentSpeed);
    }

    private void translatePlayerLeft(int currentSpeed, int oldX, Collision collidedWith){
        mainPlayer.move(Directions.LEFT, currentSpeed);

        if (MapModel.getMapModel().getMap().checkCollisions(mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX(),
                mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY(),
                oldX + mainPlayer.getGameSpriteRenderInformation().getTargetWidth(),
                mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() + mainPlayer.getGameSpriteRenderInformation().getTargetHeight(),
                collidedWith)
        ) {
            mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateX(collidedWith.getWorldCoordinateX() + collidedWith.getWidth());
            MapModel.getMapModel().getMap().translateMap(Directions.LEFT, oldX - mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX());
            return;
        }
        MapModel.getMapModel().getMap().translateMap(Directions.LEFT, currentSpeed);
    }

    private void translatePlayerRight(int currentSpeed, int oldX, Collision collidedWith){
        mainPlayer.move(Directions.RIGHT, currentSpeed);

        if (MapModel.getMapModel().getMap().checkCollisions(oldX,
                mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY(),
                mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() + mainPlayer.getGameSpriteRenderInformation().getTargetWidth(),
                mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() + mainPlayer.getGameSpriteRenderInformation().getTargetHeight(),
                collidedWith)
        ) {
            mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateX(collidedWith.getWorldCoordinateX() - mainPlayer.getGameSpriteRenderInformation().getTargetWidth());
            MapModel.getMapModel().getMap().translateMap(Directions.RIGHT, mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() - oldX);
            return;
        }
        MapModel.getMapModel().getMap().translateMap(Directions.RIGHT, currentSpeed);
    }

    public void movePlayer(Directions horizontalDirection, Directions verticalDirection) {
        if (mainPlayer.isBlocked())
            return;

        int oldX = mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX();
        int oldY = mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY();

        int currentSpeed = (horizontalDirection != null && verticalDirection != null) ? (int) Math.ceil(mainPlayer.getCharacterInformation().getSpeed() / 1.414) : mainPlayer.getCharacterInformation().getSpeed();

        Collision collidedWith = new Collision(0, 0, 0, 0);

        Directions[] directions = {horizontalDirection, verticalDirection};
        for (Directions direction : directions) {
            if (direction == null)
                continue;

            switch (direction) {
                case UP:
                    translatePlayerUp(currentSpeed, oldY, collidedWith);
                    break;

                case DOWN:
                    translatePlayerDown(currentSpeed, oldY, collidedWith);
                    break;

                case LEFT:
                    translatePlayerLeft(currentSpeed, oldX, collidedWith);
                    break;

                case RIGHT:
                    translatePlayerRight(currentSpeed, oldX, collidedWith);
                    break;
            }
        }
    }

    public void shoot() {
        if (inventory.getEquippedCell() == null)
            return;

        mainPlayer.shoot(inventory.getEquippedCell().getItem());
    }


    public void createInventory(InventoryInformation inventoryInformation, InventoryCellGeneralInformation inventoryCellGeneralInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        inventory = new Inventory(inventoryInformation, inventoryCellGeneralInformation, gameSpriteSourceInformation, gameSpriteRenderInformation);
    }

    public void takeItem(){
        if (mainPlayer.isBlocked())
            return;

        Item item = MapModel.getMapModel().getMap().getNearestAvailableItem(mainPlayer);
        inventory.addItemToInventory(item);
        MapModel.getMapModel().getMap().getItemsOnMap().remove(item);
        MapModel.getMapModel().getDrawableObjects().remove(item);
    }


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
    public GunSight getCurrentGunSight() { return currentGunSight;}


    public void setMainPlayer(PlayableCharacter mainPlayer){
        this.mainPlayer = mainPlayer;
        this.mainPlayer.setMap(MapModel.getMapModel().getMap());
        this.mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateX(mainPlayer.getGameSpriteRenderInformation().getScreenCoordinateX() - MapModel.getMapModel().getMap().getMapInformation().mapCoordinateX());
        this.mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateY(mainPlayer.getGameSpriteRenderInformation().getScreenCoordinateY() - MapModel.getMapModel().getMap().getMapInformation().mapCoordinateY());
        MapModel.getMapModel().getDrawableObjects().add(mainPlayer);
        MapModel.getMapModel().getUpdatableObjects().add(mainPlayer);
    }

    public  boolean isInMenu() { return isInMenu; }


    public  void setIsInventoryActive(boolean isInventoryActive) {this.isInventoryActive = isInventoryActive;}
    public  void setIsInteracting(boolean isInteracting) {this.isInteracting = isInteracting;}
    public  void setCurrentGunSight(GunSight gunSight) { currentGunSight = gunSight; }
    public  void setIsInMenu(boolean isInMenu) { this.isInMenu = isInMenu;}
}
