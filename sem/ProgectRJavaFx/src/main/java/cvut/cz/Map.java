package cvut.cz;

import cvut.cz.characters.Directions;
import cvut.cz.characters.GameCharacter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Map extends GameSprite{

    private List<Collision> collisions;

    public Map(URL pathToMap, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int targetCoordinateX, int targetCoordinateY, int targetWidth, int targetHeight, List<Collision> collisions)
    {
        super(pathToMap, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, targetCoordinateX, targetCoordinateY, targetWidth, targetHeight,0, 0);
        this.collisions = collisions;
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


    public void translateMap(Directions direction, double offset) {
        switch (direction) {
            case UP:
                screenCoordinateY += offset;
                break;
            case DOWN:
                screenCoordinateY -= offset;
                break;
            case LEFT:
                screenCoordinateX += offset;
                break;
            case RIGHT:
                screenCoordinateX -= offset;
                break;
        }
    }

    public void setPathToMap(URL pathToMap) { pathToImage = pathToMap; }
}
