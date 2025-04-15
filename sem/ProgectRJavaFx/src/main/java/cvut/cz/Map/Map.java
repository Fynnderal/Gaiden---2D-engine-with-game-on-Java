package cvut.cz.Map;

import cvut.cz.GameSprite;
import cvut.cz.GameSpriteRenderInformation;
import cvut.cz.GameSpriteSourceInformation;
import cvut.cz.characters.Directions;
import cvut.cz.items.Item;

import java.util.List;

public class Map extends GameSprite {

    private final List<Collision> collisions;
    private List<Item> items;

    public Map(List<Collision> collisions, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation)
    {
        super(gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.gameSpriteRenderInformation.setWorldCoordinateX(0);
        this.gameSpriteRenderInformation.setWorldCoordinateY(0);

        this.collisions = collisions;
    }

    public void checkNearestAvailableItems(){

    }

    public boolean checkCollisions(int pathRectXLeft, int pathRectYUp, int pathRectXRight, int pathRectYDown, Collision collidedWith) {
        for (Collision collision : collisions) {
            if (pathRectXLeft < collision.getWorldCoordinateX() + collision.getWidth() && pathRectXRight > collision.getWorldCoordinateX() && pathRectYUp < collision.getWorldCoordinateY() + collision.getHeight() && pathRectYDown > collision.getWorldCoordinateY()) {
                collidedWith.setWorldCoordinateX(collision.getWorldCoordinateX());
                collidedWith.setWorldCoordinateY(collision.getWorldCoordinateY());
                collidedWith.setWidth(collision.getWidth());
                collidedWith.setHeight(collision.getHeight());

                return true;
            }
        }
        return false;
    }

    public void translateMap(Directions direction, int offset) {
        switch (direction) {
            case UP:
                this.gameSpriteRenderInformation.setScreenCoordinateY(gameSpriteRenderInformation.getScreenCoordinateY() + offset);
                break;
            case DOWN:
                this.gameSpriteRenderInformation.setScreenCoordinateY(gameSpriteRenderInformation.getScreenCoordinateY() - offset);
                break;
            case LEFT:
                this.gameSpriteRenderInformation.setScreenCoordinateX(gameSpriteRenderInformation.getScreenCoordinateX() + offset);
                break;
            case RIGHT:
                this.gameSpriteRenderInformation.setScreenCoordinateX(gameSpriteRenderInformation.getScreenCoordinateX() - offset);
                break;
        }
    }
}
