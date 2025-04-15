package cvut.cz;

import cvut.cz.Map.MapSlicer;
import cvut.cz.Map.Tile;
import cvut.cz.characters.*;
import cvut.cz.items.*;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

import java.net.URL;
import java.io.File;
import javafx.scene.layout.StackPane;

import javax.imageio.ImageIO;


public class MainApplication extends Application {
    private static final Logger logger = Logger.getLogger(MainApplication.class.getName());

    private final double SCREEN_STANDARD_WIDTH = 1536;
    private final double SCREEN_STANDARD_HEIGHT = 864;
    private final double RENDER_SCALE_FACTOR;

    private double screenWidth;
    private double screenHeight;

    private Model model;
    private List<GameSprite> drawableObjects;
    private boolean isInventoryActive;
    private Stage stage;


    private static List<GraphicsContext> graphicsContexts;


    public MainApplication() {
        screenWidth = Screen.getPrimary().getBounds().getWidth();
        screenHeight = Screen.getPrimary().getBounds().getHeight();
        double w_scale_factor =  screenWidth/  SCREEN_STANDARD_WIDTH;
        double h_scale_factor = screenHeight / SCREEN_STANDARD_HEIGHT;
        RENDER_SCALE_FACTOR = Math.min(w_scale_factor, h_scale_factor);
        screenWidth = SCREEN_STANDARD_WIDTH * RENDER_SCALE_FACTOR;
        screenHeight = SCREEN_STANDARD_HEIGHT * RENDER_SCALE_FACTOR;
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        setManager(stage);
    }

    public static void main(String[] args) {
        launch();
    }

