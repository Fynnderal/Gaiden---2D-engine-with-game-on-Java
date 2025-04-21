package cvut.cz;

import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.Map.*;
import cvut.cz.Map.Map;
import cvut.cz.Model.Model;
import cvut.cz.characters.*;
import cvut.cz.items.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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

    private boolean isInventoryActive;
    private boolean isAiming;
    private GunSight currentGunSight;
    private Stage stage;

    private long lastUpdate;
    private final long INTERVAL_BETWEEN_UPDATES = 6944444;


    private static List<Image> imagesToDraw;
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
        isInventoryActive = false;
        isAiming = false;
        lastUpdate = 0;

        graphicsContexts = new ArrayList<>();

        Canvas canvas = new Canvas(screenWidth, screenHeight);
        StackPane stackPane = new StackPane(canvas);

        graphicsContexts.addFirst(canvas.getGraphicsContext2D());
        graphicsContexts.getFirst().setImageSmoothing(false);
        graphicsContexts.getFirst().clearRect(0, 0, screenWidth, screenHeight);

        Scene scene = new Scene(stackPane);
        scene.setCursor(Cursor.NONE);
        scene.setFill(Color.BLACK);

        URL pathToTiles = getClass().getResource("/cvut/cz/Level1.png");
        URL pathToCollisions = getClass().getResource("/cvut/cz/mapcollisiontest.txt");
        URL pathToMap0 = getClass().getResource("/cvut/cz/mapSection0.txt");
        List<URL> pathsToSections = new ArrayList<>();
        pathsToSections.add(pathToMap0);
        for (int i = 1; i <= 15; ++i ) {
            pathsToSections.add(getClass().getResource("/cvut/cz/mapSection" + i + ".txt"));
        }


        MapSlicer mapSlicer = new MapSlicer(pathToTiles, 16, 0, 0, 157, 17270);


        MapInformation mapInformation = new MapInformation(10, 10, 3, 3);

//        pathsToSections.add(pathToMap3);

        createMap(mapSlicer, pathToCollisions, pathsToSections, mapInformation);
        //test


        URL pathToUI = getClass().getResource("/cvut/cz/FreeHorrorUi.png");



        GameSpriteSourceInformation playerSourceInformation = new GameSpriteSourceInformation(getClass().getResource("/cvut/cz/testcharacter.png"),0, 0, 360, 360 );
        GameSpriteRenderInformation playerRenderInformation = new GameSpriteRenderInformation(630, 294, 200, 200, 0,0);
        CharacterInformation playerInformation = new CharacterInformation(0, States.IDLE, 90, 100,7);


        GameSpriteSourceInformation gunSightSourceInformation = new GameSpriteSourceInformation(pathToUI, 0, 0, 16, 16);
        GameSpriteRenderInformation gunSightRenderInformation = new GameSpriteRenderInformation(630, 294, 80, 80, 0, 0);
        GunSight gunSight = new GunSight(gunSightSourceInformation, gunSightRenderInformation, 40, 40);

        GameSpriteSourceInformation shotgunSightSourceInformation = new GameSpriteSourceInformation(pathToUI, 84, 49, 14, 9);
        GameSpriteRenderInformation shotgunSightRenderInformation = new GameSpriteRenderInformation(630, 294, 72, 60, 0, 0);
        GunSight shotgunSight = new GunSight(shotgunSightSourceInformation, shotgunSightRenderInformation, 40, 40);


        HashMap<String, GunSight> weaponGunSights = new HashMap();
        weaponGunSights.put("pistol", gunSight);
        weaponGunSights.put("shotgun", shotgunSight);
        createMainCharacter(null, playerInformation, playerSourceInformation, playerRenderInformation, Model.getMap());
        Model.getMainPlayer().setWeaponsAndGunSights(weaponGunSights);


        GameSpriteSourceInformation pistolSourceInformation = new GameSpriteSourceInformation(pathToUI, 0, 0 , 100 ,100);
        ItemInformation pistol = new ItemInformation( "pistol", null, true, false, false, 10);


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("water", "pistol");

        GameSpriteSourceInformation teabagInformation = new GameSpriteSourceInformation(pathToUI, 50, 50,50,40);
        ItemInformation teabag = new ItemInformation("teabag", hashMap, false, false, true, 0 );


        HashMap<String, String> hashMap2 = new HashMap<>();
        hashMap2.put("teabag", "tea");

        GameSpriteSourceInformation waterInformation = new GameSpriteSourceInformation(pathToUI, 100, 50,50,40);
        ItemInformation water = new ItemInformation("water", hashMap2, false, true, true, 0);

        GameSpriteSourceInformation brokenHammerInformation = new GameSpriteSourceInformation(pathToUI, 100, 100,50,50);
        ItemInformation brokenHammer = new ItemInformation("hammer", null, false, false, false, 0);

        GameSpriteSourceInformation teaInformation = new GameSpriteSourceInformation(pathToUI, 5, 5,100,100);
        ItemInformation tea = new ItemInformation("tea", null, false, true, true, 0);


        HashMap<String, Item> possibleItems = new HashMap<>();
        possibleItems.put("water", new Item(waterInformation, water, 1));
        possibleItems.put("teabag", new Item(teabagInformation, teabag, 1));
        possibleItems.put("pistol", new Item(pistolSourceInformation, pistol, 1));
        possibleItems.put("brokenHammer", new Item(brokenHammerInformation, brokenHammer, 1));
        possibleItems.put("tea", new Item(teaInformation, tea, 1));

        List<Item> items = new ArrayList<>();


        items.add(new Item(teaInformation, teabag, 1));
        items.add(new Item(waterInformation,water, 1));
        items.add(new Item(brokenHammerInformation, brokenHammer, 1));
        items.add(new Item(pistolSourceInformation, pistol, 1));




        GameSpriteSourceInformation pistolWorldSourceInformation = new GameSpriteSourceInformation(pathToUI, 0, 0 , 100 ,100);
        GameSpriteRenderInformation pistolWorldRenderInformation = new GameSpriteRenderInformation( 10, 10 , 50 , 50, 100,100);

        HashMap<String, String> hashMapWorld = new HashMap<>();
        hashMap.put("water", "pistol");
