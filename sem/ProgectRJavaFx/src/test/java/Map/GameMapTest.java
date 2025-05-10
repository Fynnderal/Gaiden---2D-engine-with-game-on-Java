package Map;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.Map.*;
import cvut.cz.characters.Directions;
import cvut.cz.characters.GameCharacter;
import cvut.cz.characters.NPC;
import cvut.cz.items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameMapTest {
    private GameMap gameMap;
    private GameCharacter gameCharacter;

    @BeforeEach
    void setUp() {
        List<Collision> collisions = new ArrayList<>();
        List<MapSection> mapSections = new ArrayList<>();
        MapInformation mapInformation = new MapInformation(0, 0, 0, 0);
        gameMap = new GameMap(collisions, mapSections, mapInformation);
        gameCharacter = new GameCharacter(null, null, new GameSpriteRenderInformation(0,0,0,0,0,0)){
            @Override
            public void update() {
                // No implementation needed for this test
            }

            @Override
            public void move(Directions direction, int speed) {
                // No implementation needed for this test
            }

            @Override
            public void attack() {
                // No implementation needed for this test
            }
        };
    }

    @Test
    void getNearestNPCReturnsNullWhenNoCharactersOnMap() {
        gameMap.setCharactersOnMap(new ArrayList<>());
        assertNull(gameMap.getNearestNPC(gameCharacter));
    }

    @Test
    void getNearestNPCReturnsCorrectNPCWhenCharacterIsNearby() {
        NPC npc = new NPC(null, null, new GameSpriteRenderInformation(0,0,0,0,0,0), null, null){
            @Override
            public void update() {
                // No implementation needed for this test
            }

            @Override
            public void move(Directions direction, int speed) {
                // No implementation needed for this test
            }

            @Override
            public void attack() {
                // No implementation needed for this test
            }

            @Override
            public String[] interact() {
                return null;
            }

            @Override
            public void changePlayersAnswer(Directions direction){

            }
        };

        npc.getGameSpriteRenderInformation().setScreenCoordinateX(10);
        npc.getGameSpriteRenderInformation().setScreenCoordinateY(10);
        gameMap.setCharactersOnMap(List.of(npc));

        gameCharacter.getGameSpriteRenderInformation().setScreenCoordinateX(12);
        gameCharacter.getGameSpriteRenderInformation().setScreenCoordinateY(12);

        assertEquals(npc, gameMap.getNearestNPC(gameCharacter));
    }

    @Test
    void getNearestAvailableItemReturnsNullWhenNoItemsOnMap() {
        gameMap.setItemsOnMap(new ArrayList<>());
        assertNull(gameMap.getNearestAvailableItem(gameCharacter));
    }

    @Test
    void getNearestAvailableItemReturnsCorrectItemWhenItemIsNearby() {
        Item item = new Item(null, new GameSpriteRenderInformation(0,0,0,0,0,0), null, 1);
        item.getGameSpriteRenderInformation().setScreenCoordinateX(15);
        item.getGameSpriteRenderInformation().setScreenCoordinateY(15);

        Item item2 = new Item(null, new GameSpriteRenderInformation(0,0,0,0,0,0), null, 1);
        item2.getGameSpriteRenderInformation().setScreenCoordinateX(0);
        item2.getGameSpriteRenderInformation().setScreenCoordinateY(0);


        gameMap.setItemsOnMap(List.of(item, item2));
        gameCharacter.getGameSpriteRenderInformation().setScreenCoordinateX(18);
        gameCharacter.getGameSpriteRenderInformation().setScreenCoordinateY(18);
        assertEquals(item, gameMap.getNearestAvailableItem(gameCharacter));
    }

    @Test
    void getNearestMapSpotsReturnsEmptyListWhenNoMapSpots() {
        gameMap.setMapSpots(new ArrayList<>());
        assertTrue(gameMap.getNearestMapSpots(gameCharacter).isEmpty());
    }

    @Test
    void getNearestMapSpotsReturnsCorrectMapSpotsWhenNearby() {
        MapSpot mapSpot = new MapSpot(null, new GameSpriteRenderInformation(0,0,0,0,0,0), null);

        mapSpot.getGameSpriteRenderInformation().setScreenCoordinateX(20);
        mapSpot.getGameSpriteRenderInformation().setScreenCoordinateY(20);

        MapSpot mapSpot2 = new MapSpot(null, new GameSpriteRenderInformation(0,0,0,0,0,0), null);

        mapSpot2.getGameSpriteRenderInformation().setScreenCoordinateX(0);
        mapSpot2.getGameSpriteRenderInformation().setScreenCoordinateY(0);


        gameMap.setMapSpots(List.of(mapSpot, mapSpot2));

        gameCharacter.getGameSpriteRenderInformation().setScreenCoordinateX(22);
        gameCharacter.getGameSpriteRenderInformation().setScreenCoordinateY(22);

        assertEquals(1, gameMap.getNearestMapSpots(gameCharacter).size());
        assertEquals(mapSpot, gameMap.getNearestMapSpots(gameCharacter).get(0));
    }

    @Test
    void checkCollisionsReturnsEmptyListWhenNoCollisionIsNotDetected() {
        Collision collision = new Collision(50, 50, 10, 10);

        gameMap.getCollisions().add(collision);

        assertEquals(0, gameMap.checkCollisions(0, 0, 5, 5).size());
    }

    @Test
    void checkCollisionsReturnsListOfCollisionsWhenCollisionDetected() {
        Collision collision = new Collision(10, 10, 10, 10);
        collision.setWorldCoordinateX(10);
        collision.setWorldCoordinateY(10);
        collision.setWidth(10);
        collision.setHeight(10);

        gameMap.getCollisions().add(collision);

        List<Collision> collisions = gameMap.checkCollisions(5, 5, 15, 15);

        assertEquals(1, collisions.size());

        assertEquals(10, collisions.getFirst().getWorldCoordinateX());
        assertEquals(10, collisions.getFirst().getWorldCoordinateY());
    }

    @Test
    void translateMapUpMovesObjectsCorrectly() {
        MapSection mapSection = new MapSection(null, new GameSpriteRenderInformation(0,0,0,0,0,0), null);

        mapSection.getGameSpriteRenderInformation().setScreenCoordinateX(100);
        mapSection.getGameSpriteRenderInformation().setScreenCoordinateY(100);

        gameMap.getMapSections().add(mapSection);

        gameMap.translateMap(Directions.UP, 10);
        assertEquals(100, mapSection.getGameSpriteRenderInformation().getScreenCoordinateX());
        assertEquals(90, mapSection.getGameSpriteRenderInformation().getScreenCoordinateY());
    }

    @Test
    void translateMapDownMovesObjectsCorrectly() {
        MapSection mapSection = new MapSection(null, new GameSpriteRenderInformation(0,0,0,0,0,0), null);

        mapSection.getGameSpriteRenderInformation().setScreenCoordinateX(100);
        mapSection.getGameSpriteRenderInformation().setScreenCoordinateY(100);

        gameMap.getMapSections().add(mapSection);

        gameMap.translateMap(Directions.DOWN, 10);
        assertEquals(100, mapSection.getGameSpriteRenderInformation().getScreenCoordinateX());
        assertEquals(110, mapSection.getGameSpriteRenderInformation().getScreenCoordinateY());
    }

    @Test
    void translateMapRightMovesObjectsCorrectly() {
        MapSection mapSection = new MapSection(null, new GameSpriteRenderInformation(0,0,0,0,0,0), null);

        mapSection.getGameSpriteRenderInformation().setScreenCoordinateX(100);
        mapSection.getGameSpriteRenderInformation().setScreenCoordinateY(100);

        gameMap.getMapSections().add(mapSection);

        gameMap.translateMap(Directions.RIGHT, 10);
        assertEquals(110, mapSection.getGameSpriteRenderInformation().getScreenCoordinateX());
        assertEquals(100, mapSection.getGameSpriteRenderInformation().getScreenCoordinateY());
    }

    @Test
    void translateMapLeftMovesObjectsCorrectly() {
        MapSection mapSection = new MapSection(null, new GameSpriteRenderInformation(0,0,0,0,0,0), null);

        mapSection.getGameSpriteRenderInformation().setScreenCoordinateX(100);
        mapSection.getGameSpriteRenderInformation().setScreenCoordinateY(100);

        gameMap.getMapSections().add(mapSection);

        gameMap.translateMap(Directions.LEFT, 10);
        assertEquals(90, mapSection.getGameSpriteRenderInformation().getScreenCoordinateX());
        assertEquals(100, mapSection.getGameSpriteRenderInformation().getScreenCoordinateY());
    }
}
