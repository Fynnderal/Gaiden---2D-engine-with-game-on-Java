package cvut.cz;

import cvut.cz.Model.MainPlayerModel;
import cvut.cz.Model.MapModel;
import cvut.cz.Model.Updatable;
import cvut.cz.characters.Directions;

import cvut.cz.characters.CrossHair;
import cvut.cz.items.InventoryCell;
import cvut.cz.items.ItemInformation;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.FileWriter;
import java.util.HashSet;

import java.util.Set;
import java.util.logging.Logger;


/**
 * Manages the logic and controls of the game, including player interactions,
 * inventory management, and movement.
 */
public class LogicManager {
    private static final Logger logger = Logger.getLogger(LogicManager.class.getName());

    private final MainApplication mainApp;

    // True if the player is selecting a cell to combine with another cell
    private boolean selectingCell;
    // The cell that the player is selecting to combine with another cell
    private static InventoryCell selectedCell;

    // The x and y coordinates of the mouse
    private int MouseX;
    private int MouseY;

    // The set of currently pressed keys
    private static Set<KeyCode> pressedKeys;

    /**
     * Constructs a LogicManager with the specified main application instance.
     *
     * @param application The main application instance.
     */
    public LogicManager(MainApplication application) {
        mainApp = application;
        pressedKeys = new HashSet<>();
        selectingCell = false;
        setControls();
    }

    /**
     * Handles aiming logic when the right mouse button is pressed.
     *
     * @param e The mouse event triggered by pressing the right mouse button.
     */
    private void aim(MouseEvent e){
        if (MainPlayerModel.getMainPlayerModel().getInventory().getEquippedCell() != null &&
                MainPlayerModel.getMainPlayerModel().getMainPlayer().getWeaponsAndCrossHairs() != null) {

            ItemInformation currentWeapon = MainPlayerModel.getMainPlayerModel().getInventory().getEquippedCell().getItem().getItemInformation();
            if (currentWeapon.name().equals("pistol") || currentWeapon.name().equals("shotgun")) {
                MouseX = (int) e.getSceneX();
                MouseY = (int) e.getSceneY();
                MainPlayerModel.getMainPlayerModel().getMainPlayer().isAiming = true;
                CrossHair currentCrossHair = MainPlayerModel.getMainPlayerModel().getMainPlayer().getWeaponsAndCrossHairs().get(currentWeapon.name());
                MainPlayerModel.getMainPlayerModel().setCurrentGunSight(currentCrossHair);
            }
        }
    }

    /**
     * Sets up mouse controls for the game
     */
    private void setMouseControls(){
        mainApp.getStage().getScene().setOnMouseDragged(e -> {
            MouseX = (int) e.getSceneX();
            MouseY = (int) e.getSceneY();
        });

        mainApp.getStage().getScene().setOnMousePressed(e -> {
            if (MainPlayerModel.getMainPlayerModel().isInMenu())
                return;
            if (MainPlayerModel.getMainPlayerModel().isInteracting() || MainPlayerModel.getMainPlayerModel().isInventoryActive())
                return;

            switch (e.getButton()) {
                case MouseButton.PRIMARY:
                    MainPlayerModel.getMainPlayerModel().shoot();
                    break;
                case MouseButton.SECONDARY:
                    aim(e);
                    break;
            }
        });

        mainApp.getStage().getScene().setOnMouseReleased(e -> {
            if (MainPlayerModel.getMainPlayerModel().isInMenu())
                return;

            if (e.getButton() == MouseButton.SECONDARY) {
                MainPlayerModel.getMainPlayerModel().getMainPlayer().isAiming = false;
                MainPlayerModel.getMainPlayerModel().setCurrentGunSight(null);
            }
        });
    }

