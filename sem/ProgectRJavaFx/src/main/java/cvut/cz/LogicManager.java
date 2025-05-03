package cvut.cz;

import cvut.cz.Model.MainPlayerModel;
import cvut.cz.Model.MapModel;
import cvut.cz.Model.Updatable;
import cvut.cz.characters.Directions;

import cvut.cz.characters.GunSight;
import cvut.cz.items.InventoryCell;
import cvut.cz.items.ItemInformation;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.HashSet;

import java.util.Set;
import java.util.logging.Logger;

public class LogicManager {
    private static final Logger logger = Logger.getLogger(LogicManager.class.getName());

    private final MainApplication mainApp;
    private boolean selectingCell;
    private static InventoryCell selectedCell;

    private int MouseX;
    private int MouseY;

    private static Set<KeyCode> pressedKeys;

    public LogicManager(MainApplication application) {
        mainApp = application;
        pressedKeys = new HashSet<>();
        selectingCell = false;
        setControls();
    }

    private void aim(MouseEvent e){
        if (MainPlayerModel.getMainPlayerModel().getInventory().getEquippedCell() != null &&
                MainPlayerModel.getMainPlayerModel().getMainPlayer().getWeaponsAndGunSights() != null) {

            ItemInformation currentWeapon = MainPlayerModel.getMainPlayerModel().getInventory().getEquippedCell().getItem().getItemInformation();
            if (currentWeapon.name().equals("pistol") || currentWeapon.name().equals("shotgun")) {
                MouseX = (int) e.getSceneX();
                MouseY = (int) e.getSceneY();
                MainPlayerModel.getMainPlayerModel().getMainPlayer().isAiming = true;
                GunSight currentGunSight = MainPlayerModel.getMainPlayerModel().getMainPlayer().getWeaponsAndGunSights().get(currentWeapon.name());
                MainPlayerModel.getMainPlayerModel().setCurrentGunSight(currentGunSight);
            }
        }
    }

    private void setMouseControls(){
        mainApp.getStage().getScene().setOnMouseDragged(e -> {
            MouseX = (int) e.getSceneX();
            MouseY = (int) e.getSceneY();
        });

        mainApp.getStage().getScene().setOnMousePressed(e -> {
            if (MainPlayerModel.getMainPlayerModel().isInMenu())
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

    private void interactWithNPC(KeyEvent e){
        if (MainPlayerModel.getMainPlayerModel().isInteracting()) {
            if (e.getCode() == KeyCode.W)
                MainPlayerModel.getMainPlayerModel().getCurrentNPCInteractingWith().changePlayersAnswer(Directions.UP);
            else if (e.getCode() == KeyCode.S)
                MainPlayerModel.getMainPlayerModel().getCurrentNPCInteractingWith().changePlayersAnswer(Directions.DOWN);
        }
    }

    private void controlCombiningItems(){
        if (selectingCell){
            MainPlayerModel.getMainPlayerModel().getInventory().combineCells(selectedCell, MainPlayerModel.getMainPlayerModel().getInventory().getSelectedInventoryCell());
            selectedCell = null;
            selectingCell = false;
        }else {
            selectedCell = MainPlayerModel.getMainPlayerModel().getInventory().getSelectedInventoryCell();
            selectingCell = true;
        }
    }

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

                MainPlayerModel.getMainPlayerModel().getInventory().equipCell(MainPlayerModel.getMainPlayerModel().getInventory().getSelectedInventoryCell());
                break;
            case KeyCode.F:
                if (selectingCell)
                    break;

                MainPlayerModel.getMainPlayerModel().getInventory().useCell(MainPlayerModel.getMainPlayerModel().getInventory().getSelectedInventoryCell());
                break;
            case KeyCode.K:
                if (selectingCell)
                    break;

                MainPlayerModel.getMainPlayerModel().getInventory().discardCell(MainPlayerModel.getMainPlayerModel().getInventory().getSelectedInventoryCell());
                break;
        }
    }

    private void controlPlayer(KeyEvent e){
        if (e.getCode() == KeyCode.E)
            MainPlayerModel.getMainPlayerModel().takeItem();
        else if (e.getCode() == KeyCode.SPACE)
            MainPlayerModel.getMainPlayerModel().teleportMainPlayer();
        else if (e.getCode() == KeyCode.ENTER)
            MainPlayerModel.getMainPlayerModel().getMainPlayer().getInventory().writeAvailableItems();
        else
            pressedKeys.add(e.getCode());
    }

    private void setKeyControls(){
        mainApp.getStage().getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                MainPlayerModel.getMainPlayerModel().setIsInMenu(!MainPlayerModel.getMainPlayerModel().isInMenu());
            }

            if (MainPlayerModel.getMainPlayerModel().isInMenu())
                return;

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

    private void setControls(){
        setMouseControls();
        setKeyControls();
    }

    private void moveInventoryPointer(Directions direction) {
        MainPlayerModel.getMainPlayerModel().getInventory().movePointer(direction);
    }

    private void movePlayer(Directions horizontalDirection, Directions verticalDirection) {
        MainPlayerModel.getMainPlayerModel().movePlayer(horizontalDirection, verticalDirection);
    }

    public void update() {
            if (MainPlayerModel.getMainPlayerModel().isInMenu() || MainPlayerModel.getMainPlayerModel().getMainPlayer().isDead())
                return;

            checkControls();
            for (Updatable updatable: MapModel.getMapModel().getUpdatableObjects())
                updatable.update();
    }

    private void checkControls() {
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