    private void setManager(Stage stage) {
        model = new Model();
        drawableObjects = new ArrayList<>();
        graphicsContexts = new ArrayList<>();

        Canvas canvas = new Canvas(screenWidth, screenHeight);
        StackPane stackPane = new StackPane(canvas);

        graphicsContexts.addFirst(canvas.getGraphicsContext2D());
        graphicsContexts.getFirst().setImageSmoothing(false);
        graphicsContexts.getFirst().clearRect(0, 0, screenWidth, screenHeight);

        Scene scene = new Scene(stackPane);

        URL pathToTiles = getClass().getResource("/cvut/cz/Tileset.png");
        URL pathToCollisions = getClass().getResource("/cvut/cz/mapcollisiontest.txt");
        URL pathToMap = getClass().getResource("/cvut/cz/maptest.txt");

        MapSlicer mapSlicer = new MapSlicer(pathToTiles, 16, 0, 0, 31, 496);

        createMap(mapSlicer, pathToCollisions, pathToMap, 0, 0, 5, 5);
        //test





        GameSpriteSourceInformation playerSourceInformation = new GameSpriteSourceInformation(getClass().getResource("/cvut/cz/testcharacter.png"),0, 0, 360, 360 );
        GameSpriteRenderInformation playerRenderInformation = new GameSpriteRenderInformation(0, 0, 200, 200, 630, 294);
        CharacterInformation playerInformation = new CharacterInformation(0, States.IDLE, 0, 0,7);
        createMainCharacter(null, playerInformation, playerSourceInformation, playerRenderInformation);



        GameSpriteSourceInformation pistolSourceInformation = new GameSpriteSourceInformation(pathToTiles, 0, 0 , 100 ,100);
        ItemInformation pistol = new ItemInformation(pistolSourceInformation, null, "pistol", null, true, false, false);


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("water", "pistol");

        GameSpriteSourceInformation teabagInformation = new GameSpriteSourceInformation(pathToTiles, 50, 50,50,40);
        ItemInformation teabag = new ItemInformation(teabagInformation, null, "teabag", hashMap, false, false, true);


        HashMap<String, String> hashMap2 = new HashMap<>();
        hashMap2.put("teabag", "tea");

        GameSpriteSourceInformation waterInformation = new GameSpriteSourceInformation(pathToTiles, 100, 50,50,40);
        ItemInformation water = new ItemInformation(waterInformation, null,"water", hashMap2, false, true, true);

        GameSpriteSourceInformation brokenHammerInformation = new GameSpriteSourceInformation(pathToTiles, 100, 100,50,50);
        ItemInformation brokenHammer = new ItemInformation(brokenHammerInformation, null, "hammer", null, false, false, false);

        GameSpriteSourceInformation teaInformation = new GameSpriteSourceInformation(pathToTiles, 5, 5,100,100);
        ItemInformation tea = new ItemInformation(teaInformation, null,"tea", null, false, true, true);


        HashMap<String, ItemInformation> possibleItems = new HashMap<>();
        possibleItems.put("water", water);
        possibleItems.put("teabag", teabag);
        possibleItems.put("pistol", pistol);
        possibleItems.put("brokenHammer", brokenHammer);
        possibleItems.put("tea", tea);

        Item[] items = new Item[]{
            new Item(teabag),
            new Item(water),
            new Item(brokenHammer),
            new Item(pistol),
        };
        model.getMainPlayer().setItems(items);
        try {
            URL file = new File("test_items_my.json").toURI().toURL();
            model.getMainPlayer().writeAvailableItems(file);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        URL pathToUI = getClass().getResource("/cvut/cz/FreeHorrorUi.png");
        GameSpriteSourceInformation pointerSourceInformation = new GameSpriteSourceInformation(pathToUI, 198, 132, 24, 25);
        Pointer pointer = new Pointer(pointerSourceInformation);

        InventoryCellGeneralInformation inventoryCellGeneralInformation = new InventoryCellGeneralInformation(98, 98,14, 14, 70, 70, 96, 100);
        InventoryInformation inventoryInformation = new InventoryInformation(inventoryCellGeneralInformation, pointer, possibleItems, 35, 14, 12, 3, (int)SCREEN_STANDARD_WIDTH - 408, 80);
        GameSpriteSourceInformation inventorySourceInformation = new GameSpriteSourceInformation(pathToUI, 128, 131, 64, 76);
        GameSpriteRenderInformation inventoryRenderInformation = new GameSpriteRenderInformation((int)SCREEN_STANDARD_WIDTH - 450, 10, 448, 532, 0, 0);
        graphicsContexts.add(createInventory(stackPane, inventoryInformation, model.getMainPlayer(), inventorySourceInformation, inventoryRenderInformation));
        //test

        stage.setScene(scene);
        stage.setScene(scene);
        stage.setTitle("Project R");
        stage.setFullScreen(true);


        stage.fullScreenProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                stage.setFullScreen(true);
            }
        });

        stage.setResizable(false);
        stage.show();

        logger.info(String.format("Finished setting stage\nScreen Width: %f\n Screen Height: %f, Scaling factor: %f", Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight(), RENDER_SCALE_FACTOR));



        LogicThread logic = new LogicThread(this);
        Thread logicThread = new Thread(logic);
        logicThread.setDaemon(true);
        logicThread.start();

        RenderManager render = new RenderManager(this);
        render.start();
    }





    private void createMainCharacter(URL pathToItems, CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        model.createMainPlayer(pathToItems, characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation);
        drawableObjects.add(1, model.getMainPlayer());
    }

    private void createMap(MapSlicer mapSlicer, URL pathToCollisions,  URL pathToMap, int mapCoordinateX, int mapCoordinateY, int mapTargetScaleFactorX, int mapTargetScaleFactorY) {
        model.createMap(mapSlicer, pathToCollisions,  pathToMap, mapCoordinateX, mapCoordinateY, mapTargetScaleFactorX, mapTargetScaleFactorY);
        List<Tile> mapTiles = model.getMapConstructor().getMapTiles();
        WritableImage writableImage = new WritableImage(model.getMapConstructor().getMapImageWidth(), model.getMapConstructor().getMapImageHeight());

        PixelWriter pixelWriter = writableImage.getPixelWriter();
        if (pixelWriter == null)
            logger.severe("PixelWriter is null");

        PixelReader pixelReader;
        for (Tile tile: mapTiles) {
            pixelReader = new Image(String.valueOf(tile.getGameSpriteSourceInformation().getPathImage())).getPixelReader();
            copyPixels(pixelReader, pixelWriter, Tile.getSourceTileSize(), Tile.getSourceTileSize(), tile.getGameSpriteSourceInformation().getSourceCoordinateX(), tile.getGameSpriteSourceInformation().getSourceCoordinateY(), tile.getGameSpriteRenderInformation().getWorldCoordinateX(), tile.getGameSpriteRenderInformation().getWorldCoordinateY());
        }
        try {

            File file = new File("map.png");
            URL pathToImage = file.toURI().toURL();
            saveImage(writableImage, file);
            model.getMap().getGameSpriteSourceInformation().setPathImage(pathToImage);
            drawableObjects.addFirst( model.getMap());
            logger.info("Created Map:\nPath to image: " + pathToImage + "\nPath to map: " + pathToMap + "\nMap source X: " + model.getMap().getGameSpriteSourceInformation().getSourceCoordinateX() + "\nMap source Y: " + model.getMap().getGameSpriteSourceInformation().getSourceCoordinateY() + "\nMap source width: " + model.getMap().getGameSpriteSourceInformation().getSourceWidth() + "\nMap source height: " + model.getMap().getGameSpriteSourceInformation().getSourceHeight() + "\nMap screen X: " + model.getMap().getGameSpriteRenderInformation().getScreenCoordinateX() + "\nMap screen Y: " + model.getMap().getGameSpriteRenderInformation().getScreenCoordinateY() + "\nMap screen width: " + model.getMap().getGameSpriteRenderInformation().getTargetWidth() + "\nMap screen height: " + model.getMap().getGameSpriteRenderInformation().getTargetHeight() + "\nMap world X: " +  model.getMap().getGameSpriteRenderInformation().getWorldCoordinateX() + "\nMap world Y: " + model.getMap().getGameSpriteRenderInformation().getWorldCoordinateY());
        } catch (MalformedURLException e) {
            logger.severe("Problem with path to image; ERROR: " + e.getMessage());
        }
    }

    private void copyPixels(PixelReader pixelReader, PixelWriter pixelWriter, int width, int height, int sourceX, int sourceY, int targetX, int targetY) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelWriter.setArgb(targetX + j, targetY + i, pixelReader.getArgb(sourceX + j, sourceY + i));
            }
        }
    }

    private void saveImage(WritableImage image, File file) {
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            logger.info("Map was saved to: " + file.toURI());
        } catch (IOException | NullPointerException e) {
            logger.severe("Unable to save map to " + file.toURI() + "; ERROR: " + e.getMessage());
        }
    }



    private GraphicsContext createInventory(StackPane stackPane, InventoryInformation inventoryInformation, PlayableCharacter character, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        model.createInventory(inventoryInformation, character, gameSpriteSourceInformation, gameSpriteRenderInformation);
        Canvas inventoryCanvas = new Canvas(screenWidth, screenHeight);
        stackPane.getChildren().add(inventoryCanvas);
        inventoryCanvas.getGraphicsContext2D().setImageSmoothing(false);
        return inventoryCanvas.getGraphicsContext2D();
    }

    public double getSCREEN_STANDARD_WIDTH() { return SCREEN_STANDARD_WIDTH;}
    public double getSCREEN_STANDARD_HEIGHT() { return SCREEN_STANDARD_HEIGHT;}
    public double getRENDER_SCALE_FACTOR() { return RENDER_SCALE_FACTOR;}
    public double getScreenWidth() { return screenWidth;}
    public double getScreenHeight() { return screenHeight;}
    public List<GameSprite> getDrawableObjects() { return drawableObjects;}
    public List<GraphicsContext> getGraphicsContexts() { return graphicsContexts;}
    public Model getModel() {return model;}
    public Stage getStage() {return stage;}

    synchronized public boolean isInventoryActive() { return isInventoryActive;}

    synchronized public void setIsInventoryActive(boolean isInventoryActive) {this.isInventoryActive = isInventoryActive;}

}