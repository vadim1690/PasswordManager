package SoftwareStarter;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ManagementSystem;
import model.ModelInterface;
import view.GUI;
import view.UserInterface;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));

        ModelInterface model = ManagementSystem.getInstance();
        UserInterface userInterface = new GUI(stage);
        Controller controller = new Controller(model,userInterface);

    }

    public static void main(String[] args) {
        launch();
    }
}