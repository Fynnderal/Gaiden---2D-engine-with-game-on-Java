package cvut.cz.items;

import cvut.cz.GameSprite;
import cvut.cz.GameSpriteRenderInformation;
import cvut.cz.GameSpriteSourceInformation;

import java.net.URL;

public class Pointer extends GameSprite {

    public Pointer(GameSpriteSourceInformation gameSpriteSourceInformation){
        super(gameSpriteSourceInformation, new GameSpriteRenderInformation(0, 0, 0, 0, 0, 0));
    }

    public void move(int offsetX, int offsetY){
        this.gameSpriteRenderInformation.setScreenCoordinateX(gameSpriteRenderInformation.getScreenCoordinateX() + offsetX);
        this.gameSpriteRenderInformation.setScreenCoordinateY(gameSpriteRenderInformation.getScreenCoordinateY() + offsetY);
    }
}