    /**
     * Handles interaction with NPCs when specific keys are pressed.
     *
     * @param e The key event triggered by pressing a key.
     */
    private void interactWithNPC(KeyEvent e){
        if (MainPlayerModel.getMainPlayerModel().isInteracting()) {
            if (e.getCode() == KeyCode.W)
                MainPlayerModel.getMainPlayerModel().getCurrentNPCInteractingWith().changePlayersAnswer(Directions.UP);
            else if (e.getCode() == KeyCode.S)
                MainPlayerModel.getMainPlayerModel().getCurrentNPCInteractingWith().changePlayersAnswer(Directions.DOWN);
        }
    }
    /**
     * Handles the logic for combining items in the inventory.
     */
    private void controlCombiningItems(){
        if (selectingCell){
            MainPlayerModel.getMainPlayerModel().getInventory().getInventoryController().combineCells(selectedCell, MainPlayerModel.getMainPlayerModel().getInventory().getSelectedInventoryCell());
            selectedCell = null;
            selectingCell = false;
        }else {
            selectedCell = MainPlayerModel.getMainPlayerModel().getInventory().getSelectedInventoryCell();
            selectingCell = true;
        }
    }

    /**
     * Handles inventory controls based on key events.
     *
     * @param e The key event triggered by pressing a key.
     */
    private void controlInventory(KeyEvent e){
        switch (e.getCode()) {
            case KeyCode.D:
                moveInventoryPointer(Directions.RIGHT);
                break;
            case KeyCode.A:
                moveInventoryPointer(Directions.LEFT);
                break;
            case KeyCode.W:
                moveInventoryPointer(Directions.UP);
                break;
            case KeyCode.S:
                moveInventoryPointer(Directions.DOWN);
                break;
            case KeyCode.T:
                controlCombiningItems();
                break;
            case KeyCode.Q:
                if (selectingCell) {
                    selectingCell = false;
                    selectedCell = null;
                }
                break;
            case KeyCode.R:
                if (selectingCell)
                    break;

                MainPlayerModel.getMainPlayerModel().getInventory().getInventoryController().equipCell(MainPlayerModel.getMainPlayerModel().getInventory().getSelectedInventoryCell());
                break;
            case KeyCode.F:
                if (selectingCell)
                    break;

                MainPlayerModel.getMainPlayerModel().getInventory().getInventoryController().useCell(MainPlayerModel.getMainPlayerModel().getMainPlayer());
                break;
            case KeyCode.K:
                if (selectingCell)
                    break;

                MainPlayerModel.getMainPlayerModel().getInventory().getInventoryController().discardCell(MainPlayerModel.getMainPlayerModel().getInventory().getSelectedInventoryCell());
                break;
        }
    }

    /**
     * Handles player controls based on key events.
     *
     * @param e The key event triggered by pressing a key.
     */
    private void controlPlayer(KeyEvent e){
        if (e.getCode() == KeyCode.E)
            MainPlayerModel.getMainPlayerModel().takeItem();
        else if (e.getCode() == KeyCode.ENTER){
            System.out.println("X: " + MainPlayerModel.getMainPlayerModel().getMainPlayer().getGameSpriteRenderInformation().getWorldCoordinateX());
            System.out.println("Y: " + MainPlayerModel.getMainPlayerModel().getMainPlayer().getGameSpriteRenderInformation().getWorldCoordinateY());
            System.out.println("=====================================================================");
        }
        else
            pressedKeys.add(e.getCode());
    }

    /**
     * Sets up key controls for the game, including handling key presses and releases.
     */
    private void setKeyControls(){
        mainApp.getStage().getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                MainPlayerModel.getMainPlayerModel().setIsInMenu(!MainPlayerModel.getMainPlayerModel().isInMenu());
            }

            if (MainPlayerModel.getMainPlayerModel().isInMenu())
                return;

            // Opens inventory
            if (!MainPlayerModel.getMainPlayerModel().isInteracting() && e.getCode() == KeyCode.TAB &&
                MainPlayerModel.getMainPlayerModel().getInventory() != null) {

                MainPlayerModel.getMainPlayerModel().setIsInventoryActive(!MainPlayerModel.getMainPlayerModel().isInventoryActive());
                MainPlayerModel.getMainPlayerModel().getMainPlayer().isAiming = false;
                selectingCell = false;
                selectedCell = null;
                pressedKeys.clear();
                mainApp.getGraphicsContexts().get(1).clearRect(0, 0, mainApp.getScreenWidth(), mainApp.getScreenHeight());
            }

            interactWithNPC(e);

