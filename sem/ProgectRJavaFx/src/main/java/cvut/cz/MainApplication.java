package cvut.cz;

import cvut.cz.LevelCreator.GUICreator;
import cvut.cz.LevelCreator.LevelCreator;
import cvut.cz.Model.MainPlayerModel;
import cvut.cz.Model.MapModel;
import cvut.cz.Utils.LoggerController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

import java.net.URL;

/**
 * Main application class for the game.
 * Handles the initialization, configuration, and management of the game lifecycle.
 */
public class MainApplication extends Application {
    private static final Logger logger = Logger.getLogger(MainApplication.class.getName());

    // Screen Width for which all sizes are calculated
    private final double SCREEN_STANDARD_WIDTH = 1536;
    // Screen Height for which all sizes are calculated
    private final double SCREEN_STANDARD_HEIGHT = 864;
    // Scaling factor for the current screen if its size is different from the standard
    private final double RENDER_SCALE_FACTOR;

    // Current screen width and height
    private double screenWidth;
    private double screenHeight;

    // Main container for the GUI
    private StackPane mainContainer;
    // Main stage
    private Stage stage;
    private GUICreator guiCreator;

    // path to custom font
    private URL pathToFont;
    private Font font;


    private volatile boolean running = false;

    private LogicManager logic;
    private RenderManager renderManager;
    private AnimationTimer timer;

    // Time of the last update
    private long lastUpdate;
    // Time between updates of logic (about 144 fps)
    private final long INTERVAL_BETWEEN_UPDATES = 6944444;

    private static List<GraphicsContext> graphicsContexts;

    /**
     * Constructs the MainApplication and calculates the render scale factor.
     */
    public MainApplication() {
        screenWidth = Screen.getPrimary().getBounds().getWidth();
        screenHeight = Screen.getPrimary().getBounds().getHeight();

        double w_scale_factor = screenWidth / SCREEN_STANDARD_WIDTH;
        double h_scale_factor = screenHeight / SCREEN_STANDARD_HEIGHT;

        RENDER_SCALE_FACTOR = Math.min(w_scale_factor, h_scale_factor);
        screenWidth = SCREEN_STANDARD_WIDTH * RENDER_SCALE_FACTOR;
        screenHeight = SCREEN_STANDARD_HEIGHT * RENDER_SCALE_FACTOR;
    }

    /**
     * Starts the application and configures the stage.
     *
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        configureManager(stage);
    }


    public static void main(String[] args) {
        if (args.length > 0){
            if (args[0].equals("loggersOff"))
                LoggerController.offLoggers();
        }
        launch();
    }

    /**
     * Creates the main scene for the application.
     *
     * @return The created scene.
     */
    private Scene createScene() {
        Scene scene = new Scene(mainContainer);
        scene.setFill(Color.BLACK);

        pathToFont = MainApplication.class.getResource("/cvut/cz/mago3.ttf");
        font = Font.loadFont(String.valueOf(pathToFont), 50);

        guiCreator.createMainMenu();
        mainContainer.getChildren().add(guiCreator.getMainMenu());
        scene.setCursor(Cursor.CROSSHAIR);

        return scene;
    }

