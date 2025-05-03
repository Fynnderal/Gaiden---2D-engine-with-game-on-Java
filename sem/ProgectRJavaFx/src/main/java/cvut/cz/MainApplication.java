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

import java.util.*;
import java.util.List;
import java.util.logging.Logger;

import java.net.URL;


public class MainApplication extends Application {
    private static final Logger logger = Logger.getLogger(MainApplication.class.getName());

    private final double SCREEN_STANDARD_WIDTH = 1536;
    private final double SCREEN_STANDARD_HEIGHT = 864;
    private final double RENDER_SCALE_FACTOR;

    private double screenWidth;
    private double screenHeight;

    private StackPane mainContainer;
    private Stage stage;
    private GUICreator guiCreator;

    private URL pathToFont;
    private Font font;

    private volatile boolean running = false;

    private LogicManager logic;
    private RenderManager renderManager;
    private AnimationTimer timer;

    private long lastUpdate;
    private final long INTERVAL_BETWEEN_UPDATES = 6944444;

    private static List<GraphicsContext> graphicsContexts;

    public MainApplication() {
        screenWidth = Screen.getPrimary().getBounds().getWidth();
        screenHeight = Screen.getPrimary().getBounds().getHeight();
        double w_scale_factor = screenWidth / SCREEN_STANDARD_WIDTH;
        double h_scale_factor = screenHeight / SCREEN_STANDARD_HEIGHT;
        RENDER_SCALE_FACTOR = Math.min(w_scale_factor, h_scale_factor);
        screenWidth = SCREEN_STANDARD_WIDTH * RENDER_SCALE_FACTOR;
        screenHeight = SCREEN_STANDARD_HEIGHT * RENDER_SCALE_FACTOR;
    }

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


    public void restartGame() {
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

    public void showMainMenu() {
        restartGame();
        mainContainer.getChildren().add(guiCreator.getMainMenu());
        MainPlayerModel.getMainPlayerModel().setIsInMenu(false);
        guiCreator.getMainMenu().setVisible(true);
    }

    private void update(LogicManager logic, RenderManager renderManager) {
        logic.update();
        renderManager.update();
    }

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

    public void startGame(){
        restartGame();
        logic = new LogicManager(this);
        renderManager = new RenderManager(this);

        lastUpdate = 0;

        prepareGUI();
        new LevelCreator(renderManager, this).createLevel(1);

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

    public void setRunning(Boolean isRunning) { this.running = isRunning;}
}