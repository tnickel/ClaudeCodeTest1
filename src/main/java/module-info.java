module de.wetter {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;
    requires java.net.http;

    opens de.wetter.app to javafx.fxml;
    opens de.wetter.model to com.google.gson;
    opens de.wetter.ui to javafx.fxml;

    exports de.wetter.app;
    exports de.wetter.model;
    exports de.wetter.service;
    exports de.wetter.ui;
}
