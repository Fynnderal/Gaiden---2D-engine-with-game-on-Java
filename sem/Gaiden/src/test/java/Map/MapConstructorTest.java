package Map;

import cvut.cz.Map.GameMap;
import cvut.cz.Map.MapConstructor;
import cvut.cz.Map.MapInformation;
import cvut.cz.Map.MapSlicer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MapConstructorTest {

    private MapConstructor mapConstructor;
    private MapSlicer mapSlicer;
    private URL mapCollisionsPath;
    private List<URL> mapSectionsPaths;
    private MapInformation mapInformation;

    @BeforeEach
    void setUp() {
        mapSlicer = new MapSlicer(null, 16,0,0,600,2701);
        mapSectionsPaths = new ArrayList<>();
        mapSectionsPaths.add(MapConstructorTest.class.getResource("mapSection0.txt"));

        mapCollisionsPath = MapConstructor.class.getClassLoader().getResource("collisions");
        mapInformation = new MapInformation(0, 0, 0, 0);
        mapConstructor = new MapConstructor(mapSlicer, mapCollisionsPath, mapSectionsPaths, mapInformation);
    }


    @Test
    void createMapHandlesEmptySectionPathsGracefully() {
        mapConstructor = new MapConstructor(mapSlicer, mapCollisionsPath, null, mapInformation);
        GameMap gameMap = mapConstructor.createMap();
        assertNotNull(gameMap);
        assertTrue(gameMap.getMapSections().isEmpty());
    }

}
