package cvut.cz;


import cvut.cz.Model.Model;
import cvut.cz.characters.Directions;

import cvut.cz.items.InventoryCell;
import cvut.cz.items.ItemInformation;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;


import java.util.HashSet;

import java.util.Set;
import java.util.logging.Logger;

public class LogicManager {
    private static final Logger logger = Logger.getLogger(LogicManager.class.getName());

    private final MainApplication mainApp;
    private boolean selectingCell;
    private static InventoryCell selectedCell;
    private ItemInformation currentWeapon;

    private int MouseX;
    private int MouseY;

    private static Set<KeyCode> pressedKeys;

    public LogicManager(MainApplication application) {
        mainApp = application;
        pressedKeys = new HashSet<>();
        selectingCell = false;

        mainApp.getStage().getScene().setOnMouseDragged(e -> {
            MouseX = (int) e.getSceneX();
            MouseY = (int) e.getSceneY();
        });

        mainApp.getStage().getScene().setOnMousePressed(e -> {
            switch (e.getButton()) {
                case MouseButton.PRIMARY:
                    Model.shoot();
                    break;
                case MouseButton.SECONDARY:
                    if (Model.getInventory().getEquippedCell() != null) {
                        currentWeapon = Model.getInventory().getEquippedCell().getItem().getItemInformation();
                        if (currentWeapon.name().equals("pistol") || currentWeapon.name().equals("shotgun")) {
                            MouseX = (int) e.getSceneX();
                            MouseY = (int) e.getSceneY();
                            mainApp.setIsAiming(true);
                            mainApp.setCurrentGunSight(Model.getMainPlayer().getWeaponsAndGunSights().get(currentWeapon.name()));
                        }
                    }
                    break;
            }
        });

        mainApp.getStage().getScene().setOnMouseReleased(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                mainApp.setIsAiming(false);
                mainApp.setCurrentGunSight(null);
            }
        });

        mainApp.getStage().getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.TAB) {
                mainApp.setIsInventoryActive(!mainApp.isInventoryActive());
                mainApp.setIsAiming(false);
                selectingCell = false;
                selectedCell = null;
                pressedKeys.clear();
                mainApp.getGraphicsContexts().get(1).clearRect(0, 0, mainApp.getScreenWidth(), mainApp.getScreenHeight());
            }

            if (mainApp.isInventoryActive()) {
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
                        if (selectingCell){
                            Model.getInventory().combineCells(selectedCell, Model.getInventory().getSelectedInventoryCell());
                            selectedCell = null;
                            selectingCell = false;
                        }else {
                            selectedCell = Model.getInventory().getSelectedInventoryCell();
                            selectingCell = true;
                        }
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

                        Model.getInventory().equipCell(Model.getInventory().getSelectedInventoryCell());
                        break;
                    case KeyCode.F:
                        if (selectingCell)
                            break;

                        Model.getInventory().useCell(Model.getInventory().getSelectedInventoryCell());
                        break;
                    case KeyCode.K:
                        if (selectingCell)
                            break;

                        Model.getInventory().discardCell(Model.getInventory().getSelectedInventoryCell());
                        break;
                }
            }
            else{
                if (e.getCode() == KeyCode.E)
                    Model.takeItem();
                else
                    pressedKeys.add(e.getCode());
            }
        });
        mainApp.getStage().getScene().setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
    }

    private void moveInventoryPointer(Directions direction) {
        Model.getInventory().movePointer(direction);
    }

    private void movePlayer(Directions direction) {
        Model.movePlayer(direction);
    }

    public void update() {
            checkControls();
            for (Updatable updatable: Model.getUpdatableObjects())
                updatable.update();
    }

    private void checkControls() {
        if (mainApp.isAiming()){
            mainApp.getCurrentGunSight().getGameSpriteRenderInformation().setScreenCoordinateX(MouseX);
            mainApp.getCurrentGunSight().getGameSpriteRenderInformation().setScreenCoordinateY(MouseY);
            return;
        }
        if (pressedKeys.contains(KeyCode.W))
            movePlayer(Directions.UP);
        if (pressedKeys.contains(KeyCode.A))
            movePlayer(Directions.LEFT);
        if (pressedKeys.contains(KeyCode.D))
            movePlayer(Directions.RIGHT);
        if (pressedKeys.contains(KeyCode.S))
            movePlayer(Directions.DOWN);

    }

}
