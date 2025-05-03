package cvut.cz.LevelCreator;

import cvut.cz.FirstLevel;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.MainApplication;
import cvut.cz.Map.MapInformation;
import cvut.cz.Map.MapSlicer;
import cvut.cz.Map.Ventilation;
import cvut.cz.Model.MainPlayerModel;
import cvut.cz.Model.MapModel;
import cvut.cz.Utils.MapLoader;
import cvut.cz.RenderManager;
import cvut.cz.characters.*;
import cvut.cz.items.InventoryCellGeneralInformation;
import cvut.cz.items.InventoryInformation;
import cvut.cz.items.Pointer;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LevelCreator {
    protected final RenderManager renderManager;
    protected final MainApplication mainApplication;


    public LevelCreator(RenderManager renderManager, MainApplication mainApplication) {
        this.renderManager = renderManager;
        this.mainApplication = mainApplication;
    }
    public void createLevel(int currentLevel) {
        switch(currentLevel) {
            case 1:
                FirstLevel firstLevel = new FirstLevel(mainApplication, renderManager);
                firstLevel.createLevel();
                break;
        }

    }

    public GunSight createPistolGunSight(double scaleFactor) {
        URL pathToPistolGunsight = MainApplication.class.getResource("/cvut/cz/pistolgunsight.png");

        GameSpriteSourceInformation pistolGunSightSource = new GameSpriteSourceInformation(pathToPistolGunsight, 0, 0, 16, 16);
        GameSpriteRenderInformation pistolGunSightRender = new GameSpriteRenderInformation(713, 432, (int) Math.ceil(16 * scaleFactor), (int) Math.ceil(16 * scaleFactor),  0, 0);

        renderManager.getImagesToDraw().put(String.valueOf(pathToPistolGunsight), new Image(String.valueOf(pathToPistolGunsight)));

        return new GunSight(pistolGunSightSource, pistolGunSightRender, 28, 28);
    }

    public GunSight createShotGunGunSight(double scaleFactor) {
        URL pathToShotGunGunSight = MainApplication.class.getResource("/cvut/cz/Shotgungunsight.png");

        GameSpriteSourceInformation shotGunSightSource = new GameSpriteSourceInformation(pathToShotGunGunSight, 0, 0, 16, 9);
        GameSpriteRenderInformation shorGunSightRender = new GameSpriteRenderInformation(713, 432, (int) Math.ceil(16 * scaleFactor), (int) Math.ceil(9 * scaleFactor), 0, 0);

        renderManager.getImagesToDraw().put(String.valueOf(pathToShotGunGunSight), new Image(String.valueOf(pathToShotGunGunSight)));

        return new GunSight(shotGunSightSource, shorGunSightRender, 49, 28);

    }

    public Ventilation createVentilation(int worldX, int worldY, double scaleFactor) {
        URL pathToVentilation = MainApplication.class.getResource("/cvut/cz/ventilation.png");

        GameSpriteSourceInformation ventilationSourceInformation = new GameSpriteSourceInformation(pathToVentilation, 0, 0, 99, 46);
        GameSpriteRenderInformation ventilationRenderInformation = new GameSpriteRenderInformation(0, 0, (int) Math.ceil(99 * scaleFactor), (int) Math.ceil(46 * scaleFactor), worldX, worldY);

        return new Ventilation(ventilationSourceInformation, ventilationRenderInformation);
    }

    public void createMap(int currentLevel) {
        List<URL> pathToSections = new ArrayList<>();
        MapSlicer mapSlicer = null;
        URL pathToMap = null;

        switch(currentLevel) {
                case 1:
                pathToMap = MainApplication.class.getResource("/cvut/cz/Level1.png");
                mapSlicer = new MapSlicer(pathToMap, 16, 0, 0, 157, 17270);
                getFirstLevelSections(pathToSections);
                break;
        }

        MapInformation mapInformation = new MapInformation(0, 0, 5, 5);
        MapModel.getMapModel().createMap(mapSlicer, null, pathToSections, mapInformation);

        new Thread(new MapLoader(mainApplication, pathToMap)).start();
    }

    public void createInventory() {
        URL pathToUI = MainApplication.class.getResource("/cvut/cz/UI.png");

        GameSpriteSourceInformation inventorySourceInformation = new GameSpriteSourceInformation(pathToUI, 128, 128, 64, 80);
        GameSpriteRenderInformation inventoryRenderInformation = new GameSpriteRenderInformation((int) mainApplication.getSCREEN_STANDARD_WIDTH() - 400, 20, 384, 480, 0, 0);

        InventoryInformation inventoryInformation = new InventoryInformation(30, 12, 12, 3, (int) mainApplication.getSCREEN_STANDARD_WIDTH() - 364, 80);
        InventoryCellGeneralInformation inventoryCellGeneralInformation = new InventoryCellGeneralInformation(84, 84, 12, 12, 60, 60, 82, 86);

        MainPlayerModel.getMainPlayerModel().createInventory(inventoryInformation, inventoryCellGeneralInformation, inventorySourceInformation, inventoryRenderInformation);

        String inventoryPath = String.valueOf(MainPlayerModel.getMainPlayerModel().getInventory().getGameSpriteSourceInformation().getPathImage());
        renderManager.getImagesToDraw().put(inventoryPath, new Image(inventoryPath));

        Canvas inventoryCanvas = new Canvas(mainApplication.getScreenWidth(), mainApplication.getScreenHeight());
        inventoryCanvas.setMouseTransparent(true);

        mainApplication.getMainContainer().getChildren().add(inventoryCanvas);
        inventoryCanvas.getGraphicsContext2D().setImageSmoothing(false);
        mainApplication.getGraphicsContexts().add(inventoryCanvas.getGraphicsContext2D());

        GameSpriteSourceInformation pointerSourceInformation = new GameSpriteSourceInformation(pathToUI, 192, 128, 24, 25);
        Pointer pointer = new Pointer(pointerSourceInformation);
        MainPlayerModel.getMainPlayerModel().getInventory().setPointer(pointer);
    }

    private void getFirstLevelSections(List<URL> pathToSections) {
        for (int i = 0; i < 30; i++) {
            URL pathToSection = MainApplication.class.getResource("/cvut/cz/MapSections/Level1/mapSection" + i + ".txt");
            pathToSections.add(pathToSection);
        }
    }

}
