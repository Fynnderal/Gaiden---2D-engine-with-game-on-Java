package cvut.cz;

import cvut.cz.GameSprite.GameSprite;
import cvut.cz.Model.MainPlayerModel;
import cvut.cz.Model.MapModel;
import cvut.cz.characters.CrossHair;
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

/**
 * Manages the rendering of game elements, including sprites, inventory, and UI components.
 * This class handles drawing operations and updates the game screen based on the current game state.
 */
public class RenderManager{
    private static final Logger logger = Logger.getLogger(RenderManager.class.getName());

    // HashMap to store images to draw. Keys are the image paths, values are the images.
    private final HashMap<String, Image> imagesToDraw = new HashMap<>();

    // Background for the dialogue option in the dialogue panel when it is currently selected
    public static Background activeBackGround = new Background(new BackgroundFill(Color.ORANGE, null, null));

    // Background for the dialogue option in the dialogue panel when it is not currently selected
    public static Background notActiveBackGround = new Background(new BackgroundFill(Color.BEIGE, null, null));

    private final MainApplication mainApp;

    // Observable property to hold the current message
    private final ObjectProperty<String[]> observableCurrentMessage;
    // Flag to indicate if there is a new message
    private boolean haveNewMessage;

    /**
     * Constructs a RenderManager with the specified main application instance.
     *
     * @param application The main application instance.
     */
    public RenderManager(MainApplication application) {
        mainApp = application;

        observableCurrentMessage = new SimpleObjectProperty<>();
        observableCurrentMessage.setValue(MainPlayerModel.getMainPlayerModel().getCurrentMessage());

        // if value of observableCurrentMessage changes, set haveNewMessage to true
        observableCurrentMessage.addListener((_, _, _) -> haveNewMessage = true);

        haveNewMessage = false;

    }

    /**
     * Draws a game sprite on the specified graphics context.
     *
     * @param sprite The game sprite to draw.
     * @param gc The graphics context to draw on.
     */
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

    /**
     * Draws inventory-related labels on the specified graphics context.
     *
     * @param gc The graphics context to draw on.
     */
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

    /**
     * Draws the inventory and its contents on the specified graphics context.
     *
     * @param gc The graphics context to draw on.
     */
    private void drawInventory(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.loadFont(String.valueOf(mainApp.getPathToFont()), 35));
        gc.clearRect(0, 0, mainApp.getScreenWidth(), mainApp.getScreenHeight());

        drawSprite(MainPlayerModel.getMainPlayerModel().getInventory(), gc);

        // Draws items in the inventory cells
        for (InventoryCell cell : MainPlayerModel.getMainPlayerModel().getInventory().getCells()) {
            if (cell.getItem() != null) {
                drawSprite(cell.getItem(), gc);
                gc.fillText(String.valueOf(cell.getItem().getAmount()), cell.getCoordinateX() + MainPlayerModel.getMainPlayerModel().getInventory().getInventoryCellGeneralInformation().amountLabelCoordinateXRelativeToCell(), cell.getCoordinateY() + MainPlayerModel.getMainPlayerModel().getInventory().getInventoryCellGeneralInformation().amountLabelCoordinateYRelativeToCell());
            }
        }
        // Draws the pointer
        if (MainPlayerModel.getMainPlayerModel().getInventory().getPointer() != null)
            drawSprite(MainPlayerModel.getMainPlayerModel().getInventory().getPointer(), gc);

