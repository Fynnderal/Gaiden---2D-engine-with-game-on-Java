package cvut.cz.Utils;

import cvut.cz.GameSprite.GameSprite;
import cvut.cz.MainApplication;
import cvut.cz.Map.*;
import cvut.cz.Model.MapModel;
import javafx.application.Platform;
import javafx.scene.image.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class MapLoader implements Runnable{
    private static final Logger logger = Logger.getLogger(MapLoader.class.getName());

    private final MainApplication mainApp;
    private final URL pathToMap;

    private List<MapSection> mapSections;

    public MapLoader(MainApplication mainApp, URL pathToMap) {
        this.mainApp = mainApp;
        this.pathToMap = pathToMap;
    }


    class Counter {
        private int counter;

        public Counter() {
            counter = 0;
        }

        public void increment() { counter++; }

        public int getCounter() {
            return counter;
        }
    }

    @Override
    public void run() {
        mapSections = MapModel.getMapModel().getMap().getMapSections();

        ExecutorService executorService = Executors.newFixedThreadPool(8, r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });

        Counter counter = new Counter();

        for (MapSection mapSection : mapSections) {
            processSection(mapSection, executorService, counter);
        }
        executorService.shutdown();

        String currentPath;
        for (GameSprite gameSprite : MapModel.getMapModel().getDrawableObjects()) {
            currentPath = String.valueOf(gameSprite.getGameSpriteSourceInformation().getPathImage());
            mainApp.getRenderManager().getImagesToDraw().put(currentPath, new Image(currentPath));
        }

        Platform.runLater(() -> {
            mainApp.getGuiCreator().getLoadingBar().setProgress(1);
            mainApp.getGuiCreator().getLoadScreen().setVisible(false);
            mainApp.getGuiCreator().getHealthPanel().setVisible(true);
        });
        mainApp.setRunning(true);
    }

    private void processSection(MapSection mapSection, ExecutorService executorService, Counter counter) {
        WritableImage writableImage = new WritableImage(mapSection.getGameSpriteRenderInformation().getTargetWidth(), mapSection.getGameSpriteRenderInformation().getTargetHeight());

        PixelWriter pixelWriter = writableImage.getPixelWriter();
        if (pixelWriter == null) {
            logger.severe("PixelWriter is null");
            return;
        }

        PixelReader pixelReader = new Image(String.valueOf(pathToMap)).getPixelReader();
        CountDownLatch latch = new CountDownLatch(mapSection.getTiles().size());

        for (Tile tile : mapSection.getTiles()) {
            executorService.execute(() -> {
                ImageCopy.copyPixels(pixelReader, pixelWriter, Tile.getSourceTileSize(), Tile.getSourceTileSize(), tile.getGameSpriteSourceInformation().getSourceCoordinateX(), tile.getGameSpriteSourceInformation().getSourceCoordinateY(), tile.getGameSpriteRenderInformation().getWorldCoordinateX() - mapSection.getGameSpriteRenderInformation().getWorldCoordinateX(), tile.getGameSpriteRenderInformation().getWorldCoordinateY() - mapSection.getGameSpriteRenderInformation().getWorldCoordinateY());
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.severe("Interrupted while waiting for latch: " + e.getMessage());
        }

        saveSection(mapSection, counter, writableImage);

        counter.increment();
        Platform.runLater(() -> mainApp.getGuiCreator().getLoadingBar().setProgress((double) counter.getCounter() / mapSections.size()));

    }

    private void saveSection(MapSection mapSection, Counter counter, WritableImage writableImage) {
        try {
            File file = new File("map" + counter.getCounter() + ".png");
            URL pathToImage = file.toURI().toURL();
            ImageCopy.saveImage(writableImage, file);
            mapSection.getGameSpriteSourceInformation().setPathImage(pathToImage);
        } catch (MalformedURLException e) {
            logger.severe("Problem with path to image; ERROR: " + e.getMessage());
        }
    }
}
