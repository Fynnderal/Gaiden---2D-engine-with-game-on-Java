package cvut.cz.Utils;

import java.io.File;
import java.util.logging.*;

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

    /**
     * Enables logging to a file by adding a FileHandler to the root logger.
     */
    public static void enableFileLogging() {
        try {
            Logger rootLogger = Logger.getLogger("");

            for (Handler handler : rootLogger.getHandlers()) {
                rootLogger.removeHandler(handler);
            }

            String pathToLogs = "logs.log";
            File logs = new File(pathToLogs);
            if (logs.exists()) {
                logs.delete();
            }


            FileHandler fileHandler = new FileHandler(pathToLogs, true);
            fileHandler.setFormatter(new SimpleFormatter()); // простой формат (как в консоли)

            rootLogger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
