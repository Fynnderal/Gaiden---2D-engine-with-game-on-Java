package cvut.cz.LevelCreator;

import cvut.cz.FirstLevel;
import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.MainApplication;
import cvut.cz.Map.*;
import cvut.cz.Model.MainPlayerModel;
import cvut.cz.Model.MapModel;
import cvut.cz.SecondLevel;
import cvut.cz.Utils.MapLoader;
import cvut.cz.RenderManager;
import cvut.cz.characters.*;
import cvut.cz.items.InventoryCellGeneralInformation;
import cvut.cz.items.InventoryInformation;
import cvut.cz.items.Item;
import cvut.cz.items.Pointer;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A base class for creating levels in the game.
 * Provides methods for creating levels, maps, inventories, and other game elements.
 */
public class LevelCreator {
    protected final RenderManager renderManager;
    protected final MainApplication mainApplication;

    /**
     * Constructor for the LevelCreator class.
     *
     * @param renderManager The render manager responsible for rendering game objects.
     * @param mainApplication The main application instance.
     */
    public LevelCreator(RenderManager renderManager, MainApplication mainApplication) {
        this.renderManager = renderManager;
        this.mainApplication = mainApplication;
    }

    /**
     * Creates a level based on the specified level number.
     *
     * @param currentLevel The number of the level to create.
     */
    public void createLevel(int currentLevel) {
        switch(currentLevel) {
            case 1:
                FirstLevel firstLevel = new FirstLevel(mainApplication, renderManager);
                firstLevel.createLevel();
                break;
            case 2:
                SecondLevel secondLevel = new SecondLevel(mainApplication, renderManager);
                secondLevel.createLevel();
                break;
        }

    }

    /**
     * Creates a pistol crosshair with the specified scale factor.
     *
     * @param scaleFactor The scale factor for resizing the crosshair.
     * @return A CrossHair object representing the pistol crosshair.
     */
    public CrossHair createPistolCrossHair(double scaleFactor) {
        URL pathToPistolGunsight = MainApplication.class.getResource("/cvut/cz/pistolgunsight.png");

        GameSpriteSourceInformation pistolGunSightSource = new GameSpriteSourceInformation(pathToPistolGunsight, 0, 0, 16, 16);
        GameSpriteRenderInformation pistolGunSightRender = new GameSpriteRenderInformation(713, 432, (int) Math.ceil(16 * scaleFactor), (int) Math.ceil(16 * scaleFactor),  0, 0);

        renderManager.getImagesToDraw().put(String.valueOf(pathToPistolGunsight), new Image(String.valueOf(pathToPistolGunsight)));

        return new CrossHair(pistolGunSightSource, pistolGunSightRender, 28, 28);
    }

    /**
     * Creates a shotgun crosshair with the specified scale factor.
     *
     * @param scaleFactor The scale factor for resizing the crosshair.
     * @return A CrossHair object representing the shotgun crosshair.
     */
    public CrossHair createShotGunCrossHair(double scaleFactor) {
        URL pathToShotGunGunSight = MainApplication.class.getResource("/cvut/cz/Shotgungunsight.png");

        GameSpriteSourceInformation shotGunSightSource = new GameSpriteSourceInformation(pathToShotGunGunSight, 0, 0, 16, 9);
        GameSpriteRenderInformation shorGunSightRender = new GameSpriteRenderInformation(713, 432, (int) Math.ceil(16 * scaleFactor), (int) Math.ceil(9 * scaleFactor), 0, 0);

        renderManager.getImagesToDraw().put(String.valueOf(pathToShotGunGunSight), new Image(String.valueOf(pathToShotGunGunSight)));

        return new CrossHair(shotGunSightSource, shorGunSightRender, 49, 28);

    }

    /**
     * Creates a door object at the specified world coordinates.
     *
     * @param worldX The X-coordinate of the door in the world.
     * @param worldY The Y-coordinate of the door in the world.
     * @param scaleFactor The scale factor for resizing the ventilation sprite.
     * @return A Ventilation object representing the ventilation.
     */
    public Door createDoor(int worldX, int worldY, double scaleFactor, String neededItem, URL pathToSprite) {
        GameSpriteSourceInformation closedDoorSourceInformation = new GameSpriteSourceInformation(pathToSprite, 0, 32, 14, 20);
        GameSpriteRenderInformation closedDoorRenderInformation = new GameSpriteRenderInformation(0, 0, (int) Math.ceil(14 * scaleFactor), (int) Math.ceil(20 * scaleFactor), worldX, worldY);

        GameSpriteSourceInformation openedDoorSourceInformation = new GameSpriteSourceInformation(pathToSprite, 0, 0, 8, 25);
        GameSpriteRenderInformation openedDoorRenderInformation = new GameSpriteRenderInformation(0, 0, (int) Math.ceil(8 * scaleFactor), (int) Math.ceil(25 * scaleFactor), worldX, worldY);

        Collision collision = new Collision(worldX, worldY - 3, closedDoorRenderInformation.getTargetWidth(), 15);
        return new Door(closedDoorSourceInformation, closedDoorRenderInformation, collision, neededItem, new GameSprite(openedDoorSourceInformation, openedDoorRenderInformation){});
    }

