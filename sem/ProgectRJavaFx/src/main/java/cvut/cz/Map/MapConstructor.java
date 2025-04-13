package cvut.cz.Map;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.io.File;

import java.net.URL;

import cvut.cz.characters.GameCharacter;

public class MapConstructor {
    private static final Logger logger = Logger.getLogger(MapConstructor.class.getName());


    private static MapConstructor singleInstance = null;
    private Map map;

    private List<Tile> sourceTiles;
    private List<Tile> mapTiles;
    private List<Collision> mapCollisions;
    private List<GameCharacter> characters;
    private List<GameCharacter> items;


    private int mapCoordinateX;
    private int mapCoordinateY;
    private int mapImageHeight;
    private int mapImageWidth;
    private int mapTargetWidth;
    private int mapTargetHeight;
    private int mapTargetScaleFactorX;
    private int mapTargetScaleFactorY;

    private URL mapFilePath;
    private MapSlicer mapSlicer;
    private URL mapCollisionsPath;




    public MapConstructor(MapSlicer mapSlicer, URL mapFilePath, int mapCoordinateX, int mapCoordinateY, int mapScaleFactorX, int mapScaleFactorY, URL mapCollisionsPath) {
        if (singleInstance != null)
            return;

        this.mapSlicer = mapSlicer;
        this.mapCoordinateX = mapCoordinateX;
        this.mapCoordinateY = mapCoordinateY;
        this.mapFilePath = mapFilePath;
        this.mapTargetScaleFactorX = mapScaleFactorX;
        this.mapTargetScaleFactorY = mapScaleFactorY;

        this.mapCollisionsPath = mapCollisionsPath;

        singleInstance = this;


    }

    public Map createMap() {
        createMapTiles();
        createCollisions();
        //placeCharacters
        //placeItems
        map = new Map(null, 0, 0,this.mapImageWidth,this.mapImageHeight, this.mapCoordinateX, this.mapCoordinateY, this.mapImageWidth * this.mapTargetScaleFactorX, this.mapImageHeight * this.mapTargetScaleFactorY,  this.mapCollisions);
        return map;
    }
    private void readSourceTiles()
    {
        mapSlicer.sliceMap();
        sourceTiles = mapSlicer.getSlicedTiles();
        logger.info("Got source tiles: " + sourceTiles.size());
    }

    private List<Tile> createMapTiles() {
        readSourceTiles();
        Tile currentTile;
        mapTiles = new ArrayList<>();
        int counter = 0;
        int currentRow = 0;
        int idx;
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
                    currentTile.setWorldCoordinateX(counter * Tile.getSourceTileSize());
                    currentTile.setWorldCoordinateY(currentRow * Tile.getSourceTileSize());

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
        return mapTiles;
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
