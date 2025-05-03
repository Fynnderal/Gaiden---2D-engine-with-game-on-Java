package cvut.cz.characters;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

import static cvut.cz.Animation.AnimationStates.*;
import static cvut.cz.Animation.AnimationStates.WalkingLEFT;

public class Zombie extends Enemy{

    public Zombie(CharacterInformation characterInformation,
                  GameSpriteSourceInformation gameSpriteSourceInformation,
                  GameSpriteRenderInformation gameSpriteRenderInformation,
                  ActionAreaInformation actionAreaInformation,
                  PlayableCharacter mainCharacter) {
        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation, actionAreaInformation, mainCharacter);
    }

    @Override
    protected void move(Directions direction, int speed) {
        int currentWorldCoordinateY;
        int currentWorldCoordinateX;
        switch (direction) {
            case UP:
                currentWorldCoordinateY = gameSpriteRenderInformation.getWorldCoordinateY() - speed;
                characterAnimation.currentAnimationState = WalkingUP;
                if (currentWorldCoordinateY >= actionAreaInformation.actionAreaY()){
                    this.gameSpriteRenderInformation.setWorldCoordinateY(currentWorldCoordinateY);
                    this.gameSpriteRenderInformation.setScreenCoordinateY(gameSpriteRenderInformation.getScreenCoordinateY() - speed);
                }

                break;

            case DOWN:
                currentWorldCoordinateY = gameSpriteRenderInformation.getWorldCoordinateY() + speed;
                characterAnimation.currentAnimationState = WalkingDOWN;

                if (currentWorldCoordinateY <= actionAreaInformation.actionAreaY() + actionAreaInformation.actionAreaHeight()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateY(currentWorldCoordinateY);
                    this.gameSpriteRenderInformation.setScreenCoordinateY(gameSpriteRenderInformation.getScreenCoordinateY() + speed);
                }

                break;

            case RIGHT:
                currentWorldCoordinateX = gameSpriteRenderInformation.getWorldCoordinateX() + speed;
                characterAnimation.currentAnimationState = WalkingRIGHT;

                if (currentWorldCoordinateX <= actionAreaInformation.actionAreaX() + actionAreaInformation.actionAreaWidth()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateX(currentWorldCoordinateX);
                    this.gameSpriteRenderInformation.setScreenCoordinateX(gameSpriteRenderInformation.getScreenCoordinateX() + speed);
                }

                break;

            case LEFT:
                currentWorldCoordinateX = gameSpriteRenderInformation.getWorldCoordinateX() - speed;
                characterAnimation.currentAnimationState = WalkingLEFT;

                if (currentWorldCoordinateX >= actionAreaInformation.actionAreaX()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateX(currentWorldCoordinateX);
                    this.gameSpriteRenderInformation.setScreenCoordinateX(gameSpriteRenderInformation.getScreenCoordinateX() - speed);
                }

                break;
        }
    }
}