    /**
     * Creates a vending machine object at the specified world coordinates.
     * @param worldX The X-coordinate of the vending machine in the world.
     * @param worldY The Y-coordinate of the vending machine in the world.
     * @param scaleFactor The scale factor for resizing the vending machine sprite.
     * @param itemToGive The item that the vending machine gives.
     *
     * @return A VendingMachine object representing the vending machine.
     */
    public VendingMachine createVendingMachine(int worldX, int worldY, double scaleFactor, Item itemToGive) {
        URL pathToVendingMachine = MainApplication.class.getResource("/cvut/cz/vendingmachine.png");

        GameSpriteSourceInformation vendingMachineSourceInformation = new GameSpriteSourceInformation(pathToVendingMachine, 0, 0, 16, 23);
        GameSpriteRenderInformation vendingMachineRenderInformation = new GameSpriteRenderInformation(0, 0, (int) Math.ceil(16 * scaleFactor), (int) Math.ceil(23 * scaleFactor), worldX, worldY);

        return new VendingMachine(vendingMachineSourceInformation, vendingMachineRenderInformation, itemToGive);
    }


    /**
     * Creates a ventilation object at the specified world coordinates.
     *
     * @param worldX The X-coordinate of the ventilation in the world.
     * @param worldY The Y-coordinate of the ventilation in the world.
     * @param scaleFactor The scale factor for resizing the ventilation sprite.
     * @return A Ventilation object representing the ventilation.
     */
    public Ventilation createVentilation(int worldX, int worldY, double scaleFactor) {
        URL pathToVentilation = MainApplication.class.getResource("/cvut/cz/ventilation.png");

        GameSpriteSourceInformation ventilationSourceInformation = new GameSpriteSourceInformation(pathToVentilation, 0, 0, 99, 46);
        GameSpriteRenderInformation ventilationRenderInformation = new GameSpriteRenderInformation(0, 0, (int) Math.ceil(99 * scaleFactor), (int) Math.ceil(46 * scaleFactor), worldX, worldY);

        return new Ventilation(ventilationSourceInformation, ventilationRenderInformation);
    }

    /**
     * Creates a map for the specified level.
     *
     * @param currentLevel The number of the level for which to create the map.
     */
    public void createMap(int currentLevel) {
        List<URL> pathToSections = new ArrayList<>();
        MapSlicer mapSlicer = null;
        URL pathToMap = null;
        URL pathToCollisions = null;
        switch(currentLevel) {
            case 1:
                pathToMap = LevelCreator.class.getResource("/cvut/cz/Level1/Level1.png");
                mapSlicer = new MapSlicer(pathToMap, 16, 0, 0, 157, 17270);
                for (int i = 0; i < 30; i++) {
                    URL pathToSection = LevelCreator.class.getResource("/cvut/cz/Level1/MapSections/mapSection" + i + ".txt");
                    pathToSections.add(pathToSection);
                }
                pathToCollisions = LevelCreator.class.getResource("/cvut/cz/Level1/collisions.txt");
                break;

            case 2:
                System.out.println("wea re here");
                pathToMap = LevelCreator.class.getResource("/cvut/cz/Level2/level2.png");
                mapSlicer = new MapSlicer(pathToMap, 16, 0, 0, 157, 17270);
                for (int i = 0; i < 30; i++) {
                    URL pathToSection = LevelCreator.class.getResource("/cvut/cz/Level2/MapSections/mapSection" + i + ".txt");
                    pathToSections.add(pathToSection);
                }
                pathToCollisions = LevelCreator.class.getResource("/cvut/cz/Level2/collisions.txt");
                break;
        }

        MapInformation mapInformation = new MapInformation(0, 0, 7, 7);
        MapModel.getMapModel().createMap(mapSlicer, pathToCollisions , pathToSections, mapInformation);

        new Thread(new MapLoader(mainApplication, pathToMap)).start();
    }

    /**
     * Creates the player's inventory and initializes its components.
     */
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

        MainPlayerModel.getMainPlayerModel().getInventory().getInventoryController().readAvailableItems(MainPlayerModel.getMainPlayerModel().getPathToItems());
    }
}
