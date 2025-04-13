package cvut.cz;

import cvut.cz.Map.MapSlicer;
import cvut.cz.Map.Tile;
import cvut.cz.characters.Directions;
import cvut.cz.characters.GameCharacter;
import cvut.cz.characters.States;
import cvut.cz.items.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

import java.net.URL;
import java.io.File;
import javafx.scene.layout.StackPane;

import javax.imageio.ImageIO;


public class ViewManager extends Application {
    private static final int SCREEN_STANDARD_WIDTH = 1536;
    private static final int SCREEN_STANDARD_HEIGHT = 864;
    private final double RENDER_SCALE_FACTOR;

    public static Model model;
    private List<GameSprite> drawableObjects;
    private boolean isInventoryActive = false;


    private double screenWidth;
    private double screenHeight;

    private static final Logger logger = Logger.getLogger(ViewManager.class.getName());

    private static List<GraphicsContext> graphicsContexts;

    private static Set<KeyCode> pressedKeys;

    private static InventoryCell selectedCell;
    private static boolean selectingCell;





    public ViewManager() {
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
        setManager(stage);
    }

    public static void main(String[] args) {
        launch();
    }

    private void setManager(Stage stage) {
        selectingCell = false;
        pressedKeys = new HashSet<>();
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

        createMap(mapSlicer, pathToMap, 0, 0, 5, 5, pathToCollisions);
        //test


        scene.setOnKeyPressed(e -> {
            if (selectingCell){
                switch (e.getCode()) {
                    case KeyCode.Q:
                        selectingCell = false;
                        selectedCell  = null;
                        System.out.println("Cancel selecting cell");
                        break;
                    case KeyCode.T:
                        System.out.println("trying to combine: " + selectedCell.getItem().getName() + "and" + model.getInventory().getSelectedInventoryCell().getItem().getName());
                        model.getInventory().combineCells(selectedCell, model.getInventory().getSelectedInventoryCell());
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
                isInventoryActive = !isInventoryActive;
                pressedKeys.clear();
                graphicsContexts.get(1).clearRect(0, 0, screenWidth, screenHeight);
            }
            if (isInventoryActive) {
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
                        selectedCell = model.getInventory().getSelectedInventoryCell();
                        selectingCell = true;
                        System.out.println("First selected: " + selectedCell.getItem().getName());
                        break;

                }
            }
            else{
                pressedKeys.add(e.getCode());
            }
        });
        scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));



        createMainCharacter(null, getClass().getResource("/cvut/cz/testcharacter.png"), 0, 0, 360, 360, 200, 200, 630, 294, 0, States.IDLE, 0, 0, 7);

        ItemInformation pistol = new ItemInformation(0,0 , 100,100, "pistol", pathToTiles, false, null, true, false, false, 1);


        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("water", "pistol");
        ItemInformation teabag = new ItemInformation(50, 50, 50, 40, "teabag",  pathToTiles, false, hashMap, false, true, true, 1);


        HashMap<String, String> hashMap2 = new HashMap();
        hashMap2.put("teabag", "tea");
        ItemInformation water = new ItemInformation(100, 50, 50, 40, "water", pathToTiles, false, hashMap2, false, false, true, 1);

        ItemInformation brokenHammer = new ItemInformation(100, 100, 50, 50, "hammer", pathToTiles, true, null, false, false, false, 1);

        ItemInformation tea = new ItemInformation(5, 5, 100 ,100, "tea", pathToTiles, false, null, false, true, true, 1);


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


        InventoryCellInformation inventoryCellInformation = new InventoryCellInformation(98, 98, 35, 14, 12, 3, SCREEN_STANDARD_WIDTH - 408, 80, 14, 14, 70, 70, 96, 100);
        URL pathToUI = getClass().getResource("/cvut/cz/FreeHorrorUi.png");
        Pointer pointer = new Pointer(pathToUI, 198, 132, 24, 25);

        graphicsContexts.add(createInventory(stackPane,possibleItems,  inventoryCellInformation, pointer, model.getMainPlayer(), pathToUI, 128, 131, 64, 76, SCREEN_STANDARD_WIDTH -450, 10, 448, 532));
        //test


        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> update()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

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
    }

    private void drawSprite(GameSprite sprite, GraphicsContext gc) {
        gc.drawImage(new Image(String.valueOf(sprite.getPathImage())), sprite.sourceCoordinateX, sprite.sourceCoordinateY, sprite.sourceWidth, sprite.sourceHeight, sprite.screenCoordinateX * RENDER_SCALE_FACTOR, sprite.screenCoordinateY * RENDER_SCALE_FACTOR, sprite.targetWidth * RENDER_SCALE_FACTOR, sprite.targetHeight * RENDER_SCALE_FACTOR);
    }

    private void update() {
        checkControls();
        graphicsContexts.getFirst().clearRect(0, 0, screenWidth, screenHeight);
        for (GameSprite sprite : drawableObjects) {
            if (sprite == null)
                continue;

            drawSprite(sprite, ViewManager.graphicsContexts.getFirst());
        }
        if (isInventoryActive) {
            graphicsContexts.get(1).setFill(Color.RED);
            graphicsContexts.get(1).setFont(new Font("Arial", 20));
            graphicsContexts.get(1).clearRect(0, 0, screenWidth, screenHeight);
            drawSprite(model.getInventory(), graphicsContexts.get(1));
            for (InventoryCell cell : model.getInventory().getCells()) {
                if (cell.getItem() != null) {
                    drawSprite(cell.getItem(), graphicsContexts.get(1));
                    graphicsContexts.get(1).fillText(String.valueOf(cell.getItem().getAmount()), cell.getCoordinateX() + model.getInventory().getCellInformation().getAmountLabelCoordinateX(), cell.getCoordinateY() + model.getInventory().getCellInformation().getAmountLabelCoordinateY());
                }
            }
            drawSprite(model.getInventory().getPointer(), graphicsContexts.get(1));
            Item currentItem = model.getInventory().getSelectedInventoryCell().getItem();
            if (currentItem != null) {
                if (currentItem.getCanBeCombinedWithInto() != null)
                    graphicsContexts.get(1).fillText("T: Combine", SCREEN_STANDARD_WIDTH - 200, SCREEN_STANDARD_HEIGHT - 50);
                if (!currentItem.getIsBroken()) {
                    if (currentItem.getCanBeEquipped())
                        graphicsContexts.get(1).fillText("R: Equip", SCREEN_STANDARD_WIDTH - 300, SCREEN_STANDARD_HEIGHT - 50);

                    if (currentItem.getCanBeUsed())
                        graphicsContexts.get(1).fillText("F: Use", SCREEN_STANDARD_WIDTH - 400, SCREEN_STANDARD_HEIGHT - 50);

                }

                if (currentItem.getCanBeDiscarded())
                    graphicsContexts.get(1).fillText("K: Discard", SCREEN_STANDARD_WIDTH - 500, SCREEN_STANDARD_HEIGHT - 50);
            }
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

    private void moveInventoryPointer(Directions direction) {
        model.getInventory().movePointer(direction);
    }

    private void movePlayer(Directions direction) {
        model.movePlayer(direction);
    }

    private void createMap(MapSlicer mapSlicer, URL pathToMap, int mapCoordinateX, int mapCoordinateY, int mapTargetScaleFactorX, int mapTargetScaleFactorY, URL pathToCollisions) {
        model.createMap(mapSlicer, pathToMap, mapCoordinateX, mapCoordinateY, mapTargetScaleFactorX, mapTargetScaleFactorY, pathToCollisions);
        List<Tile> mapTiles = model.getMapConstructor().getMapTiles();
        WritableImage writableImage = new WritableImage(model.getMapConstructor().getMapImageWidth(), model.getMapConstructor().getMapImageHeight());

        PixelWriter pixelWriter = writableImage.getPixelWriter();
        if (pixelWriter == null)
            logger.severe("PixelWriter is null");

        PixelReader pixelReader;
        for (Tile tile: mapTiles) {
            pixelReader = new Image(String.valueOf(tile.getPathImage())).getPixelReader();
            copyPixels(pixelReader, pixelWriter, Tile.getSourceTileSize(), Tile.getSourceTileSize(), tile.getSourceCoordinateX(), tile.getSourceCoordinateY(), tile.getWorldCoordinateX(), tile.getWorldCoordinateY());
        }
        try {

            File file = new File("map.png");
            URL pathToImage = file.toURI().toURL();
            saveImage(writableImage, file);
            model.getMap().setPathToMap(pathToImage);
            drawableObjects.addFirst( model.getMap());
            logger.info("Created Map:\nPath to image: " + pathToImage + "\nPath to map: " + pathToMap + "\nMap source X: " + model.getMap().getSourceCoordinateX() + "\nMap source Y: " + model.getMap().getSourceCoordinateY() + "\nMap source width: " + model.getMap().getSourceWidth() + "\nMap source height: " + model.getMap().getSourceHeight() + "\nMap screen X: " + model.getMap().getScreenCoordinateX() + "\nMap screen Y: " + model.getMap().getScreenCoordinateY() + "\nMap screen width: " + model.getMap().getTargetWidth() + "\nMap screen height: " + model.getMap().getTargetHeight() + "\nMap world X: " +  model.getMap().getWorldCoordinateX() + "\nMap world Y: " + model.getMap().getWorldCoordinateY());
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

    private void createMainCharacter(URL pathToItems, URL pathToSprite, int sourceX, int sourceY, int sourceWidth, int sourceHeight, int screenWidth, int screenHeight, int worldX, int worldY, int attackPower, States currentState, int currentHealth, int maxHealth, int speed) {
        model.createMainPlayer(pathToItems, pathToSprite, sourceX, sourceY, sourceWidth, sourceHeight, screenWidth, screenHeight, worldX, worldY, attackPower, currentState, currentHealth, maxHealth, speed);
        drawableObjects.add(1, model.getMainPlayer());
    }

    private GraphicsContext createInventory(StackPane stackPane, HashMap<String, ItemInformation> possibleItems, InventoryCellInformation inventoryCellInformation, Pointer pointer, GameCharacter character, URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int targetCoordinateX, int targetCoordinateY, int targetWidth, int targetHeight) {
        model.createInventory(possibleItems, inventoryCellInformation, pointer, character, pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, targetCoordinateX, targetCoordinateY, targetWidth, targetHeight);
        Canvas inventoryCanvas = new Canvas(screenWidth, screenHeight);
        stackPane.getChildren().add(inventoryCanvas);
        inventoryCanvas.getGraphicsContext2D().setImageSmoothing(false);
        return inventoryCanvas.getGraphicsContext2D();
    }

}