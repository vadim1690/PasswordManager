module com.example.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens SoftwareStarter to javafx.fxml;
    opens Tests;
    exports SoftwareStarter;
    exports controller;
    opens controller to javafx.fxml;
}