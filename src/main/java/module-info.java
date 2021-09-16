module com.example.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens softwareStarter to javafx.fxml;
    opens tests;
    exports softwareStarter;
    exports controller;
    exports model;
    opens model to javafx.base, javafx.fxml;
    opens controller to javafx.base, javafx.fxml;
}