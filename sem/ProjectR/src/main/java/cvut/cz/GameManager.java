package cvut.cz;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.FlowPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import javafx.scene.input.KeyCode;


import javax.management.DescriptorRead;


public class GameManager extends Application {
    private static final double SCREEN_STANDARD_WIDTH = 1536;
    private static final double SCREEN_STANDARD_HEIGHT = 864;
    private final double RENDER_SCALE_FACTOR;

    private static final double  SPRITE_STANDARD_SIZE = 200;   // Standard size for screen 1536x864
    private static Image Sprite_Image = new Image("FreeHorrorUi.png");

    public GameManager() {
        double w_scale_factor = Screen.getPrimary().getBounds().getWidth() / SCREEN_STANDARD_WIDTH;
        double h_scale_factor = Screen.getPrimary().getBounds().getWidth() / SCREEN_STANDARD_HEIGHT;

        RENDER_SCALE_FACTOR = w_scale_factor > h_scale_factor ? h_scale_factor : w_scale_factor;
    }

    public double getRENDER_SCALE_FACTOR() {
        return RENDER_SCALE_FACTOR;
    }

    @Override
    public void start(Stage stage) {

        Canvas canvas = new Canvas(SCREEN_STANDARD_WIDTH * RENDER_SCALE_FACTOR,  SCREEN_STANDARD_HEIGHT * RENDER_SCALE_FACTOR);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);
        gc.clearRect(0, 0, SCREEN_STANDARD_WIDTH, SCREEN_STANDARD_HEIGHT);

        gc.drawImage(Sprite_Image, 1 ,1 , 14, 14, 5 * RENDER_SCALE_FACTOR, 5 * RENDER_SCALE_FACTOR, SPRITE_STANDARD_SIZE * RENDER_SCALE_FACTOR,SPRITE_STANDARD_SIZE * RENDER_SCALE_FACTOR);
        gc.drawImage(Sprite_Image, 128, 64, 32, 32, 150 * RENDER_SCALE_FACTOR, 150 * RENDER_SCALE_FACTOR, SPRITE_STANDARD_SIZE* RENDER_SCALE_FACTOR, SPRITE_STANDARD_SIZE * RENDER_SCALE_FACTOR);

        stage.setScene(new Scene(new FlowPane(canvas)));
        stage.setTitle("Project R");

        stage.setFullScreen(true);
        stage.fullScreenProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                stage.setFullScreen(true);
            }
        });

        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}