            if (MainPlayerModel.getMainPlayerModel().isInventoryActive()) {
                controlInventory(e);
            }
            else{
                if (e.getCode() == KeyCode.F) {
                    MainPlayerModel.getMainPlayerModel().setIsInteracting(MainPlayerModel.getMainPlayerModel().interactWithNPC() != null);
                    if (MainPlayerModel.getMainPlayerModel().isInteracting())
                        pressedKeys.clear();
                }
                else if (!MainPlayerModel.getMainPlayerModel().isInteracting()) {
                    controlPlayer(e);
                }
            }
        });
        mainApp.getStage().getScene().setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
    }

    /**
     * Sets up all controls for the game, including mouse and key controls.
     */
    private void setControls(){
        setMouseControls();
        setKeyControls();
    }

    /**
     * Moves the inventory pointer in the specified direction.
     *
     * @param direction The direction to move the pointer.
     */
    private void moveInventoryPointer(Directions direction) {
        MainPlayerModel.getMainPlayerModel().getInventory().getInventoryController().movePointer(direction);
    }

    /**
     * Moves the player in the specified horizontal and vertical directions.
     *
     * @param horizontalDirection The horizontal direction to move the player.
     * @param verticalDirection The vertical direction to move the player.
     */
    private void movePlayer(Directions horizontalDirection, Directions verticalDirection) {
        MainPlayerModel.getMainPlayerModel().movePlayer(horizontalDirection, verticalDirection);
    }

    /**
     * Updates the game logic, including checking controls and updating updatable objects.
     */
    public void update() {
        if (MainPlayerModel.getMainPlayerModel().isInMenu() || MainPlayerModel.getMainPlayerModel().getMainPlayer().isDead())
            return;

        checkPressedKeys();
        for (Updatable updatable: MapModel.getMapModel().getUpdatableObjects())
            updatable.update();

        if (MapModel.getMapModel().getPassageBetweenLevels() != null){
            if (MapModel.getMapModel().getPassageBetweenLevels().isPlayerWithinPassage(MainPlayerModel.getMainPlayerModel().getMainPlayer())) {
                //saves information about the current items
                MainPlayerModel.getMainPlayerModel().getInventory().getInventoryController().writeAvailableItems(MainPlayerModel.getMainPlayerModel().getPathToItems());

                MapModel.getMapModel().setCurrentLevel(MapModel.getMapModel().getCurrentLevel() + 1);

                //saves information about the current level to a file
                try(FileWriter fileWriter = new FileWriter(MapModel.getMapModel().getPathToFileWithLevel(), false)) {
                    fileWriter.write(String.valueOf(MapModel.getMapModel().getCurrentLevel()));
                } catch (Exception e) {
                    logger.severe("Failed to write level to file: " + e.getMessage());
                }

                mainApp.startGame(false);
            }
        }
    }
    
    /**
     * Checks the set of the pressed keys and updates the player's state accordingly.
     */
    private void checkPressedKeys() {
        if (MainPlayerModel.getMainPlayerModel().getMainPlayer().isAiming){
            MainPlayerModel.getMainPlayerModel().getCurrentGunSight().getGameSpriteRenderInformation().setScreenCoordinateX(MouseX);
            MainPlayerModel.getMainPlayerModel().getCurrentGunSight().getGameSpriteRenderInformation().setScreenCoordinateY(MouseY);
            return;
        }
        if (pressedKeys.contains(KeyCode.W))
        {
            if (pressedKeys.contains(KeyCode.D))
                movePlayer(Directions.UP, Directions.RIGHT);
            else if (pressedKeys.contains(KeyCode.A))
                movePlayer(Directions.UP, Directions.LEFT);
            else
                movePlayer(Directions.UP, null);
        }else if (pressedKeys.contains(KeyCode.S)){
            if (pressedKeys.contains(KeyCode.D))
                movePlayer(Directions.DOWN, Directions.RIGHT);
            else if (pressedKeys.contains(KeyCode.A))
                movePlayer(Directions.DOWN, Directions.LEFT);
            else
                movePlayer(Directions.DOWN, null);
        }
        else{
            if (pressedKeys.contains(KeyCode.A))
                movePlayer(null, Directions.LEFT);
            if (pressedKeys.contains(KeyCode.D))
                movePlayer(null, Directions.RIGHT);
        }
    }
}
