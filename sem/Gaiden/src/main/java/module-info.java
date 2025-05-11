module cvut.cz {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires javafx.swing;
    requires jdk.security.jgss;
    requires java.logging;
    requires jdk.compiler;


    opens cvut.cz to com.fasterxml.jackson.databind, javafx.fxml;
    opens cvut.cz.items to com.fasterxml.jackson.databind, javafx.fxml;
    opens cvut.cz.Map to com.fasterxml.jackson.databind, javafx.fxml;
    opens cvut.cz.characters to com.fasterxml.jackson.databind, javafx.fxml;
    opens cvut.cz.GameSprite to com.fasterxml.jackson.databind, javafx.fxml;
    opens cvut.cz.Animation to com.fasterxml.jackson.databind, javafx.fxml;
    opens cvut.cz.Model to com.fasterxml.jackson.databind, javafx.fxml;


    exports cvut.cz;
    exports cvut.cz.items;
    exports cvut.cz.characters;
    exports cvut.cz.Map;
    exports cvut.cz.GameSprite;
    exports cvut.cz.Model;
    exports cvut.cz.Animation;
    exports cvut.cz.LevelCreator;
    opens cvut.cz.LevelCreator to com.fasterxml.jackson.databind, javafx.fxml;
    exports cvut.cz.Utils;
    opens cvut.cz.Utils to com.fasterxml.jackson.databind, javafx.fxml;
}