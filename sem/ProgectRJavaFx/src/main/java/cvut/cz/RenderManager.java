package cvut.cz;

import cvut.cz.GameSprite.GameSprite;
import cvut.cz.Model.Model;
import cvut.cz.items.InventoryCell;
import cvut.cz.items.Item;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.List;

public class RenderManager{
    private static final Logger logger = Logger.getLogger(RenderManager.class.getName());

    private final MainApplication mainApp;

    public RenderManager(MainApplication application) {
        mainApp = application;
    }


    private void drawSprite(Image image, GameSprite sprite, GraphicsContext gc) {
        if ( sprite.getGameSpriteRenderInformation().getScreenCoordinateX() <= mainApp.getSCREEN_STANDARD_WIDTH() && (sprite.getGameSpriteRenderInformation().getScreenCoordinateX() + sprite.getGameSpriteRenderInformation().getTargetWidth()) >= 0 && sprite.getGameSpriteRenderInformation().getScreenCoordinateY() <= mainApp.getSCREEN_STANDARD_HEIGHT() && sprite.getGameSpriteRenderInformation().getScreenCoordinateY() + sprite.getGameSpriteRenderInformation().getTargetHeight() >= 0)
            gc.drawImage(image, sprite.getGameSpriteSourceInformation().getSourceCoordinateX(), sprite.getGameSpriteSourceInformation().getSourceCoordinateY(), sprite.getGameSpriteSourceInformation().getSourceWidth(), sprite.getGameSpriteSourceInformation().getSourceHeight(), sprite.getGameSpriteRenderInformation().getScreenCoordinateX() * mainApp.getRENDER_SCALE_FACTOR(), sprite.getGameSpriteRenderInformation().getScreenCoordinateY() * mainApp.getRENDER_SCALE_FACTOR(), sprite.getGameSpriteRenderInformation().getTargetWidth() * mainApp.getRENDER_SCALE_FACTOR(), sprite.getGameSpriteRenderInformation().getTargetHeight() * mainApp.getRENDER_SCALE_FACTOR());
    }
    private void drawSprite(GameSprite sprite, GraphicsContext gc) {
        if ( sprite.getGameSpriteRenderInformation().getScreenCoordinateX() <= mainApp.getSCREEN_STANDARD_WIDTH() && (sprite.getGameSpriteRenderInformation().getScreenCoordinateX() + sprite.getGameSpriteRenderInformation().getTargetWidth()) >= 0 && sprite.getGameSpriteRenderInformation().getScreenCoordinateY() <= mainApp.getSCREEN_STANDARD_HEIGHT() && sprite.getGameSpriteRenderInformation().getScreenCoordinateY() + sprite.getGameSpriteRenderInformation().getTargetHeight() >= 0)
            gc.drawImage(new Image(String.valueOf(sprite.getGameSpriteSourceInformation().getPathImage())), sprite.getGameSpriteSourceInformation().getSourceCoordinateX(), sprite.getGameSpriteSourceInformation().getSourceCoordinateY(), sprite.getGameSpriteSourceInformation().getSourceWidth(), sprite.getGameSpriteSourceInformation().getSourceHeight(), sprite.getGameSpriteRenderInformation().getScreenCoordinateX() * mainApp.getRENDER_SCALE_FACTOR(), sprite.getGameSpriteRenderInformation().getScreenCoordinateY() * mainApp.getRENDER_SCALE_FACTOR(), sprite.getGameSpriteRenderInformation().getTargetWidth() * mainApp.getRENDER_SCALE_FACTOR(), sprite.getGameSpriteRenderInformation().getTargetHeight() * mainApp.getRENDER_SCALE_FACTOR());
    }

    private void drawInventory(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.setFont(new Font("Arial", 20));
        gc.clearRect(0, 0, mainApp.getScreenWidth(), mainApp.getScreenHeight());
        drawSprite(Model.getInventory(), gc);

        for (InventoryCell cell : Model.getInventory().getCells()) {
            if (cell.getItem() != null) {
                drawSprite(cell.getItem(), gc);
                gc.fillText(String.valueOf(cell.getItem().getAmount()), cell.getCoordinateX() + Model.getInventory().getInventoryInformation().inventoryCellGeneralInformation().amountLabelCoordinateXRelativeToCell(), cell.getCoordinateY() + Model.getInventory().getInventoryInformation().inventoryCellGeneralInformation().amountLabelCoordinateYRelativeToCell());
            }
        }
        drawSprite(Model.getInventory().getInventoryInformation().pointer(), gc);
        InventoryCell currentInventoryCell = Model.getInventory().getSelectedInventoryCell();
        Item currentItem = currentInventoryCell.getItem();

        if (currentItem != null) {
            if (currentItem.getItemInformation().canBeCombinedWithInto() != null)
                gc.fillText("T: Combine", mainApp.getSCREEN_STANDARD_WIDTH() - 200, mainApp.getSCREEN_STANDARD_HEIGHT() - 50);

            if (currentItem.getItemInformation().canBeEquipped())
                if (!currentInventoryCell.isItemEquipped())
                    gc.fillText("R: Equip", mainApp.getSCREEN_STANDARD_WIDTH() - 300, mainApp.getSCREEN_STANDARD_HEIGHT() - 50);
                else
                    gc.fillText("R: Unequip", mainApp.getSCREEN_STANDARD_WIDTH() - 300, mainApp.getSCREEN_STANDARD_HEIGHT() - 50);

            if (currentItem.getItemInformation().canBeUsed())
                gc.fillText("F: Use",  mainApp.getSCREEN_STANDARD_WIDTH() - 400, mainApp.getSCREEN_STANDARD_HEIGHT()  - 50);

            if (currentItem.getItemInformation().canBeDiscarded())
                gc.fillText("K: Discard", mainApp.getSCREEN_STANDARD_WIDTH() - 500, mainApp.getSCREEN_STANDARD_HEIGHT() - 50);
        }
    }


    public void update() {
            if (mainApp.getGraphicsContexts().isEmpty()) {
                logger.severe("Don't have Graphics Context");
                return;
            }

            mainApp.getGraphicsContexts().getFirst().clearRect(0, 0, mainApp.getScreenWidth(), mainApp.getScreenHeight());
            for (int i = 0; i < Model.getDrawableObjects().size(); i++) {
                if (Model.getDrawableObjects().get(i) == null)
                    continue;

                drawSprite(mainApp.getImagesToDraw().get(i), Model.getDrawableObjects().get(i), mainApp.getGraphicsContexts().getFirst());
            }

            if (mainApp.isAiming()){
                mainApp.getGraphicsContexts().get(1).clearRect(0, 0, mainApp.getScreenWidth(), mainApp.getScreenHeight());
                if (mainApp.getGraphicsContexts().size() < 2){
                    logger.severe("Don't have Graphics Context");
                    return;
                }
                drawSprite(mainApp.getCurrentGunSight(), mainApp.getGraphicsContexts().get(1));
            }

            if (mainApp.isInventoryActive()) {
                if (mainApp.getGraphicsContexts().size() < 2) {
                    logger.severe("Don't have Graphics Context");
                    return;
                }
                drawInventory(mainApp.getGraphicsContexts().get(1));
            }

            if (!mainApp.isAiming() && !mainApp.isInventoryActive())
                mainApp.getGraphicsContexts().get(1).clearRect(0, 0, mainApp.getScreenWidth(), mainApp.getScreenHeight());
    }


}
