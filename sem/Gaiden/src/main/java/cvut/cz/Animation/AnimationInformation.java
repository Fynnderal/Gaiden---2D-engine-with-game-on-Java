package cvut.cz.Animation;

/**
 * Represents information about a single frame of animation.
 * This record is used to define the source and target dimensions
 * for rendering an animation frame on the screen.
 *
 * @param sourceX       The starting X coordinate (in pixels) in the source image from which the animation frame is extracted.
 * @param sourceY       The starting Y coordinate (in pixels) in the source image from which the animation frame is extracted.
 * @param sourceWidth   The width (in pixels) of the animation frame in the source image.
 * @param sourceHeight  The height (in pixels) of the animation frame in the source image.
 * @param targetWidth   The width (in pixels) of the animation frame when rendered on the screen.
 * @param targetHeight  The height (in pixels) of the animation frame when rendered on the screen.
 */
public record AnimationInformation(int sourceX,
                                   int sourceY,
                                   int sourceWidth,
                                   int sourceHeight,
                                   int targetWidth,
                                   int targetHeight) { }
