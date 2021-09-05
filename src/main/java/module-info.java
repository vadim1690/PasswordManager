module com.example.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens softwareStarter to javafx.fxml;
    opens tests;
    exports softwareStarter;
    exports controller;
    opens controller to javafx.fxml;
    opens model to javafx.base;
}