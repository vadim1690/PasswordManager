package view;

import softwareStarter.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ModelInterface;

import java.io.IOException;

public class GUI implements UserInterface {

    public static final String MAIN_STAGE_TITLE = "Password Manager";

    private ModelInterface controller;

    public GUI(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(Main.class.getResource("MainView.fxml"));
        primaryStage.setTitle(MAIN_STAGE_TITLE);
        primaryStage.setScene(new Scene(root,1000,800));
        primaryStage.show();
    }

    @Override
    public void registerController(ModelInterface controller) {
        this.controller = controller;
    }
}
