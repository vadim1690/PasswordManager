module com.example.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.sql;
    requires org.mockito;


    opens starter to javafx.fxml;
    opens tests;
    exports starter;
    exports controller;
    exports model;
    opens model to javafx.base, javafx.fxml;
    opens controller to javafx.base, javafx.fxml;
    exports tests;
}