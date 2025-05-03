package cvut.cz.Animation;

/**
 * Saves the information about one frame of animation
 *
 * @param sourceX - Starting X coordinate in the source image from which the animation is created
 * @param sourceY - Starting Y coordinate in the source image from which the animation is created
 * @param sourceWidth - Width of the source image
 * @param sourceHeight - Height of the source image
 * @param targetWidth - Width of the final image on the screen
 * @param targetHeight - Height of the final image on the screen
 */
public record AnimationInformation(int sourceX, int sourceY, int sourceWidth, int sourceHeight, int targetWidth, int targetHeight) { }
