package cvut.cz.Map;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.io.File;

import java.net.URL;

import cvut.cz.GameSpriteRenderInformation;
import cvut.cz.GameSpriteSourceInformation;
import cvut.cz.characters.GameCharacter;

public class MapConstructor {
    private static final Logger logger = Logger.getLogger(MapConstructor.class.getName());

    private Map map;

    private List<Tile> sourceTiles;
    private List<Tile> mapTiles;
    private List<Collision> mapCollisions;
    private List<GameCharacter> characters;
    private List<GameCharacter> items;


    private int mapImageHeight;
    private int mapImageWidth;
    private int mapTargetWidth;
    private int mapTargetHeight;

    private final int mapCoordinateX;
    private final int mapCoordinateY;
    private final int mapTargetScaleFactorX;
    private final int mapTargetScaleFactorY;

    private final URL mapFilePath;
    private final MapSlicer mapSlicer;
    private final URL mapCollisionsPath;

    public MapConstructor(MapSlicer mapSlicer, URL mapCollisionsPath, URL mapFilePath, int mapCoordinateX, int mapCoordinateY, int mapScaleFactorX, int mapScaleFactorY) {
        this.mapSlicer = mapSlicer;
        this.mapCoordinateX = mapCoordinateX;
        this.mapCoordinateY = mapCoordinateY;
        this.mapFilePath = mapFilePath;
        this.mapTargetScaleFactorX = mapScaleFactorX;
        this.mapTargetScaleFactorY = mapScaleFactorY;
        this.mapCollisionsPath = mapCollisionsPath;
    }

    public Map createMap() {
        createMapTiles();
        createCollisions();
        //placeCharacters
        //placeItems
        map = new Map(mapCollisions, new GameSpriteSourceInformation(null, 0, 0, mapImageWidth, mapImageHeight),  new GameSpriteRenderInformation(this.mapCoordinateX, this.mapCoordinateY, this.mapImageWidth * this.mapTargetScaleFactorX, this.mapImageHeight * this.mapTargetScaleFactorY, 0, 0));
        return map;
    }

    private void readSourceTiles()
    {
        mapSlicer.sliceMap();
        sourceTiles = mapSlicer.getSlicedTiles();
        logger.info("Got source tiles: " + sourceTiles.size());
    }

    private void createMapTiles() {
        readSourceTiles();
        mapTiles = new ArrayList<>();

        Tile currentTile;
        int counter = 0, currentRow = 0, idx;
        try (Scanner scanner = new Scanner(new File(mapFilePath.getPath()))){
            while (scanner.hasNextInt()) {
                idx = scanner.nextInt();
                if (idx < 0) {
                    int temp = Tile.getSourceTileSize() * counter;
                    if (temp > this.mapImageWidth)
                        this.mapImageWidth = temp;

                    counter = 0;
                    currentRow++;
                    continue;
                }
                try {
                    currentTile = sourceTiles.get(idx);
                    currentTile.getGameSpriteRenderInformation().setWorldCoordinateX(counter * Tile.getSourceTileSize());
                    currentTile.getGameSpriteRenderInformation().setWorldCoordinateY(currentRow * Tile.getSourceTileSize());

                    mapTiles.add(currentTile);
                }catch(NullPointerException e) {
                    logger.severe("Null instead of tile. Row " + currentRow + "Column " + counter + "Idx = " + idx);
                }
                counter++;
            }
        } catch (FileNotFoundException e) {
            logger.severe("Map File was not found");
        }
        this.mapImageHeight = Tile.getSourceTileSize() * currentRow;

        logger.info("Created Map tiles: " + mapTiles.size());
        logger.info("Map Image Height: " + this.mapImageHeight);
        logger.info("Map Image Width: " + this.mapImageWidth);
    }

    private void createCollisions() {
        mapCollisions = new ArrayList<>();
        int x, y, w, h;
        try (Scanner scanner = new Scanner(new File(mapCollisionsPath.getPath()))) {
            while (scanner.hasNextInt()) {
                x = scanner.nextInt();
                y = scanner.nextInt();
                w = scanner.nextInt();
                h = scanner.nextInt();

                if (w <= 0 || h <= 0) {
                    throw new Exception("Width and Height must be grater than zero");
                }
                mapCollisions.add(new Collision(x, y, w, h));
            }
        }
        catch (Exception e) {
            logger.severe("Problem reading collisions file: " + e.getMessage());
        }
    }

    private void placeCharacters() {
    }

    private void placeItems() {

    }

    public int getMapImageWidth() { return mapImageWidth; }
    public int getMapImageHeight() { return mapImageHeight; }
    public List<Tile> getMapTiles() { return mapTiles; }
}
