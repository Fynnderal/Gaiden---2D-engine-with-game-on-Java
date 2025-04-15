package cvut.cz;

import cvut.cz.items.InventoryCell;
import cvut.cz.items.Item;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.logging.Logger;

public class RenderManager{
    private static final Logger logger = Logger.getLogger(RenderManager.class.getName());

    private final MainApplication mainApp;

    public RenderManager(MainApplication application) {
        mainApp = application;
    }


    public void start(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> update()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void drawSprite(GameSprite sprite, GraphicsContext gc) {
        gc.drawImage(new Image(String.valueOf(sprite.getGameSpriteSourceInformation().getPathImage())), sprite.getGameSpriteSourceInformation().getSourceCoordinateX(), sprite.getGameSpriteSourceInformation().getSourceCoordinateY(), sprite.getGameSpriteSourceInformation().getSourceWidth(), sprite.gameSpriteSourceInformation.getSourceHeight(), sprite.getGameSpriteRenderInformation().getScreenCoordinateX() * mainApp.getRENDER_SCALE_FACTOR(), sprite.getGameSpriteRenderInformation().getScreenCoordinateY() * mainApp.getRENDER_SCALE_FACTOR(), sprite.getGameSpriteRenderInformation().getTargetWidth() * mainApp.getRENDER_SCALE_FACTOR(), sprite.getGameSpriteRenderInformation().getTargetHeight() * mainApp.getRENDER_SCALE_FACTOR());
    }

    private void drawInventory(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.setFont(new Font("Arial", 20));
        gc.clearRect(0, 0, mainApp.getScreenWidth(), mainApp.getScreenHeight());
        drawSprite(mainApp.getModel().getInventory(), gc);

        for (InventoryCell cell : mainApp.getModel().getInventory().getCells()) {
            if (cell.getItem() != null) {
                drawSprite(cell.getItem(), gc);
                gc.fillText(String.valueOf(cell.getItemAmount()), cell.getCoordinateX() + mainApp.getModel().getInventory().getInventoryInformation().inventoryCellGeneralInformation().amountLabelCoordinateXRelativeToCell(), cell.getCoordinateY() + mainApp.getModel().getInventory().getInventoryInformation().inventoryCellGeneralInformation().amountLabelCoordinateYRelativeToCell());
            }
        }
        drawSprite(mainApp.getModel().getInventory().getInventoryInformation().pointer(), gc);
        Item currentItem = mainApp.getModel().getInventory().getSelectedInventoryCell().getItem();

        if (currentItem != null) {
            if (currentItem.getItemInformation().canBeCombinedWithInto() != null)
                gc.fillText("T: Combine", mainApp.getSCREEN_STANDARD_WIDTH() - 200, mainApp.getSCREEN_STANDARD_HEIGHT() - 50);

            if (currentItem.getItemInformation().canBeEquipped())
                gc.fillText("R: Equip", mainApp.getSCREEN_STANDARD_WIDTH() - 300, mainApp.getSCREEN_STANDARD_HEIGHT() - 50);

            if (currentItem.getItemInformation().canBeUsed())
                gc.fillText("F: Use", mainApp.getSCREEN_STANDARD_WIDTH(), - 400, mainApp.getSCREEN_STANDARD_HEIGHT()  - 50);

            if (currentItem.getItemInformation().canBeDiscarded())
                gc.fillText("K: Discard", mainApp.getSCREEN_STANDARD_WIDTH(), - 500, mainApp.getSCREEN_STANDARD_HEIGHT() - 50);
        }
    }


    public void update() {
            if (mainApp.getGraphicsContexts().isEmpty()) {
                logger.severe("Don't have Graphics Context");
                return;
            }

            mainApp.getGraphicsContexts().getFirst().clearRect(0, 0, mainApp.getScreenWidth(), mainApp.getScreenHeight());
            for (GameSprite sprite : mainApp.getDrawableObjects()) {
                if (sprite == null)
                    continue;

                drawSprite(sprite, mainApp.getGraphicsContexts().getFirst());
            }

            if (mainApp.isInventoryActive()) {
                if (mainApp.getGraphicsContexts().size() < 2) {
                    logger.severe("Don't have Graphics Context");
                    return;
                }
                drawInventory(mainApp.getGraphicsContexts().get(1));
            }

    }


}
