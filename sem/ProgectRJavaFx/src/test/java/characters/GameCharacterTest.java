package characters;

import cvut.cz.Animation.AnimationInformation;
import cvut.cz.Animation.AnimationStates;
import cvut.cz.Animation.CharacterAnimation;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.characters.CharacterInformation;
import cvut.cz.characters.Directions;
import cvut.cz.characters.GameCharacter;
import cvut.cz.characters.States;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameCharacterTest {

    GameCharacter character;
    @BeforeEach
    void setUp() {
        // Initialize the character for testing
        CharacterInformation characterInfo = new CharacterInformation(0, States.IDLE, 100, 5);
        GameSpriteRenderInformation renderInfo = new GameSpriteRenderInformation(0, 0, 0, 0, 0, 0);
        GameSpriteSourceInformation sourceInfo = new GameSpriteSourceInformation(null, 0, 0, 0, 0);

        CharacterAnimation characterAnimationInfo = new CharacterAnimation();
        // Create a character instance
        character = new GameCharacter(characterInfo, sourceInfo, renderInfo) {
            @Override
            public void move(Directions direction, int speed) {
                // No implementation needed for this test
            }

            @Override
            public void attack() {
                // No implementation needed for this test
            }
        };
        character.setCharacterAnimation(characterAnimationInfo);
    }

    @Test
    void takeDamage_testDying() {
        character.setCharacterAnimation(null);
        character.takeDamage(character.getCharacterInformation().getMaxHealth());
        assertTrue(character.isDead());
    }

    @Test
    void takeDamage_testNegativeDamageOverMaximum(){
        character.takeDamage(-100);
        assertEquals(character.getCharacterInformation().getMaxHealth(), character.getCharacterInformation().getCurrentHealth());
    }

    @Test
    void takeDamage_testNegativeDamage(){
        character.getCharacterInformation().setCurrentHealth(character.getCharacterInformation().getMaxHealth() - 10);
        character.takeDamage(-10);
        assertEquals(character.getCharacterInformation().getMaxHealth(), character.getCharacterInformation().getCurrentHealth());
    }

    @Test
    void update_resettingAnimationStateToIdleUp(){
        character.getCharacterAnimation().currentAnimationState = AnimationStates.WalkingUp;
        character.update();
        assertEquals(AnimationStates.IdleUp, character.getCharacterAnimation().currentAnimationState);
    }

    @Test
    void update_resettingAnimationStateToIdleDown(){
        character.getCharacterAnimation().currentAnimationState = AnimationStates.WalkingDown;
        character.update();
        assertEquals(AnimationStates.IdleDown, character.getCharacterAnimation().currentAnimationState);
    }
    @Test
    void update_resettingAnimationStateToIdleRight(){
        character.getCharacterAnimation().currentAnimationState = AnimationStates.WalkingRight;
        character.update();
        assertEquals(AnimationStates.IdleRight, character.getCharacterAnimation().currentAnimationState);
    }
    @Test
    void update_resettingAnimationStateToIdleLeft(){
        character.getCharacterAnimation().currentAnimationState = AnimationStates.WalkingLeft;
        character.update();
        assertEquals(AnimationStates.IdleLeft, character.getCharacterAnimation().currentAnimationState);
    }


}
