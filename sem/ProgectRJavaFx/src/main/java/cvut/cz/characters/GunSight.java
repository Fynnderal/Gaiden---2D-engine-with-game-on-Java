package cvut.cz.characters;

import cvut.cz.GameSprite.GameSprite;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

public class GunSight extends GameSprite {
    private final int centerXRelativeToGunSight;
    private final int centerYRelativeToGunSight;

    public GunSight(GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation, int centerXRelativeToGunSight, int centerYRelativeToGunSight) {
        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.centerXRelativeToGunSight = centerXRelativeToGunSight;
        this.centerYRelativeToGunSight = centerYRelativeToGunSight;
    }

    public int getCenterXRelativeToGunSight() { return centerXRelativeToGunSight; }
    public int getCenterYRelativeToGunSight() { return centerYRelativeToGunSight; }
}
