package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertUtilities {
    private static final String ERROR_ALERT_TITLE = "Error";


    private static final String DELETE_CONFIRMATION_ALERT_TITLE = "Delete Confirmation Dialog";
    private static final String DELETE_CONFIRMATION_MESSAGE = "Are you sure you want to delete? ";
    private static final String SQL_ERROR_MESSAGE = "Database error occurred";

    public static void errorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static Optional<ButtonType> deleteConfirmationAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(DELETE_CONFIRMATION_ALERT_TITLE);
        alert.setContentText(DELETE_CONFIRMATION_MESSAGE);
        alert.setHeaderText(null);
       return alert.showAndWait();

    }

    public static void sqlErrorAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(SQL_ERROR_MESSAGE);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
