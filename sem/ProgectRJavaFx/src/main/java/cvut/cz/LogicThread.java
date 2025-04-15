package cvut.cz;


import cvut.cz.characters.Directions;

import cvut.cz.items.InventoryCell;
import javafx.scene.input.KeyCode;


import java.util.HashSet;

import java.util.Set;
import java.util.logging.Logger;

public class LogicThread implements Runnable{
    private static final Logger logger = Logger.getLogger(LogicThread.class.getName());

    private final MainApplication mainApp;
    private boolean selectingCell;
    private static InventoryCell selectedCell;

    private static Set<KeyCode> pressedKeys;

    public LogicThread(MainApplication application) {
        mainApp = application;

        pressedKeys = new HashSet<>();
        selectingCell = false;
        mainApp.getStage().getScene().setOnKeyPressed(e -> {
            if (selectingCell){
                switch (e.getCode()) {
                    case KeyCode.Q:
                        selectingCell = false;
                        selectedCell  = null;
                        System.out.println("Cancel selecting cell");
                        break;
                    case KeyCode.T:
                        System.out.println("trying to combine: " + selectedCell.getItem().getItemInformation().name() + "and" + mainApp.getModel().getInventory().getSelectedInventoryCell().getItem().getItemInformation().name());
                        mainApp.getModel().getInventory().combineCells(selectedCell, mainApp.getModel().getInventory().getSelectedInventoryCell());
                        break;
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
                }
                return;
            }
            if (e.getCode() == KeyCode.E) {
                mainApp.setIsInventoryActive(!mainApp.isInventoryActive());
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
                        selectedCell = mainApp.getModel().getInventory().getSelectedInventoryCell();
                        selectingCell = true;
                        System.out.println("First selected: " + selectedCell.getItem().getItemInformation().name());
                        break;

                }
            }
            else{
                pressedKeys.add(e.getCode());
            }
        });
        mainApp.getStage().getScene().setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
    }

    private void moveInventoryPointer(Directions direction) {
        mainApp.getModel().getInventory().movePointer(direction);
    }

    private void movePlayer(Directions direction) {
        mainApp.getModel().movePlayer(direction);
    }

    @Override
    public void run() {
        while(true) {
            checkControls();
            try {
                Thread.sleep(1000 / 60);
            }catch (InterruptedException e) {}
        }

    }

    private void checkControls() {
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
