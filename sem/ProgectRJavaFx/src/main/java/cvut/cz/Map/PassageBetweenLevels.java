package cvut.cz.Map;


import cvut.cz.characters.PlayableCharacter;


/**
 * Represents a passage between levels in the game.
 * Defines the area of the passage and provides functionality to check if a player is within it.
 */
public class PassageBetweenLevels {
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    /**
     * Constructs a new PassageBetweenLevels object with the specified dimensions.
     *
     * @param x The X-coordinate of the top-left corner of the passage.
     * @param y The Y-coordinate of the top-left corner of the passage.
     * @param width The width of the passage.
     * @param height The height of the passage.
     */
    public PassageBetweenLevels(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Checks if the specified player is within the boundaries of the passage.
     *
     * @param player The playable character to check.
     * @return True if the player is within the passage, false otherwise.
     */
    public boolean isPlayerWithinPassage(PlayableCharacter player){
        return (
                player.getGameSpriteRenderInformation().getWorldCoordinateX() + player.getCollisionWidth() > x &&
                player.getGameSpriteRenderInformation().getWorldCoordinateX() < x + width &&
                player.getGameSpriteRenderInformation().getWorldCoordinateY() + player.getCollisionHeight() > y &&
                player.getGameSpriteRenderInformation().getWorldCoordinateY() < y + height
                );
    }
}
