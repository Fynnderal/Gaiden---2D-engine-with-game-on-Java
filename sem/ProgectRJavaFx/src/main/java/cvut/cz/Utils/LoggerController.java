package cvut.cz.Utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerController {
    private LoggerController() {}

    public static void offLoggers() {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.OFF);
    }
}
