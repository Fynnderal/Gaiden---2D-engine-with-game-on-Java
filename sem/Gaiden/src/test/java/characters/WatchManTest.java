package characters;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.characters.*;
import cvut.cz.items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


class WatchManTest {

    private WatchMan watchMan;
    private PlayableCharacter mainCharacter;

    @BeforeEach
    void setUp() {
        CharacterInformation characterInfo = new CharacterInformation(60, States.IDLE, 100, 8);
        GameSpriteRenderInformation renderInfo = new GameSpriteRenderInformation(0, 0, 0, 0, 0, 0);
        GameSpriteSourceInformation sourceInfo = new GameSpriteSourceInformation(null, 0, 0, 0, 0);
        ActionAreaInformation actionAreaInfo = new ActionAreaInformation(0, 0, 1000, 1000, 400);


        CharacterInformation playerCharacterInfo = new CharacterInformation(10, States.IDLE, 100, 5);
        GameSpriteSourceInformation sourceCharacterInfo = new GameSpriteSourceInformation(null, 0, 0, 0, 0);
        GameSpriteRenderInformation playerRenderInfo = new GameSpriteRenderInformation(0, 0, 0, 0, 0, 0);

        mainCharacter = new PlayableCharacter(playerCharacterInfo, sourceCharacterInfo, playerRenderInfo){
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
            public void shoot(Item item) {
                // No implementation needed for this test
            }

            @Override
            public void aim(Item item){
                // No implementation needed for this test
            }

            @Override
            public void useItem(Item item) {
                // No implementation needed for this test
            }
        };

        watchMan = new WatchMan(characterInfo, sourceInfo, renderInfo, actionAreaInfo, mainCharacter);
    }

    @Test
    void update_setsStateToIdleWhenPlayerNotInActionArea() {
        mainCharacter.getGameSpriteRenderInformation().setWorldCoordinateX(2000); // Place player outside action area
        mainCharacter.getGameSpriteRenderInformation().setWorldCoordinateY(2000);

        watchMan.update();

        assertEquals(States.IDLE, watchMan.getCharacterInformation().getCurrentState());
    }

    @Test
    void update_setsStateToAlarmedWhenPlayerInActionArea() {
        mainCharacter.getGameSpriteRenderInformation().setWorldCoordinateX(800); // Place player within action area
        mainCharacter.getGameSpriteRenderInformation().setWorldCoordinateY(800);

        watchMan.update();

        assertEquals(States.ALARMED, watchMan.getCharacterInformation().getCurrentState());
    }

    @Test
    void update_setsSpeedToHalfWhenAlarmed() {
        watchMan.getCharacterInformation().setCurrentState(States.ALARMED);

        watchMan.update();

        assertEquals(4, watchMan.getCharacterInformation().getSpeed());
    }

    @Test
    void update_setsSpeedToHalfWhenChasing() {
        watchMan.getCharacterInformation().setCurrentState(States.CHASING);

        watchMan.update();

        assertEquals(8, watchMan.getCharacterInformation().getSpeed());
    }

    @Test
    void takeDamage_setsStateToChasing() {
        int X = new Random().nextInt(1000);
        watchMan.takeDamage(X);

        assertEquals(States.CHASING, watchMan.getCharacterInformation().getCurrentState());
    }


}