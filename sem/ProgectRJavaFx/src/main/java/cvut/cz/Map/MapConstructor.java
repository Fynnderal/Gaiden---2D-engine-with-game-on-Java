package cvut.cz.Map;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.io.File;

import java.net.URL;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

/**
 * Constructs a game map by creating map sections and defining collisions.
 * This class reads map data from files and generates the necessary components for the game map.
 */
public class MapConstructor {
    private static final Logger logger = Logger.getLogger(MapConstructor.class.getName());

    // The list of tiles from which the map is constructed
    private List<Tile> sourceTiles;
    // The list of collisions
    private List<Collision> mapCollisions;
    // The list of map sections
    private List<MapSection> mapSections;

    // The coordinates of the map in the game world
    private final int mapCoordinateX;
    // The coordinates of the map in the game world
    private final int mapCoordinateY;
    // The scale factor of the map width in the game world
    private final int mapTargetScaleFactorX;
    // The scale factor of the map height in the game world
    private final int mapTargetScaleFactorY;

    // General information about the map
    private final MapInformation mapInformation;

    // The paths to the information about map sections
    private final List<URL> mapSectionsPaths;

    private final MapSlicer mapSlicer;

    // The path to the file with collision information
    private final URL mapCollisionsPath;

    /**
     * Constructs a MapConstructor object with the specified parameters.
     *
     * @param mapSlicer The slicer used to divide the source image into tiles.
     * @param mapCollisionsPath The path to the file containing collision information.
     * @param mapSectionsPaths The paths to the information about map sections.
     * @param mapInformation General information about the map.
     */
    public MapConstructor(MapSlicer mapSlicer,URL mapCollisionsPath, List<URL> mapSectionsPaths, MapInformation mapInformation) {
        this.mapSlicer = mapSlicer;
        this.mapInformation = mapInformation;
        this.mapCoordinateX = mapInformation.mapCoordinateX();
        this.mapCoordinateY = mapInformation.mapCoordinateY();
        this.mapSectionsPaths = mapSectionsPaths;
        this.mapTargetScaleFactorX = mapInformation.mapTargetScaleFactorX();
        this.mapTargetScaleFactorY = mapInformation.mapTargetScaleFactorY();
        this.mapCollisionsPath = mapCollisionsPath;
    }

    /**
     * Creates the game map by generating map sections and collisions.
     *
     * @return A GameMap object representing the constructed map.
     */
    public GameMap createMap() {
        createMapSections();
        createCollisions();

        return new GameMap(mapCollisions, mapSections, this.mapInformation);
    }

    /**
     * Reads the source tiles by slicing the map.
     */
    private void readSourceTiles()
    {
        mapSlicer.sliceMap();
        sourceTiles = mapSlicer.getSlicedTiles();
        logger.info("Got source tiles: " + sourceTiles.size());
    }

    /**
     * Creates the map sections by reading data from the section files.
     */
    private void createMapSections() {
        readSourceTiles();
        mapSections = new ArrayList<>();
        if (mapSectionsPaths == null) {
            logger.severe("Map sections paths are null");
            return;
        }

        for (URL url: mapSectionsPaths) {
            readSection(url);
        }
    }

    /**
     * Reads a map section from the specified file and creates its tiles.
     *
     * @param pathToSection The path to the file containing the map section data.
     */
    private void readSection(URL pathToSection) {
        Tile currentTile;
        int currentSectionWidth = 0, currentSectionHeight, currentSectionWorldX = 0, currentSectionWorldY = 0;
        int column = 0, row = 0, idx;
        List <Tile> sectionTiles = new ArrayList<>();

        try (Scanner scanner = new Scanner(pathToSection.openStream())) {
            // First two numbers in the file are the world coordinates of the map section
            currentSectionWorldX = scanner.nextInt();
            currentSectionWorldY = scanner.nextInt();


            while (scanner.hasNextInt()) {
                idx = scanner.nextInt();

                // if the index is negative, it means that new row should be started
                if (idx < 0) {
                    int temp = Tile.getSourceTileSize() * column;
                    // if the width of the current row is bigger than the previous one, set the width of the current row as the longest
                    if (temp > currentSectionWidth)
                        currentSectionWidth = temp;

                    column = 0;
                    row++;
                    continue;
                }
                try {
                    // Creates the tile for the map from the source tiles
                    currentTile = sourceTiles.get(idx).clone();
                    currentTile.getGameSpriteRenderInformation().setWorldCoordinateX(currentSectionWorldX + column * Tile.getSourceTileSize());
                    currentTile.getGameSpriteRenderInformation().setWorldCoordinateY(currentSectionWorldY + row * Tile.getSourceTileSize());

                    sectionTiles.add(currentTile);
                } catch (NullPointerException e) {
                    logger.severe("Null instead of tile. Row " + row + "Column " + column + "Idx = " + idx);
                }
                column++;
            }
        } catch (FileNotFoundException e) {
            logger.severe("Map File was not found");
        }
        catch (IOException e){
            logger.severe("Problem reading map file: " + e.getMessage());
        }

        currentSectionHeight = Tile.getSourceTileSize() * row;

        GameSpriteSourceInformation mapSectionSourceInformation = new GameSpriteSourceInformation(null, 0, 0, currentSectionWidth, currentSectionHeight);

        GameSpriteRenderInformation mapSectionRenderInformation = new GameSpriteRenderInformation(currentSectionWorldX + this.mapCoordinateX,
                                                                                                  currentSectionWorldY + this.mapCoordinateY,
                                                                                                        currentSectionWidth * this.mapTargetScaleFactorX,
                                                                                                        currentSectionHeight * this.mapTargetScaleFactorY,
                                                                                                                   currentSectionWorldX,
                                                                                                                   currentSectionWorldY);

        MapSection mapSection = new MapSection(mapSectionSourceInformation, mapSectionRenderInformation, sectionTiles);
        mapSections.add(mapSection);

        logger.info("MapSection Height: " + currentSectionHeight);
        logger.info("MapSection Width: " + currentSectionWidth);
    }

    /**
     * Creates the collisions by reading data from the collision file.
     */
    private void createCollisions() {
        mapCollisions = new ArrayList<>();
        int x, y, w, h;
        try (Scanner scanner = new Scanner(mapCollisionsPath.openStream())) {
            while (scanner.hasNextInt()) {
                // Read the coordinates of the collision
                x = scanner.nextInt();
                y = scanner.nextInt();
                // Read the width and height of the collisions
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


}
