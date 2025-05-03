package cvut.cz;

import cvut.cz.GameSprite.GameSprite;
import cvut.cz.Model.MainPlayerModel;
import cvut.cz.Model.MapModel;
import cvut.cz.characters.GunSight;
import cvut.cz.items.InventoryCell;
import cvut.cz.items.Item;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.logging.Logger;

public class RenderManager{
    private static final Logger logger = Logger.getLogger(RenderManager.class.getName());

    private final HashMap<String, Image> imagesToDraw = new HashMap<>();

    public static Background activeBackGround = new Background(new BackgroundFill(Color.ORANGE, null, null));
    public static Background notActiveBackGround = new Background(new BackgroundFill(Color.BEIGE, null, null));

    private final MainApplication mainApp;

    private final ObjectProperty<String[]> observableCurrentMessage;
    private boolean haveNewMessage;

    public RenderManager(MainApplication application) {
        mainApp = application;

        observableCurrentMessage = new SimpleObjectProperty<>();
        observableCurrentMessage.setValue(MainPlayerModel.getMainPlayerModel().getCurrentMessage());
        observableCurrentMessage.addListener((_, _, _) -> haveNewMessage = true);
        haveNewMessage = false;
    }


    private void drawSprite(GameSprite sprite, GraphicsContext gc) {
        Image image = imagesToDraw.get(String.valueOf(sprite.getGameSpriteSourceInformation().getPathImage()));

        if (sprite.getGameSpriteRenderInformation().getScreenCoordinateX() <= mainApp.getSCREEN_STANDARD_WIDTH() &&
            (sprite.getGameSpriteRenderInformation().getScreenCoordinateX() + sprite.getGameSpriteRenderInformation().getTargetWidth()) >= 0 &&
            sprite.getGameSpriteRenderInformation().getScreenCoordinateY() <= mainApp.getSCREEN_STANDARD_HEIGHT() &&
            sprite.getGameSpriteRenderInformation().getScreenCoordinateY() + sprite.getGameSpriteRenderInformation().getTargetHeight() >= 0
        ) {
            gc.drawImage(image, sprite.getGameSpriteSourceInformation().getSourceCoordinateX(), sprite.getGameSpriteSourceInformation().getSourceCoordinateY(), sprite.getGameSpriteSourceInformation().getSourceWidth(), sprite.getGameSpriteSourceInformation().getSourceHeight(), sprite.getGameSpriteRenderInformation().getScreenCoordinateX() * mainApp.getRENDER_SCALE_FACTOR(), sprite.getGameSpriteRenderInformation().getScreenCoordinateY() * mainApp.getRENDER_SCALE_FACTOR(), sprite.getGameSpriteRenderInformation().getTargetWidth() * mainApp.getRENDER_SCALE_FACTOR(), sprite.getGameSpriteRenderInformation().getTargetHeight() * mainApp.getRENDER_SCALE_FACTOR());

        }
    }

    private void drawInventoryLabels(GraphicsContext gc){
        InventoryCell currentInventoryCell = MainPlayerModel.getMainPlayerModel().getInventory().getSelectedInventoryCell();
        Item currentItem = currentInventoryCell.getItem();

        if (currentItem != null) {
            if (currentItem.getItemInformation().canBeCombinedWithInto() != null)
                gc.fillText("T: Combine", mainApp.getSCREEN_STANDARD_WIDTH() - 150, mainApp.getSCREEN_STANDARD_HEIGHT() - 50);

            if (currentItem.getItemInformation().canBeEquipped())
                if (!currentInventoryCell.getIsItemEquipped())
                    gc.fillText("R: Equip", mainApp.getSCREEN_STANDARD_WIDTH() - 300, mainApp.getSCREEN_STANDARD_HEIGHT() - 50);
                else
                    gc.fillText("R: Unequip", mainApp.getSCREEN_STANDARD_WIDTH() - 300, mainApp.getSCREEN_STANDARD_HEIGHT() - 50);

            if (currentItem.getItemInformation().canBeUsed())
                gc.fillText("F: Use",  mainApp.getSCREEN_STANDARD_WIDTH() - 400, mainApp.getSCREEN_STANDARD_HEIGHT()  - 50);

            if (currentItem.getItemInformation().canBeDiscarded())
                gc.fillText("K: Discard", mainApp.getSCREEN_STANDARD_WIDTH() - 550, mainApp.getSCREEN_STANDARD_HEIGHT() - 50);
        }
    }

    private void drawInventory(GraphicsContext gc) {
        gc.setFill(Color.WHITE);

        gc.setFont(Font.loadFont(String.valueOf(mainApp.getPathToFont()), 35));
        gc.clearRect(0, 0, mainApp.getScreenWidth(), mainApp.getScreenHeight());
        drawSprite(MainPlayerModel.getMainPlayerModel().getInventory(), gc);

        for (InventoryCell cell : MainPlayerModel.getMainPlayerModel().getInventory().getCells()) {
            if (cell.getItem() != null) {
                drawSprite(cell.getItem(), gc);
                gc.fillText(String.valueOf(cell.getItem().getAmount()), cell.getCoordinateX() + MainPlayerModel.getMainPlayerModel().getInventory().getInventoryCellGeneralInformation().amountLabelCoordinateXRelativeToCell(), cell.getCoordinateY() + MainPlayerModel.getMainPlayerModel().getInventory().getInventoryCellGeneralInformation().amountLabelCoordinateYRelativeToCell());
            }
        }
        if (MainPlayerModel.getMainPlayerModel().getInventory().getPointer() != null)
            drawSprite(MainPlayerModel.getMainPlayerModel().getInventory().getPointer(), gc);

        drawInventoryLabels(gc);
    }

