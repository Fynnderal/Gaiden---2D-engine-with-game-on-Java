package cvut.cz;

import cvut.cz.Utils.LoggerController;
import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        if (args.length > 0){
            if (args[0].equals("loggersOff")) {
                LoggerController.offLoggers();
                Application.launch(MainApplication.class, args);
                return;
            }

        }
        LoggerController.enableFileLogging();
        Application.launch(MainApplication.class, args);
    }
}
