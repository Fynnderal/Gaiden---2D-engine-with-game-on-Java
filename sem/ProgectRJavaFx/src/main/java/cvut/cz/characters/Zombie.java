package cvut.cz.characters;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

import static cvut.cz.Animation.AnimationStates.*;
import static cvut.cz.Animation.AnimationStates.WalkingLeft;

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
                characterAnimation.currentAnimationState = WalkingUp;
                if (currentWorldCoordinateY >= actionAreaInformation.actionAreaY()){
                    this.gameSpriteRenderInformation.setWorldCoordinateY(currentWorldCoordinateY);
                    this.gameSpriteRenderInformation.setScreenCoordinateY(gameSpriteRenderInformation.getScreenCoordinateY() - speed);
                }

                break;

            case DOWN:
                currentWorldCoordinateY = gameSpriteRenderInformation.getWorldCoordinateY() + speed;
                characterAnimation.currentAnimationState = WalkingDown;

                if (currentWorldCoordinateY <= actionAreaInformation.actionAreaY() + actionAreaInformation.actionAreaHeight()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateY(currentWorldCoordinateY);
                    this.gameSpriteRenderInformation.setScreenCoordinateY(gameSpriteRenderInformation.getScreenCoordinateY() + speed);
                }

                break;

            case RIGHT:
                currentWorldCoordinateX = gameSpriteRenderInformation.getWorldCoordinateX() + speed;
                characterAnimation.currentAnimationState = WalkingRight;

                if (currentWorldCoordinateX <= actionAreaInformation.actionAreaX() + actionAreaInformation.actionAreaWidth()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateX(currentWorldCoordinateX);
                    this.gameSpriteRenderInformation.setScreenCoordinateX(gameSpriteRenderInformation.getScreenCoordinateX() + speed);
                }

                break;

            case LEFT:
                currentWorldCoordinateX = gameSpriteRenderInformation.getWorldCoordinateX() - speed;
                characterAnimation.currentAnimationState = WalkingLeft;

                if (currentWorldCoordinateX >= actionAreaInformation.actionAreaX()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateX(currentWorldCoordinateX);
                    this.gameSpriteRenderInformation.setScreenCoordinateX(gameSpriteRenderInformation.getScreenCoordinateX() - speed);
                }

                break;
        }
    }
}