//
//        GameSpriteSourceInformation teabagWorldSourceInformation = new GameSpriteSourceInformation(pathToUI, 50, 50,50,40);
//        GameSpriteRenderInformation teabagWorldRenderInformation = new GameSpriteRenderInformation( 10, 10 , 50 , 50, 10,10);


        HashMap<String, String> hashMapWorld2 = new HashMap<>();
        hashMap2.put("teabag", "tea");

        GameSpriteSourceInformation waterWorldSourceInformation = new GameSpriteSourceInformation(pathToUI, 100, 50,50,40);
        GameSpriteRenderInformation waterWorldRenderInformation = new GameSpriteRenderInformation( 10, 10 , 50 , 50, -40,-40);


        GameSpriteSourceInformation brokenHammerWorldSourceInformation = new GameSpriteSourceInformation(pathToUI, 100, 100,50,50);
        GameSpriteRenderInformation brokenHammerWorldRenderInformation = new GameSpriteRenderInformation( 10, 10 , 50 , 50, 260,260);


        GameSpriteSourceInformation teaWorldSourceInformation = new GameSpriteSourceInformation(pathToUI, 5, 5,100,100);
        GameSpriteRenderInformation teaWorldRenderInformation = new GameSpriteRenderInformation( 10, 10 , 50 , 50, 70,70);


        GameSpriteSourceInformation shotGunWorldSourceInformation = new GameSpriteSourceInformation(pathToUI, 0, 0 , 100 ,100);
        GameSpriteRenderInformation shotGunWorldRenderInformation = new GameSpriteRenderInformation( 10, 10 , 50 , 50, 100,100);
        ItemInformation shotgun = new ItemInformation("shotgun", null, true, false, false, 7);

        List<Item> itemsInWorld = new ArrayList<>();
        itemsInWorld.add(new Item(shotGunWorldSourceInformation, shotGunWorldRenderInformation, shotgun, 1));

        CharacterInformation zombieInformation = new CharacterInformation(10, States.IDLE, 100, 100, 3);
        GameSpriteSourceInformation zombieSourceInformation = new GameSpriteSourceInformation(pathToUI, 100, 100,50,50);
        GameSpriteRenderInformation zombieRenderInformation = new GameSpriteRenderInformation( 0, 0, 120, 120, -700, -700);
        EnemyInformation zombieEnemyInformation = new EnemyInformation(-700, -700, 1000, 800, 400);
        WatchMan watchMan = new WatchMan(zombieInformation, zombieSourceInformation, zombieRenderInformation, Model.getMainPlayer(), zombieEnemyInformation);


