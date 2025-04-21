package cvut.cz.Map;

import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.util.logging.Logger;

public class MapSlicer {
    private static final Logger logger = Logger.getLogger(MapSlicer.class.getName());

    private List<Tile> slicedTiles;
    private final URL pathToTiles;
    private final int gapBetweenTilesX;
    private final int gapBetweenTilesY;
    private final int numberOfTilesInRow;
    private final int numberOfTiles;
    private final int sourceTileSize;

    public MapSlicer(URL pathToTiles, int sourceTileSize, int gapBetweenTilesX, int gapBetweenTilesY, int numberOfTilesInRow, int numberOfTiles) {
        this.sourceTileSize = sourceTileSize;
        this.pathToTiles = pathToTiles;
        this.gapBetweenTilesX = gapBetweenTilesX;
        this.gapBetweenTilesY = gapBetweenTilesY;
        this.numberOfTilesInRow = numberOfTilesInRow;
        this.numberOfTiles = numberOfTiles;
    }

    public void sliceMap() {
        slicedTiles = new ArrayList<>();
        int rowCounter = 0;
        int columnCounter = 0;
        for (int i = 0; i < numberOfTiles; i++) {
            if (columnCounter == numberOfTilesInRow) {
                columnCounter = 0;
                rowCounter++;
            }
            slicedTiles.add(new Tile(pathToTiles, columnCounter * (sourceTileSize + gapBetweenTilesX), rowCounter * (sourceTileSize + gapBetweenTilesY)));
            columnCounter++;
        }
    }

    public List<Tile> getSlicedTiles() { return slicedTiles; }
}