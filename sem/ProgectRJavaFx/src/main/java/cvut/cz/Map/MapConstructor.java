package cvut.cz.Map;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.io.File;

import java.net.URL;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

public class MapConstructor {
    private static final Logger logger = Logger.getLogger(MapConstructor.class.getName());

    private List<Tile> sourceTiles;
    private List<Collision> mapCollisions;
    private List<MapSection> mapSections;

    private final int mapCoordinateX;
    private final int mapCoordinateY;
    private final int mapTargetScaleFactorX;
    private final int mapTargetScaleFactorY;
    private final MapInformation mapInformation;

    private final List<URL> mapSectionsPaths;
    private final MapSlicer mapSlicer;
    private final URL mapCollisionsPath;

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

    public GameMap createMap() {
        createMapSections();
        createCollisions();

        return new GameMap(mapCollisions, mapSections, this.mapInformation);
    }

    private void readSourceTiles()
    {
        mapSlicer.sliceMap();
        sourceTiles = mapSlicer.getSlicedTiles();
        logger.info("Got source tiles: " + sourceTiles.size());
    }

    private void createMapSections() {
        readSourceTiles();
        mapSections = new ArrayList<>();

        for (URL url: mapSectionsPaths) {
            readSection(url);
        }
    }

    private void readSection(URL pathToSection) {
        Tile currentTile;
        int currentSectionWidth = 0, currentSectionHeight, currentSectionWorldX = 0, currentSectionWorldY = 0;
        int column = 0, row = 0, idx;
        List <Tile> sectionTiles = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(pathToSection.getPath()))) {
            currentSectionWorldX = scanner.nextInt();
            currentSectionWorldY = scanner.nextInt();

            while (scanner.hasNextInt()) {
                idx = scanner.nextInt();
                if (idx < 0) {
                    int temp = Tile.getSourceTileSize() * column;
                    if (temp > currentSectionWidth)
                        currentSectionWidth = temp;

                    column = 0;
                    row++;
                    continue;
                }
                try {
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

        currentSectionHeight = Tile.getSourceTileSize() * row;

        GameSpriteSourceInformation mapSectionSourceInformation = new GameSpriteSourceInformation(null, 0, 0, currentSectionWidth, currentSectionHeight);
        GameSpriteRenderInformation mapSectionRenderInformation = new GameSpriteRenderInformation(currentSectionWorldX + this.mapCoordinateX, currentSectionWorldY + this.mapCoordinateY, currentSectionWidth * this.mapTargetScaleFactorX, currentSectionHeight * this.mapTargetScaleFactorY, currentSectionWorldX, currentSectionWorldY);
        MapSection mapSection = new MapSection(mapSectionSourceInformation, mapSectionRenderInformation, sectionTiles);
        mapSections.add(mapSection);

        logger.info("MapSection Height: " + currentSectionHeight);
        logger.info("MapSection Width: " + currentSectionWidth);
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


}