        drawInventoryLabels(gc);
    }

    /**
     * Updates the rendering of the game screen based on the current game state.
     * This method handles drawing sprites, UI components, and managing game interactions.
     */
    public void update() {
        // Draws game over screen
        if (MainPlayerModel.getMainPlayerModel().getMainPlayer().isDead()){
            mainApp.getGuiCreator().getGameOverScreen().setVisible(true);
            mainApp.getStage().getScene().setCursor(Cursor.CROSSHAIR);
            return;
        }

        if (mainApp.getGraphicsContexts().isEmpty()) {
            logger.severe("Don't have Graphics Context");
            return;
        }


        if (mainApp.getGuiCreator().getHealthBar() != null) {
            int currentProgress = MainPlayerModel.getMainPlayerModel().getMainPlayer().getCharacterInformation().getCurrentHealth();
            int maxProgress = MainPlayerModel.getMainPlayerModel().getMainPlayer().getCharacterInformation().getMaxHealth();
            mainApp.getGuiCreator().getHealthBar().setProgress((double) currentProgress/ maxProgress );
        }
        mainApp.getGraphicsContexts().getFirst().clearRect(0, 0, mainApp.getScreenWidth(), mainApp.getScreenHeight());
        drawDrawableSprites();

        // Draws the in-game menu
        if (MainPlayerModel.getMainPlayerModel().isInMenu()){
            mainApp.getGuiCreator().getInGameMenu().setVisible(true);
            mainApp.getStage().getScene().setCursor(Cursor.CROSSHAIR);
        }
        else{
            mainApp.getGuiCreator().getInGameMenu().setVisible(false);
            mainApp.getStage().getScene().setCursor(Cursor.NONE);
        }
        // Draws the dialogue panel if the player is interacting with an NPC
        if (MainPlayerModel.getMainPlayerModel().isInteracting()) {
            drawDialogue();
        }else{
            mainApp.getGuiCreator().getDialoguePanel().setVisible(false);
        }

        if (mainApp.getGraphicsContexts().size() > 1) {
            mainApp.getGraphicsContexts().get(1).clearRect(0, 0, mainApp.getScreenWidth(), mainApp.getScreenHeight());
            if (MainPlayerModel.getMainPlayerModel().getMainPlayer().isAiming)
                drawCrossHair(mainApp.getGraphicsContexts().get(1));

            // Draws the inventory
            if (MainPlayerModel.getMainPlayerModel().isInventoryActive()) {
                if (mainApp.getGraphicsContexts().size() < 2) {
                    return;
                }
                drawInventory(mainApp.getGraphicsContexts().get(1));
            }
        }
        else{
            logger.severe("Don't have Graphics Context for the inventory");
        }
    }

    /**
     * Draws the dialogue panel and updates its content based on the current message.
     */
    private void drawDialogue(){
        // Sets the current message to the observable property. if message differs from the previous one, set haveNewMessage to true
        observableCurrentMessage.setValue(MainPlayerModel.getMainPlayerModel().getCurrentMessage());
        VBox dialoguePanel = mainApp.getGuiCreator().getDialoguePanel();
        dialoguePanel.setVisible(true);

        if (haveNewMessage) {
            for (int i = 0; i < MainPlayerModel.getMainPlayerModel().getCurrentMessage().length; i++) {
                String currentLine = MainPlayerModel.getMainPlayerModel().getCurrentMessage()[i];

                // if there is not enough lines in the dialogue panel, create a new one
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

            // if there are more lines in the dialogue panel than in the current message, set text in the extra lines to empty line
            for (int i = MainPlayerModel.getMainPlayerModel().getCurrentMessage().length; i < dialoguePanel.getChildren().size(); i++){
                ((Label) dialoguePanel.getChildren().get(i)).setText("");
            }

            haveNewMessage = false;
        }

        // Sets the background of the dialogue panel to not active
        for (int i = 0; i < dialoguePanel.getChildren().size(); i++) {
            ((Label) dialoguePanel.getChildren().get(i)).setBackground(notActiveBackGround);
        }

        // Sets the background of the currently selected dialogue option to active
        if (MainPlayerModel.getMainPlayerModel().getCurrentNPCInteractingWith().isWaitingForAnswer()) {
            int idx = MainPlayerModel.getMainPlayerModel().getCurrentNPCInteractingWith().getCurrentPlayersAnswer();
            ((Label) dialoguePanel.getChildren().get(idx)).setBackground(activeBackGround);
        }
    }

    /**
     * Draws the crosshair on the screen when the player is aiming.
     *
     * @param gc The graphics context to draw on.
     */
    private void drawCrossHair(GraphicsContext gc){
        CrossHair crossHair = MainPlayerModel.getMainPlayerModel().getCurrentGunSight();
        drawSprite(crossHair, gc);
    }

    /**
     * Draws all drawable sprites on the screen.
     */
    private void drawDrawableSprites() {
        GameSprite currentSprite;
        for (int i = 0; i < MapModel.getMapModel().getDrawableObjects().size(); i++) {
            currentSprite = MapModel.getMapModel().getDrawableObjects().get(i);
            if (currentSprite == null)
                continue;

            drawSprite(currentSprite, mainApp.getGraphicsContexts().getFirst());
        }
    }

    /**
     * Retrieves the map of images to draw.
     *
     * @return The map of image paths to images.
     */
    public HashMap<String, Image> getImagesToDraw() { return imagesToDraw; }

}
