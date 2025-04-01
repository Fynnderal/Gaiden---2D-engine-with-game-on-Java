module cvut.cz {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens cvut.cz to javafx.fxml;
    exports cvut.cz;
}