package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.ManagementSystem;
import model.User;
import utilities.AlertUtilities;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyPasswordWindowController implements Initializable {

    private TableView<User> tableView;
    private User userToModify;


    @FXML
    private PasswordField oldPasswordField;
    @FXML
    private PasswordField newPasswordField;
    private String officialName;

    @FXML
    private Label usernameLabel;


    public void setUserTable(TableView tableView) {
        this.tableView = tableView;
        setUser(this.tableView.getSelectionModel().getSelectedItem());
        setUserName(userToModify.getUserName());
    }

    private void setUser(User userToModify) {
        this.userToModify = userToModify;
    }

    private void setUserName(String username) {
        usernameLabel.setText(username);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        oldPasswordField.setFocusTraversable(false);
        newPasswordField.setFocusTraversable(false);

    }

    public void setApplicationName(String applicationName) {
        this.officialName = applicationName;
    }

    @FXML
    private void modifyPassword() {
        try {
            ManagementSystem.getInstance().modifyPassword(
                    officialName,
                    userToModify.getUserName(),
                    oldPasswordField.getText(),
                    newPasswordField.getText()
            );
        } catch (SQLException e) {
            AlertUtilities.sqlErrorAlert();
        } catch (Exception e) {
            AlertUtilities.errorAlert(e.getMessage());
            return;
        }
        ((Stage) oldPasswordField.getScene().getWindow()).close();
    }


}
