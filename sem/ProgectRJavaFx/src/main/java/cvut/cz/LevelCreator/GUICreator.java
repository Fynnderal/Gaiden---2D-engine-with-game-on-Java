package cvut.cz.LevelCreator;

import cvut.cz.MainApplication;
import cvut.cz.Model.MainPlayerModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.logging.Logger;

public class GUICreator {
    private final MainApplication mainApp;

    private static final Logger logger = Logger.getLogger(GUICreator.class.getName());

    private ProgressBar healthBar;
    private VBox inGameMenu;
    private VBox mainMenu;
    private VBox gameOverScreen;
    private HBox healthPanel;
    private VBox dialoguePanel;
    private VBox loadScreen;
    private ProgressBar loadingBar;

    public GUICreator(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    public Button createMenuButton(String text) {
        Button button = new Button(text);

        button.setMaxSize(mainApp.getScreenWidth() / 5, mainApp.getScreenHeight() / 10);
        button.setMinSize(mainApp.getScreenWidth() / 5, mainApp.getScreenHeight() / 10);

        button.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        button.setTextFill(Color.WHITE);
        button.setFont(mainApp.getFont());

        button.setOnMouseEntered(_ -> button.setStyle("-fx-background-color: black; -fx-text-fill: grey;"));
        button.setOnMouseExited(_ -> button.setStyle("-fx-background-color: black; -fx-text-fill: white;"));

        return button;
    }

    public void createMainMenu() {
        Button continueGameButton = createMenuButton("Continue");
        continueGameButton.setOnAction(_ -> mainApp.startGame());

        Button newGameButton = createMenuButton("New Game");
        newGameButton.setOnAction(_ -> mainApp.startGame());

        Button quitGameButton = createMenuButton("Quit");
        quitGameButton.setOnAction(_ -> System.exit(0));

        mainMenu = new VBox();
        mainMenu.getChildren().add(continueGameButton);
        mainMenu.getChildren().add(newGameButton);
        mainMenu.getChildren().add(quitGameButton);
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setSpacing(100);
    }

    public void createLoadScreen() {
        loadScreen = new VBox();
        loadScreen.setAlignment(Pos.CENTER);
        Label loading = new Label("Loading...");
        loading.setFont(Font.loadFont(String.valueOf(mainApp.getPathToFont()), 120));
        loading.setTextFill(Color.RED);

        loadingBar = new ProgressBar();
        loadingBar.setProgress(0);
        loadingBar.setMaxSize(mainApp.getScreenWidth() / 5, mainApp.getScreenHeight() / 25);
        loadingBar.setMinSize(mainApp.getScreenWidth() / 5, mainApp.getScreenHeight() / 25);
        loadingBar.setStyle("-fx-accent: red; -fx-control-inner-background: black;");

        loadScreen.getChildren().addAll(loading, loadingBar);
        loadScreen.setVisible(false);
    }

    public void createHealthPanel() {
        healthPanel = new HBox();
        StackPane.setAlignment(healthPanel, Pos.TOP_LEFT);

        healthBar = (new ProgressBar(MainPlayerModel.getMainPlayerModel().getMainPlayer().getCharacterInformation().getMaxHealth()));
        healthBar.setMaxSize(mainApp.getScreenWidth() / 5, mainApp.getScreenHeight() / 30);
        healthBar.setMinSize(mainApp.getScreenWidth() / 5, mainApp.getScreenHeight() / 30);
        healthBar.setStyle("-fx-accent: red; -fx-control-inner-background: black;");
        StackPane.setMargin(healthBar, new Insets(16, 0, 0, 70));

        ImageView healthBarDecoration = new ImageView();

        URL pathToUI = MainApplication.class.getResource("/cvut/cz/UI.png");
        if (pathToUI == null) {
            logger.severe("Could not find UI.png");
            return;
        }


        healthBarDecoration.setImage(new Image(String.valueOf(pathToUI)));
        healthBarDecoration.setSmooth(false);
        healthBarDecoration.setPreserveRatio(true);
        healthBarDecoration.setFitWidth(50);

        Rectangle2D viewPort = new Rectangle2D(128, 96, 18, 15);
        healthBarDecoration.setViewport(viewPort);
        StackPane.setMargin(healthBarDecoration, new Insets(10, 0, 0, 10));

        healthPanel.getChildren().add(healthBarDecoration);
        healthPanel.getChildren().add(healthBar);

        healthPanel.setMouseTransparent(true);
        healthPanel.setVisible(false);
    }

    public void createGameOverScreen() {
        Button restartButton = createMenuButton("Restart");
        restartButton.setOnAction(_ -> {
            mainApp.startGame();
            MainPlayerModel.getMainPlayerModel().setIsInMenu(false);
        });


        Button quitDeadButton = createMenuButton("Quit");
        quitDeadButton.setOnAction(_ -> mainApp.showMainMenu());

        Label deadLabel = new Label("Dead");
        deadLabel.setFont(Font.loadFont(String.valueOf(mainApp.getPathToFont()), 120));
        deadLabel.setTextFill(Color.RED);

        gameOverScreen = new VBox();
        gameOverScreen.getChildren().add(deadLabel);
        gameOverScreen.getChildren().add(restartButton);
        gameOverScreen.getChildren().add(quitDeadButton);
        gameOverScreen.setVisible(false);
        gameOverScreen.setAlignment(Pos.CENTER);
        gameOverScreen.setSpacing(100);

    }

    public void  createInGameMenu() {
        Button continueButton = createMenuButton("Continue");
        continueButton.setOnAction(_ -> MainPlayerModel.getMainPlayerModel().setIsInMenu(false));

        Button quitButton = createMenuButton("Quit");
        quitButton.setOnAction(_ -> mainApp.showMainMenu());

        inGameMenu = new VBox();
        inGameMenu.getChildren().add(continueButton);
        inGameMenu.getChildren().add(quitButton);
        inGameMenu.setVisible(false);
        inGameMenu.setAlignment(Pos.CENTER);
        inGameMenu.setSpacing(100);
    }

    public void createDialoguePanel() {
        dialoguePanel = new VBox();

        StackPane.setAlignment(dialoguePanel, Pos.BOTTOM_LEFT);
        dialoguePanel.setMaxSize(mainApp.getScreenWidth(),mainApp.getScreenHeight() / 5);
        dialoguePanel.setMinSize(mainApp.getScreenWidth(), mainApp.getScreenHeight() / 5);
        dialoguePanel.setPadding(new Insets(20, 20, 20, 20));

        BackgroundFill backgroundFill = new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY);
        dialoguePanel.setBackground(new Background(backgroundFill));
        dialoguePanel.setVisible(false);
    }

    public ProgressBar getLoadingBar() { return loadingBar; }
    public ProgressBar getHealthBar() { return healthBar; }

    public VBox getGameOverScreen() { return gameOverScreen; }
    public VBox getInGameMenu() { return inGameMenu; }
    public VBox getDialoguePanel() { return dialoguePanel; }
    public VBox getLoadScreen() { return loadScreen; }
    public VBox getMainMenu() { return mainMenu; }

    public HBox getHealthPanel() { return healthPanel; }

}