    public void update() {
            if (MainPlayerModel.getMainPlayerModel().getMainPlayer().isDead()){
                mainApp.getGuiCreator().getGameOverScreen().setVisible(true);
                mainApp.getStage().getScene().setCursor(Cursor.CROSSHAIR);
                return;
            }

            if (mainApp.getGraphicsContexts().isEmpty()) {
                logger.severe("Don't have Graphics Context");
                return;
            }

            if (mainApp.getGuiCreator().getHealthBar() != null)
                mainApp.getGuiCreator().getHealthBar().setProgress((double) MainPlayerModel.getMainPlayerModel().getMainPlayer().getCharacterInformation().getCurrentHealth() / MainPlayerModel.getMainPlayerModel().getMainPlayer().getCharacterInformation().getMaxHealth());

            mainApp.getGraphicsContexts().getFirst().clearRect(0, 0, mainApp.getScreenWidth(), mainApp.getScreenHeight());
            drawDrawableSprites();

            if (MainPlayerModel.getMainPlayerModel().isInMenu()){
                mainApp.getGuiCreator().getInGameMenu().setVisible(true);
                mainApp.getStage().getScene().setCursor(Cursor.CROSSHAIR);
            }
            else{
                mainApp.getGuiCreator().getInGameMenu().setVisible(false);
                mainApp.getStage().getScene().setCursor(Cursor.NONE);
            }

            if (MainPlayerModel.getMainPlayerModel().isInteracting()) {
                drawDialogue();
            }else{
                mainApp.getGuiCreator().getDialoguePanel().setVisible(false);
            }

            if (MainPlayerModel.getMainPlayerModel().getMainPlayer().isAiming)
                drawGunSight();

            if (MainPlayerModel.getMainPlayerModel().isInventoryActive()) {
                if (mainApp.getGraphicsContexts().size() < 2) {
                    logger.severe("Don't have Graphics Context");
                    return;
                }
                drawInventory(mainApp.getGraphicsContexts().get(1));
            }

            if (!MainPlayerModel.getMainPlayerModel().getMainPlayer().isAiming && !MainPlayerModel.getMainPlayerModel().isInventoryActive() && mainApp.getGraphicsContexts().size() > 1)
                mainApp.getGraphicsContexts().get(1).clearRect(0, 0, mainApp.getScreenWidth(), mainApp.getScreenHeight());
    }

    private void drawDialogue(){
        observableCurrentMessage.setValue(MainPlayerModel.getMainPlayerModel().getCurrentMessage());
        VBox dialoguePanel = mainApp.getGuiCreator().getDialoguePanel();
        dialoguePanel.setVisible(true);

        if (haveNewMessage) {
            for (int i = 0; i < MainPlayerModel.getMainPlayerModel().getCurrentMessage().length; i++) {
                String currentLine = MainPlayerModel.getMainPlayerModel().getCurrentMessage()[i];
                if (i >= dialoguePanel.getChildren().size()) {
                    Label dialogueLine = new Label();
                    dialogueLine.setMaxSize(mainApp.getScreenWidth(), mainApp.getScreenHeight() / 3);
                    dialogueLine.setFont(Font.font("Arial", 20 * mainApp.getRENDER_SCALE_FACTOR()));
                    dialogueLine.setText(currentLine);
                    dialoguePanel.getChildren().add(dialogueLine);
                    continue;
                }


                ((Label) dialoguePanel.getChildren().get(i)).setText(currentLine);
            }
            for (int i = MainPlayerModel.getMainPlayerModel().getCurrentMessage().length; i < dialoguePanel.getChildren().size(); i++){
                ((Label) dialoguePanel.getChildren().get(i)).setText("");
            }

            haveNewMessage = false;
        }

        for (int i = 0; i < dialoguePanel.getChildren().size(); i++) {
            ((Label) dialoguePanel.getChildren().get(i)).setBackground(notActiveBackGround);
        }

        if (MainPlayerModel.getMainPlayerModel().getCurrentNPCInteractingWith().isWaitingForAnswer()) {
            int idx = MainPlayerModel.getMainPlayerModel().getCurrentNPCInteractingWith().getCurrentPlayersAnswer();
            ((Label) dialoguePanel.getChildren().get(idx)).setBackground(activeBackGround);
        }
    }

    private void drawGunSight(){
        if (mainApp.getGraphicsContexts().size() < 2){
            logger.severe("Don't have Graphics Context");
            return;
        }

        mainApp.getGraphicsContexts().get(1).clearRect(0, 0, mainApp.getScreenWidth(), mainApp.getScreenHeight());

        GunSight gunSight = MainPlayerModel.getMainPlayerModel().getCurrentGunSight();
        drawSprite(gunSight, mainApp.getGraphicsContexts().get(1));
    }

    private void drawDrawableSprites(){
        GameSprite currentSprite;
        for (int i = 0; i < MapModel.getMapModel().getDrawableObjects().size(); i++) {
            currentSprite = MapModel.getMapModel().getDrawableObjects().get(i);
            if (currentSprite == null)
                continue;

            drawSprite(currentSprite, mainApp.getGraphicsContexts().getFirst());
        }

    }

    public HashMap<String, Image> getImagesToDraw() { return imagesToDraw; }

}