//        Zombie zombie = new Zombie(zombieInformation, zombieSourceInformation, zombieRenderInformation, Model.getMainPlayer(), zombieEnemyInformation);
//
//        GameSpriteRenderInformation zombieRenderInformation2 = new GameSpriteRenderInformation( 0, 0, 120, 120, -300, -300);
//        Zombie zombie2 = new Zombie(zombieInformation, zombieSourceInformation, zombieRenderInformation2, Model.getMainPlayer(), zombieEnemyInformation);
//
//
//        GameSpriteRenderInformation zombieRenderInformation3 = new GameSpriteRenderInformation( 0, 0, 120, 120, 50, 50);
//        Zombie zombie3 = new Zombie(zombieInformation, zombieSourceInformation, zombieRenderInformation3, Model.getMainPlayer(), zombieEnemyInformation);

        List<GameCharacter> charactersInWorld = new ArrayList<>();
//        charactersInWorld.add(zombie);
//        charactersInWorld.add(zombie2);
//        charactersInWorld.add(zombie3);
        charactersInWorld.add(watchMan);
        setCharactersInWorld(charactersInWorld);


        GameSpriteSourceInformation doorSource = new GameSpriteSourceInformation(pathToUI, 100, 100,50,50);
        GameSpriteRenderInformation doorRender = new GameSpriteRenderInformation( 0, 0, 120, 120, -300, -300);


        GameSpriteSourceInformation keyWorldSourceInformation = new GameSpriteSourceInformation(pathToUI, 50, 50,50,40);
        GameSpriteRenderInformation keyWorldRenderInformation = new GameSpriteRenderInformation( 0, 0 , 50 , 50, 100,100);
        ItemInformation key = new ItemInformation("key", null, false, true, false, 0);
        Item keyItem = new Item(keyWorldSourceInformation, keyWorldRenderInformation, key, 1);
        itemsInWorld.add(keyItem);

        Door door = new Door(doorSource, doorRender, new Collision(-300, -300, 120, 120), keyItem);

        List<MapSpot> mapSpots = new ArrayList<>();
        mapSpots.add(door);
        setMapSpots(mapSpots);
        setItemsInWorld(itemsInWorld);


        Model.getMainPlayer().setItems(items);;

        GameSpriteSourceInformation pointerSourceInformation = new GameSpriteSourceInformation(pathToUI, 198, 132, 24, 25);
        Pointer pointer = new Pointer(pointerSourceInformation);

        InventoryCellGeneralInformation inventoryCellGeneralInformation = new InventoryCellGeneralInformation(98, 98,14, 14, 70, 70, 96, 100);
        InventoryInformation inventoryInformation = new InventoryInformation(inventoryCellGeneralInformation, pointer, possibleItems, 35, 14, 12, 3, (int)SCREEN_STANDARD_WIDTH - 408, 80);
        GameSpriteSourceInformation inventorySourceInformation = new GameSpriteSourceInformation(pathToUI, 128, 131, 64, 76);
        GameSpriteRenderInformation inventoryRenderInformation = new GameSpriteRenderInformation((int)SCREEN_STANDARD_WIDTH - 450, 10, 448, 532, 0, 0);
        graphicsContexts.add(createInventory(stackPane, inventoryInformation, Model.getMainPlayer(), inventorySourceInformation, inventoryRenderInformation));
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

        logger.info(String.format("Finished setting stage\nScreen Width: %f\nScreen Height: %f\nScaling factor: %f", Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight(), RENDER_SCALE_FACTOR));



        LogicManager logic = new LogicManager(this);
        RenderManager render = new RenderManager(this);

        imagesToDraw = new ArrayList<>();
        for (GameSprite gameSprite : Model.getDrawableObjects()) {
            imagesToDraw.add(new Image(String.valueOf(gameSprite.getGameSpriteSourceInformation().getPathImage())));
        }


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= INTERVAL_BETWEEN_UPDATES) {
                    update(logic, render);
                    lastUpdate = now;
                }
            }

        };
        timer.start();
    }

    private void update(LogicManager logic, RenderManager renderManager) {
        logic.update();
        renderManager.update();
    }



    private void createMainCharacter(URL pathToItems, CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation, Map map) {
        Model.createMainPlayer(pathToItems, characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation, map);
    }


    private void setCharactersInWorld(List<GameCharacter> characters) {
        Model.setCharactersInWorld(characters);
    }

    private void setMapSpots(List<MapSpot> mapSpots){
        Model.setMapSpots(mapSpots);
    }
    private void setItemsInWorld(List<Item> items){
        Model.setItemsInWorld(items);
    }

    private void createMap(MapSlicer mapSlicer, URL pathToCollisions,  List<URL> pathsToSections, MapInformation mapInformation) {
        Model.createMap(mapSlicer, pathToCollisions,  pathsToSections, mapInformation);

        List<MapSection> mapSections = Model.getMapConstructor().getMapSections();

        int counter = 0;
        for (MapSection mapSection : mapSections) {

            WritableImage writableImage = new WritableImage(mapSection.getGameSpriteRenderInformation().getTargetWidth(), mapSection.getGameSpriteRenderInformation().getTargetHeight());

            PixelWriter pixelWriter = writableImage.getPixelWriter();
            if (pixelWriter == null)
                logger.severe("PixelWriter is null");

            PixelReader pixelReader;

            int counterTile = 0;
            for (Tile tile: mapSection.getTiles()) {
                System.out.println(counterTile);
                pixelReader = new Image(String.valueOf(tile.getGameSpriteSourceInformation().getPathImage())).getPixelReader();
                copyPixels(pixelReader, pixelWriter, Tile.getSourceTileSize(), Tile.getSourceTileSize(), tile.getGameSpriteSourceInformation().getSourceCoordinateX(), tile.getGameSpriteSourceInformation().getSourceCoordinateY(), tile.getGameSpriteRenderInformation().getWorldCoordinateX() - mapSection.getGameSpriteRenderInformation().getWorldCoordinateX(), tile.getGameSpriteRenderInformation().getWorldCoordinateY() - mapSection.getGameSpriteRenderInformation().getWorldCoordinateY());
                counterTile++;
            }
            try {
                File file = new File("map" + counter + ".png");
                URL pathToImage = file.toURI().toURL();
                saveImage(writableImage, file);
                mapSection.getGameSpriteSourceInformation().setPathImage(pathToImage);
                logger.info("Created MapSection :\nPath to image: " + pathToImage + "\nPath to map: " + pathsToSections.get(counter) + "\nMapSection source X: " + mapSection.getGameSpriteSourceInformation().getSourceCoordinateX() + "\nMapSection source Y: " + mapSection.getGameSpriteSourceInformation().getSourceCoordinateY() + "\nMapSection source width: " + mapSection.getGameSpriteSourceInformation().getSourceWidth() + "\nMapSection source height: " + mapSection.getGameSpriteSourceInformation().getSourceHeight() + "\nMapSection screen X: " + mapSection.getGameSpriteRenderInformation().getScreenCoordinateX() + "\nMapSection screen Y: " + mapSection.getGameSpriteRenderInformation().getScreenCoordinateY() + "\nMapSection screen width: " + mapSection.getGameSpriteRenderInformation().getTargetWidth() + "\nMapSection screen height: " + mapSection.getGameSpriteRenderInformation().getTargetHeight() + "\nMapSection world X: " + mapSection.getGameSpriteRenderInformation().getWorldCoordinateX() + "\nMapSection world Y: " + mapSection.getGameSpriteRenderInformation().getWorldCoordinateY());
            } catch (MalformedURLException e) {
                logger.severe("Problem with path to image; ERROR: " + e.getMessage());
            }
            counter++;
        }
    }


    private void copyPixels(PixelReader pixelReader, PixelWriter pixelWriter, int width, int height, int sourceX, int sourceY, int targetX, int targetY) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                try {
                    pixelWriter.setArgb(targetX + j, targetY + i, pixelReader.getArgb(sourceX + j, sourceY + i));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("we are here:" + e.getMessage() );
                    continue;
                }
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
        Model.createInventory(inventoryInformation, character, gameSpriteSourceInformation, gameSpriteRenderInformation);
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
    public List<GraphicsContext> getGraphicsContexts() { return graphicsContexts;}
    public Stage getStage() {return stage;}
    public List<Image> getImagesToDraw() {return imagesToDraw;}

    public boolean isInventoryActive() { return isInventoryActive;}
    public boolean isAiming() { return isAiming; }
    public GunSight getCurrentGunSight() { return currentGunSight; }

    public void setCurrentGunSight(GunSight gunSight) { this.currentGunSight = gunSight; }
    public void setIsInventoryActive(boolean isInventoryActive) {this.isInventoryActive = isInventoryActive;}
    public void setIsAiming(boolean isAiming) {this.isAiming = isAiming;}
}