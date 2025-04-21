package cvut.cz.Model;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.Map.Collision;
import cvut.cz.Map.Map;
import cvut.cz.characters.*;
import cvut.cz.items.Inventory;
import cvut.cz.items.InventoryInformation;
import cvut.cz.items.Item;

import java.net.URL;
import java.util.HashMap;

class MainPlayerModel {

    private static MainPlayerModel instance;

    private static PlayableCharacter mainPlayer;
    private static Inventory inventory;


    private MainPlayerModel() {}

    PlayableCharacter createMainPlayer(URL pathToItems, CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation, Map map){
        mainPlayer = new OfficeWorker(pathToItems, characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation, map);
        mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateX(mainPlayer.getGameSpriteRenderInformation().getScreenCoordinateX() - Model.getMap().getMapInformation().mapCoordinateX());
        mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateY(mainPlayer.getGameSpriteRenderInformation().getScreenCoordinateY() - Model.getMap().getMapInformation().mapCoordinateY());
        Model.getDrawableObjects().add(mainPlayer);
        Model.getUpdatableObjects().add(mainPlayer);
        return mainPlayer;
    }

    void movePlayer(Directions direction) {
        int oldX = mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX();
        int oldY = mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY();

        Collision collidedWith = new Collision(0, 0, 0, 0);
        switch (direction) {
            case UP:
                mainPlayer.move(Directions.UP);

                if (Model.getMap().checkCollisions(mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX(),
                        mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY(),
                        mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() + mainPlayer.getGameSpriteRenderInformation().getTargetWidth(),
                        oldY + mainPlayer.getGameSpriteRenderInformation().getTargetHeight(),
                        collidedWith)
                ) {

                    mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateY(collidedWith.getWorldCoordinateY() + collidedWith.getHeight());
                    Model.getMap().translateMap(Directions.UP, oldY - mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY());
                    break;
                }

                Model.getMap().translateMap(Directions.UP, mainPlayer.getCharacterInformation().getSpeed());
                break;

            case DOWN:
                mainPlayer.move(Directions.DOWN);

                if (Model.getMap().checkCollisions(mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX(),
                        oldY,
                        mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() + mainPlayer.getGameSpriteRenderInformation().getTargetWidth(),
                        mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() + mainPlayer.getGameSpriteRenderInformation().getTargetHeight(),
                        collidedWith)
                ) {

                    mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateY(collidedWith.getWorldCoordinateY() - mainPlayer.getGameSpriteRenderInformation().getTargetHeight());
                    Model.getMap().translateMap(Directions.DOWN, mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() - oldY);

                    break;
                }

                Model.getMap().translateMap(Directions.DOWN, mainPlayer.getCharacterInformation().getSpeed());
                break;

            case LEFT:
                mainPlayer.move(Directions.LEFT);

                if (Model.getMap().checkCollisions(mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX(),
                        mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY(),
                        oldX + mainPlayer.getGameSpriteRenderInformation().getTargetWidth(),
                        mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() + mainPlayer.getGameSpriteRenderInformation().getTargetHeight(),
                        collidedWith)
                ) {
                    mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateX(collidedWith.getWorldCoordinateX() + collidedWith.getWidth());
                    Model.getMap().translateMap(Directions.LEFT, oldX - mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX());
                    break;
                }
                Model.getMap().translateMap(Directions.LEFT, mainPlayer.getCharacterInformation().getSpeed());
                break;

            case RIGHT:

                mainPlayer.move(Directions.RIGHT);

                if (Model.getMap().checkCollisions(oldX,
                        mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY(),
                        mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() + mainPlayer.getGameSpriteRenderInformation().getTargetWidth(),
                        mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateY() + mainPlayer.getGameSpriteRenderInformation().getTargetHeight(),
                        collidedWith)
                ) {
                    mainPlayer.getGameSpriteRenderInformation().setWorldCoordinateX(collidedWith.getWorldCoordinateX() - mainPlayer.getGameSpriteRenderInformation().getTargetWidth());
                    Model.getMap().translateMap(Directions.RIGHT, mainPlayer.getGameSpriteRenderInformation().getWorldCoordinateX() - oldX);
                    break;
                }
                Model.getMap().translateMap(Directions.RIGHT, mainPlayer.getCharacterInformation().getSpeed());
                break;
        }
    }

    void shoot() {
        if (inventory.getEquippedCell() == null)
            return;

        mainPlayer.shoot(inventory.getEquippedCell().getItem(), Model.getCharactersInWorld());
    }

    Inventory createInventory(InventoryInformation inventoryInformation, PlayableCharacter character, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        inventory = new Inventory(inventoryInformation, character, gameSpriteSourceInformation, gameSpriteRenderInformation);
        return inventory;
    }

    void takeItem(){
        Item item = Model.getMap().checkNearestAvailableItems(mainPlayer);
        inventory.addItemToInventory(item);
        Model.getMap().getItemsOnMap().remove(item);
        Model.getDrawableObjects().remove(item);
    }

    static MainPlayerModel getMainPlayerModel() {
        if (instance == null)
            instance = new MainPlayerModel();
        return instance;
    }
}
