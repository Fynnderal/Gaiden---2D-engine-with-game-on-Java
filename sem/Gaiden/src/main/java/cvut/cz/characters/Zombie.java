package cvut.cz.characters;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

import static cvut.cz.Animation.AnimationStates.*;
import static cvut.cz.Animation.AnimationStates.WalkingLeft;


/**
 * Represents one type of enemy - Zombie. It manages movement within its action area.
 * This type of enemy is slower and weaker compared to other enemies. It cannot patrol the area
 * and is restricted to its defined action area.
 */
public class Zombie extends Enemy{

    /**
     * Constructs a Zombie character with the specified character information, sprite details, and interaction details.
     *
     * @param characterInformation Information about the character's attributes.
     * @param gameSpriteSourceInformation Information about the source image for the character's sprite.
     * @param gameSpriteRenderInformation Information about how the sprite is rendered on the screen.
     * @param actionAreaInformation Information about the area where the Zombie can act.
     * @param mainCharacter The main playable character in the game.
     */
    public Zombie(CharacterInformation characterInformation,
                  GameSpriteSourceInformation gameSpriteSourceInformation,
                  GameSpriteRenderInformation gameSpriteRenderInformation,
                  ActionAreaInformation actionAreaInformation,
                  PlayableCharacter mainCharacter) {
        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation, actionAreaInformation, mainCharacter);
    }

    /**
     * Updates the Zombie's state.
     * Should be called every frame.
     */
    @Override
    public void update() {
        if (!isPlayerInActionArea())
            this.characterInformation.setCurrentState(States.IDLE);

        super.update();
    }

    /**
     * Implements movement for the Zombie. The Zombie cannot leave its action area.
     *
     * @param direction The direction of movement.
     * @param speed The speed of movement.
     */
    @Override
    protected void move(Directions direction, int speed) {
        int currentWorldCoordinateY;
        int currentWorldCoordinateX;
        switch (direction) {
            case UP:
                currentWorldCoordinateY = gameSpriteRenderInformation.getWorldCoordinateY() - speed;
                characterAnimation.currentAnimationState = WalkingUp;

                //Zombie can't leave his action area
                if (currentWorldCoordinateY>= actionAreaInformation.actionAreaY()){
                    this.gameSpriteRenderInformation.setWorldCoordinateY(currentWorldCoordinateY);
                    this.gameSpriteRenderInformation.setScreenCoordinateY(gameSpriteRenderInformation.getScreenCoordinateY() - speed);
                }

                break;

            case DOWN:
                currentWorldCoordinateY = gameSpriteRenderInformation.getWorldCoordinateY() + speed;
                characterAnimation.currentAnimationState = WalkingDown;

                //Zombie can't leave his action area
                if (currentWorldCoordinateY + collisionHeight <= actionAreaInformation.actionAreaY() + actionAreaInformation.actionAreaHeight()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateY(currentWorldCoordinateY);
                    this.gameSpriteRenderInformation.setScreenCoordinateY(gameSpriteRenderInformation.getScreenCoordinateY() + speed);
                }

                break;

            case RIGHT:
                currentWorldCoordinateX = gameSpriteRenderInformation.getWorldCoordinateX() + speed;
                characterAnimation.currentAnimationState = WalkingRight;

                //Zombie can't leave his action area
                if (currentWorldCoordinateX + collisionWidth <= actionAreaInformation.actionAreaX() + actionAreaInformation.actionAreaWidth()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateX(currentWorldCoordinateX);
                    this.gameSpriteRenderInformation.setScreenCoordinateX(gameSpriteRenderInformation.getScreenCoordinateX() + speed);
                }

                break;

            case LEFT:
                currentWorldCoordinateX = gameSpriteRenderInformation.getWorldCoordinateX() - speed;
                characterAnimation.currentAnimationState = WalkingLeft;

                //Zombie can't leave his action area
                if (currentWorldCoordinateX >= actionAreaInformation.actionAreaX()) {
                    this.gameSpriteRenderInformation.setWorldCoordinateX(currentWorldCoordinateX);
                    this.gameSpriteRenderInformation.setScreenCoordinateX(gameSpriteRenderInformation.getScreenCoordinateX() - speed);
                }

                break;
        }
    }
}
