package cvut.cz.characters;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

/**
 * Represents a Non-Playable Character in the game.
 * NPCs extend the behavior of the GameCharacter class and provide unique interaction capabilities
 * with the main playable character, such as visibility detection, interaction, and dialogue handling.
 */
public abstract class NPC extends GameCharacter{
    protected ActionAreaInformation actionAreaInformation;

    protected PlayableCharacter mainCharacter;

    //true if the player interacts with the NPC at the moment
    protected boolean interactingWithPlayer;

    //true if the player can interact with the NPC
    protected boolean isInteractable;

    //true if the NPC awaiting for player to choose phrase in dialogue
    protected boolean waitingForAnswer;

    //Index of current phrase, that player is saying at the moment.
    protected int currentPlayersAnswer;

    /**
     * Constructs a non-playable character with the specified character information, sprite information,
     * and interaction details.
     *
     * @param characterInformation an object containing basic information about the character,
     * @param gameSpriteSourceInformation information about the source image used for the sprite, including
     * @param gameSpriteRenderInformation information about how the sprite should be rendered on the screen
     * @param actionAreaInformation information about the area around the NPC where it can act
     * @param mainCharacter the main player
     */
    public NPC(CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation,
                 GameSpriteRenderInformation gameSpriteRenderInformation, ActionAreaInformation actionAreaInformation, PlayableCharacter mainCharacter) {

        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.actionAreaInformation = actionAreaInformation;
        this.isInteractable = true;
        this.interactingWithPlayer = false;
        this.waitingForAnswer = false;
        this.mainCharacter = mainCharacter;
    }

    /**
     * Checks if the player can be detected by the NPC.
     * It uses the spotting radius defined in the action area information and distance to the player.
     */
    protected void spotPlayer() {
        if (isBlocked)
            return;

        int mainCharacterX = mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateX();
        int mainCharacterY = mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateY();

        if (isPlayerInActionArea()) {
            //calculate distance between the player and the NPC
            int distance = calculateLengthOfVector(this.gameSpriteRenderInformation.getWorldCoordinateX(), this.gameSpriteRenderInformation.getWorldCoordinateY(),
                    mainCharacterX, mainCharacterY);

            if (distance <= actionAreaInformation.spottingRadius())
                this.characterInformation.setCurrentState(States.CHASING);
        }
    }

    /**
     * Checks if the player is within the action area of the NPC.
     *
     * @return true if the player is within the action area, false otherwise
     */
    public boolean isPlayerInActionArea() {
        return (
            mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateX() + collisionWidth / 2 < actionAreaInformation.actionAreaX() + actionAreaInformation.actionAreaWidth() &&
            mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateX() + mainCharacter.getCollisionWidth() / 2 >= actionAreaInformation.actionAreaX() &&
            mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateY() + collisionHeight / 2 < actionAreaInformation.actionAreaY() + actionAreaInformation.actionAreaHeight() &&
            mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateY() + mainCharacter.getCollisionHeight() / 2 >= actionAreaInformation.actionAreaY()
        );
    }

    /**
     * Implements abstract attack method. NPC can't attack by default.s
     */
    @Override
    protected void attack() {
        System.out.println("I am peaceful");
    }

    /**
     * Implements abstract attack method. NPC can't move by default.
     * @param direction - direction of movement
     * @param speed - speed of movement
     */
    @Override
    protected void move(Directions direction, int speed) {
        System.out.println("I can't move");
    }

    /**
     * Gets the index of the current dialogue phrase chosen by the player.
     *
     * @return the index of the current player's answer
     */
    public int getCurrentPlayersAnswer() { return currentPlayersAnswer; }

    /**
     * Checks if the NPC is waiting for the player to choose a dialogue option.
     *
     * @return true if the NPC is waiting for an answer, false otherwise
     */
    public boolean isWaitingForAnswer() { return waitingForAnswer; }

    /**
     * Abstract method for handling interaction with the NPC.
     *
     * @return an array of strings representing the interaction dialogue or options
     */
    public abstract String[] interact();

    /**
     * Abstract method for changing the player's dialogue choice.
     *
     * @param direction the direction in which to change the player's answer
     */
    public abstract void changePlayersAnswer(Directions direction);
}
