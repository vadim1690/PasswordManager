package starter;

import database.Datasource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ManagementSystem;
import utilities.FileUtilities;
import utilities.WindowUtilities;

import java.io.IOException;

public class Main extends Application {
    public static final String MAIN_STAGE_TITLE = "Password Manager";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {


        Parent root = FXMLLoader.load(Main.class.getResource(FileUtilities.MAIN_VIEW_WINDOW_FXML));
        primaryStage.setTitle(MAIN_STAGE_TITLE);
        Scene scene = new Scene(root, WindowUtilities.MAIN_VIEW_WINDOW_WIDTH, WindowUtilities.MAIN_VIEW_WINDOW_HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void init() throws Exception {
        super.init();
        Datasource.getInstance().open();
        ManagementSystem.getInstance().readFromDatabase();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        ManagementSystem.getInstance().saveToDatabase();
        Datasource.getInstance().close();
    }
}