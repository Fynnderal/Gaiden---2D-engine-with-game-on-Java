package cvut.cz.Animation;

/**
 *
 * @param sourceX - Starting X coordinate in source image, from which animation is created
 * @param sourceY - Starting Y coordinate in source image, from which animation is created
 * @param sourceWidth - Width of source image
 * @param sourceHeight - Height of source image
 * @param targetWidth - Width of final image on screen
 * @param targetHeight - Height of final image on screen
 */
public record AnimationInformation(int sourceX, int sourceY, int sourceWidth, int sourceHeight, int targetWidth, int targetHeight) { }