    /**
     * Configures the managers and initializes the stage.
     *
     * @param stage The primary stage for this application.
     */
    private void configureManager(Stage stage) {
        guiCreator = new GUICreator(this);

        graphicsContexts = new ArrayList<>();
        mainContainer = new StackPane();
        mainContainer.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));


        stage.setScene(createScene());
        stage.setTitle("Project R");
        stage.setFullScreen(true);


        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setResizable(false);
        stage.show();

        logger.info(String.format("Finished setting stage\nScreen Width: %f\nScreen Height: %f\nScaling factor: %f", Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight(), RENDER_SCALE_FACTOR));
    }


    /**
     * Stops the game and clears all resources.
     */
    public void stopGame() {
        guiCreator.getMainMenu().setVisible(false);
        mainContainer.getChildren().clear();

        graphicsContexts.clear();
        MapModel.getMapModel().getDrawableObjects().clear();
        MapModel.getMapModel().getUpdatableObjects().clear();


        if (timer != null) {
            timer.stop();
        }

        timer = null;
        logic = null;
        renderManager = null;
        running = false;
    }

    /**
     * Displays the main menu.
     */
    public void showMainMenu() {
        stopGame();
        mainContainer.getChildren().add(guiCreator.getMainMenu());
        MainPlayerModel.getMainPlayerModel().setIsInMenu(false);
        guiCreator.getMainMenu().setVisible(true);
    }

    /**
     * Updates the game logic and rendering.
     *
     * @param logic The logic manager.
     * @param renderManager The render manager.
     */
    private void update(LogicManager logic, RenderManager renderManager) {
        renderManager.update();
        logic.update();
    }

    /**
     * Prepares the GUI for the game.
     */
    private void prepareGUI(){
        Canvas canvas = new Canvas(screenWidth, screenHeight);
        mainContainer.getChildren().add(canvas);

        guiCreator.createLoadScreen();
        mainContainer.getChildren().add(guiCreator.getLoadScreen());
        guiCreator.getLoadingBar().setProgress(0);
        guiCreator.getLoadScreen().setVisible(true);

        guiCreator.createInGameMenu();

        graphicsContexts.addFirst(canvas.getGraphicsContext2D());
        graphicsContexts.getFirst().setImageSmoothing(false);
        graphicsContexts.getFirst().clearRect(0, 0, getScreenWidth(), getScreenHeight());

        guiCreator.createGameOverScreen();
        guiCreator.createDialoguePanel();
        guiCreator.createInGameMenu();

        mainContainer.getChildren().add(guiCreator.getGameOverScreen());
        mainContainer.getChildren().add(guiCreator.getDialoguePanel());
        mainContainer.getChildren().add(guiCreator.getInGameMenu());
    }

    /**
     * Starts the game and initializes all components.
     *
     * @param deleteAllData Flag indicating whether to delete all data and start a new game from scratch.
     */
    public void startGame(boolean deleteAllData){
        stopGame();
        logic = new LogicManager(this);
        renderManager = new RenderManager(this);

        lastUpdate = 0;

        prepareGUI();

        String pathToItems = "playerItems.json";
        String pathToLevel = "currentLevel.txt";

        File levelFile = new File(pathToLevel);
        if (!levelFile.exists()) {
            try {
                if (!levelFile.createNewFile())
                    throw new IOException("Can't create file");

            } catch (IOException e) {
                logger.severe("Error while creating file with player items: " + levelFile.getAbsolutePath());
            }
        }

        File itemsFile = new File(pathToItems);
        if (deleteAllData) {
            if (itemsFile.exists()) {
                if (!itemsFile.delete())
                    logger.severe("Error while deleting player items: " + itemsFile.getAbsolutePath());
            }
            try (FileWriter fileWriter = new FileWriter(levelFile, false)){
                fileWriter.write("1");
            } catch (IOException e) {
                logger.severe("Error while writing to file with player items: " + levelFile.getAbsolutePath());
            }
        }

        try {
            if (!itemsFile.createNewFile())
                throw new IOException("Can't create file");

        } catch (IOException e) {
            logger.severe("Error while creating file with player items: " + itemsFile.getAbsolutePath());
        }


        try (FileReader fileReader = new FileReader(levelFile)) {
            Scanner scanner = new Scanner(fileReader);
            MapModel.getMapModel().setCurrentLevel(scanner.nextInt());
        } catch (IOException e) {
            logger.severe("Error while reading file with player items: " + levelFile.getAbsolutePath());
        }
        System.out.println(MapModel.getMapModel().getCurrentLevel());
        MainPlayerModel.getMainPlayerModel().setPathToItems(pathToItems);
        MapModel.getMapModel().setPathToFileWithLevel(pathToLevel);

        new LevelCreator(renderManager, this).createLevel(MapModel.getMapModel().getCurrentLevel());

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!running)
                    return;

                if (now - lastUpdate >= INTERVAL_BETWEEN_UPDATES) {
                    update(logic, renderManager);
                    lastUpdate = now;
                }

            }

        };
        timer.start();

    }

    public double getSCREEN_STANDARD_WIDTH() {return SCREEN_STANDARD_WIDTH;}
    public double getSCREEN_STANDARD_HEIGHT() {return SCREEN_STANDARD_HEIGHT;}
    public double getRENDER_SCALE_FACTOR() {return RENDER_SCALE_FACTOR;}

    public double getScreenWidth() {return screenWidth;}
    public double getScreenHeight() {return screenHeight;}

    public List<GraphicsContext> getGraphicsContexts() {return graphicsContexts;}

    public Stage getStage() {return stage;}
    public URL getPathToFont() { return pathToFont;}
    public Font getFont() { return font; }
    public StackPane getMainContainer() {return mainContainer;}
    public GUICreator getGuiCreator() {return guiCreator;}

    public RenderManager getRenderManager() { return renderManager; }

    /**
     * Sets the running state of the game.
     *
     * @param isRunning True if the game is running, false otherwise.
     */
    public void setRunning(Boolean isRunning) { this.running = isRunning;}
}