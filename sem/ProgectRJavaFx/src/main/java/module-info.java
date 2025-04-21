module cvut.cz {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires javafx.swing;
    requires jdk.security.jgss;
    requires java.logging;


    opens cvut.cz to com.fasterxml.jackson.databind, javafx.fxml;
    opens cvut.cz.items to com.fasterxml.jackson.databind, javafx.fxml;

    exports cvut.cz;
    exports cvut.cz.items;
    exports cvut.cz.characters;
    exports cvut.cz.Map;
    opens cvut.cz.Map to com.fasterxml.jackson.databind, javafx.fxml;
    exports cvut.cz.GameSprite;
    opens cvut.cz.GameSprite to com.fasterxml.jackson.databind, javafx.fxml;
    exports cvut.cz.Model;
    opens cvut.cz.Model to com.fasterxml.jackson.databind, javafx.fxml;
}