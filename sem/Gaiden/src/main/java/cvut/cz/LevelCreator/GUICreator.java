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

/**
 * A class responsible for creating and managing the graphical user interface (GUI) elements of the game.
 * This includes menus, health panels, loading screens, and other UI components.
 */
public class GUICreator {
    private final MainApplication mainApp;


    // Screen that is displayed when the game is paused
    private VBox inGameMenu;
    // Main menu of the game
    private VBox mainMenu;

    // Screen that is displayed when the player dies
    private VBox gameOverScreen;
    // Screen that is displayed when the game is loading
    private VBox loadScreen;

    // Displays the health of the player
    private HBox healthPanel;
    // Displays dialogue while the player is interacting with an NPC
    private VBox dialoguePanel;

    private ProgressBar loadingBar;
    private ProgressBar healthBar;

    /**
     * Constructor for the GUICreator class.
     *
     * @param mainApp The main application instance.
     */
    public GUICreator(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Creates a styled menu button with the specified text.
     *
     * @param text The text to display on the button.
     * @return A Button object with the specified text and styling.
     */
    public Button createMenuButton(String text) {
        Button button = new Button(text);

        button.setMaxSize(mainApp.getScreenWidth() / 5, mainApp.getScreenHeight() / 10);
        button.setMinSize(mainApp.getScreenWidth() / 5, mainApp.getScreenHeight() / 10);

        button.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        button.setTextFill(Color.WHITE);
        button.setFont(mainApp.getFont());

        // if the cursor is over the button, change the color to grey
        button.setOnMouseEntered(_ -> button.setStyle("-fx-background-color: black; -fx-text-fill: grey;"));
        // if the cursor is not over the button, change the color back to white
        button.setOnMouseExited(_ -> button.setStyle("-fx-background-color: black; -fx-text-fill: white;"));

        return button;
    }

    /**
     * Creates the main menu of the game with options to continue, start a new game, or quit.
     */
    public void createMainMenu() {
        Button continueGameButton = createMenuButton("Continue");
        continueGameButton.setOnAction(_ -> mainApp.startGame(false));

        Button newGameButton = createMenuButton("New Game");
        newGameButton.setOnAction(_ -> mainApp.startGame(true));

        Button quitGameButton = createMenuButton("Quit");
        quitGameButton.setOnAction(_ -> System.exit(0));

        Label gameLabel = new Label("Gaiden");
        gameLabel.setFont(Font.loadFont(String.valueOf(mainApp.getPathToFont()), 140));
        gameLabel.setTextFill(Color.RED);

        mainMenu = new VBox();

        mainMenu.getChildren().add(gameLabel);
        mainMenu.getChildren().add(continueGameButton);
        mainMenu.getChildren().add(newGameButton);
        mainMenu.getChildren().add(quitGameButton);
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setSpacing(100);
    }

    /**
     * Creates the loading screen with a progress bar and a loading label.
     */
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

    /**
     * Creates the health panel displaying the player's health with a progress bar and a heart icon.
     */
    public void createHealthPanel() {
        healthPanel = new HBox();
        StackPane.setAlignment(healthPanel, Pos.TOP_LEFT);

        healthBar = (new ProgressBar(MainPlayerModel.getMainPlayerModel().getMainPlayer().getCharacterInformation().getMaxHealth()));
        healthBar.setMaxSize(mainApp.getScreenWidth() / 5, mainApp.getScreenHeight() / 30);
        healthBar.setMinSize(mainApp.getScreenWidth() / 5, mainApp.getScreenHeight() / 30);
        healthBar.setStyle("-fx-accent: red; -fx-control-inner-background: black;");
        StackPane.setMargin(healthBar, new Insets(16, 0, 0, 70));

        // Creates image of a heart for health bar
        ImageView healthBarDecoration = new ImageView();

        URL pathToUI = MainApplication.class.getResource("/cvut/cz/UI.png");


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

    /**
     * Creates the game over screen with options to restart or quit.
     */
    public void createGameOverScreen() {
        Button restartButton = createMenuButton("Restart");
        restartButton.setOnAction(_ -> {
            mainApp.startGame(false);
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

    /**
     * Creates the in-game menu with options to continue or quit.
     */
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

    /**
     * Creates the dialogue panel for displaying dialogues.
     */
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

    /**
     * Gets the loading bar.
     *
     * @return The ProgressBar object representing the loading bar.
     */
    public ProgressBar getLoadingBar() { return loadingBar; }

    /**
     * Gets the health bar.
     *
     * @return The ProgressBar object representing the health bar.
     */
    public ProgressBar getHealthBar() { return healthBar; }

    /**
     * Gets the game over screen.
     *
     * @return The VBox object representing the game over screen.
     */
    public VBox getGameOverScreen() { return gameOverScreen; }

    /**
     * Gets the in-game menu.
     *
     * @return The VBox object representing the in-game menu.
     */
    public VBox getInGameMenu() { return inGameMenu; }

    /**
     * Gets the dialogue panel.
     *
     * @return The VBox object representing the dialogue panel.
     */
    public VBox getDialoguePanel() { return dialoguePanel; }

    /**
     * Gets the loading screen.
     *
     * @return The VBox object representing the loading screen.
     */
    public VBox getLoadScreen() { return loadScreen; }

    /**
     * Gets the main menu.
     *
     * @return The VBox object representing the main menu.
     */
    public VBox getMainMenu() { return mainMenu; }

    /**
     * Gets the health panel.
     *
     * @return The HBox object representing the health panel.
     */
    public HBox getHealthPanel() { return healthPanel; }

}
