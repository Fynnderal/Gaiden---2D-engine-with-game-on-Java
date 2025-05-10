package cvut.cz.Utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for controlling the logging behavior of the application.
 */
public class LoggerController {
    private LoggerController() {}

    /**
     * Disables all logging by setting the root logger's level to OFF.
     */
    public static void offLoggers() {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.OFF);
    }
}
