package utilities;

import controller.AddAppWindowController;
import controller.AddUserWindowController;
import controller.ModifyPasswordWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import starter.Main;

import java.io.IOException;

public class StageFactory {


    public static Stage getStage(String stageName, ListView appList, TableView usersTable) throws IOException {
        //stage initialize
        Stage stage = new Stage();
        FXMLLoader loader;
        Scene scene;
        stage.setTitle(stageName);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);

        switch (stageName) {
            case WindowUtilities.ADD_APP_WINDOW_NAME: {
                //loading the fxml file
                loader = new FXMLLoader(Main.class.getResource(FileUtilities.ADD_APP_WINDOW_FXML));
                //setting the scene
                scene = new Scene(loader.load(), WindowUtilities.ADD_APP_WINDOW_WIDTH, WindowUtilities.ADD_APP_WINDOW_HEIGHT);
                stage.setScene(scene);
                //send the apps list to the add App Controller
                AddAppWindowController addAppWindowController = loader.getController();
                addAppWindowController.setAppList(appList);
                break;
            }
            case WindowUtilities.ADD_USER_WINDOW_NAME: {
                //loading the fxml file
                loader = new FXMLLoader(Main.class.getResource(FileUtilities.ADD_USER_WINDOW_FXML));
                //setting the scene
                scene = new Scene(loader.load(), WindowUtilities.ADD_USER_WINDOW_WIDTH, WindowUtilities.ADD_USER_WINDOW_HEIGHT);
                stage.setScene(scene);
                AddUserWindowController addUserWindowController = loader.getController();
                addUserWindowController.setUserTable(usersTable);
                addUserWindowController.setAppName(((Label) appList.getSelectionModel().getSelectedItem()).getText());
                break;

            }

            case WindowUtilities.MODIFY_PASSWORD_WINDOW_NAME: {
                //loading the fxml file
                loader = new FXMLLoader(Main.class.getResource(FileUtilities.MODIFY_PASSWORD_WINDOW_FXML));
                //setting the scene
                scene = new Scene(loader.load(), WindowUtilities.MODIFY_PASSWORD_WINDOW_WIDTH, WindowUtilities.MODIFY_PASSWORD_WINDOW_HEIGHT);
                stage.setScene(scene);
                ModifyPasswordWindowController ModifyPasswordController = loader.getController();
                ModifyPasswordController.setUserTable(usersTable);
                ModifyPasswordController.setApplicationName(((Label) appList.getSelectionModel().getSelectedItem()).getText());
                break;

            }
            case WindowUtilities.GENERAL_INFORMATION_WINDOW_NAME: {
                //loading the fxml file
                loader = new FXMLLoader(Main.class.getResource(FileUtilities.GENERAL_INFORMATION_WINDOW_FXML));
                //setting the scene
                scene = new Scene(loader.load(), WindowUtilities.GENERAL_INFORMATION_WINDOW_WIDTH, WindowUtilities.GENERAL_INFORMATION_WINDOW_HEIGHT);
                stage.setScene(scene);
                break;

            }
        }

        return stage;
    }

}
