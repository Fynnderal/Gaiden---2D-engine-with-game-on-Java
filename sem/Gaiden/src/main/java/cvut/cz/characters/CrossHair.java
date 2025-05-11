package cvut.cz.characters;

import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

/**
 * Represents cross hair used by the player to aim.
 */
public class CrossHair extends GameSprite {
    //X coordinate of the cross hair center (in pixels) relative to the X coordinate of the cross hair on the screen
    private final int centerXRelativeToGunSight;

    //Y coordinate of the cross hair center (in pixels) relative to the Y coordinate of the cross hair on the screen
    private final int centerYRelativeToGunSight;

    /**
     * Initializes the CrossHair to specified values.
     *
     * @param gameSpriteSourceInformation - information about sprite of the cross hair
     * @param gameSpriteRenderInformation - information about final image of the cross hair on the screen
     * @param centerXRelativeToGunSight - X coordinate of the cross hair center (in pixels) relative to the X coordinate of the cross hair on the screen
     * @param centerYRelativeToGunSight - Y coordinate of the cross hair center (in pixels) relative to the Y coordinate of the cross hair on the screen
     */
    public CrossHair(GameSpriteSourceInformation gameSpriteSourceInformation,
                     GameSpriteRenderInformation gameSpriteRenderInformation,
                     int centerXRelativeToGunSight, int centerYRelativeToGunSight) {

        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.centerXRelativeToGunSight = centerXRelativeToGunSight;
        this.centerYRelativeToGunSight = centerYRelativeToGunSight;
    }

    /**
     * Gets the X coordinate of the cross hair center relative to the gun sight.
     *
     * @return The X coordinate of the cross hair center (in pixels).
     */
    public int getCenterXRelativeToGunSight() { return centerXRelativeToGunSight; }

    /**
     * Gets the Y coordinate of the cross hair center relative to the gun sight.
     *
     * @return The Y coordinate of the cross hair center (in pixels).
     */
    public int getCenterYRelativeToGunSight() { return centerYRelativeToGunSight; }
}
