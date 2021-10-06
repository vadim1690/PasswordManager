package controller;

import exceptions.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.ManagementSystem;
import model.User;
import utilities.AlertUtilities;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPasswordWindowController implements Initializable {

   private TableView<User> tableView;
   private ListView<Label> appsList;
   private User userToModify;




    @FXML
    private PasswordField oldPasswordField;
    @FXML
    private PasswordField newPasswordField;



    @FXML
    private Button confirmButton;

    @FXML
    private Label usernameLabel;


    public void setUserTable(TableView tableView) {
        this.tableView = tableView;
        setUser(this.tableView.getSelectionModel().getSelectedItem());
    }

    private void setUser(User userToModify) {
        this.userToModify = userToModify;
    }

    public void setAppList(ListView appsList) {
        this.appsList = appsList;
    }

    public void setUserName(String username) {
        usernameLabel.setText(username);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        oldPasswordField.setFocusTraversable(false);
        newPasswordField.setFocusTraversable(false);

    }

    @FXML
    private void modifyPassword(){
        try {
            ManagementSystem.getInstance().modifyPassword(
                    appsList.getSelectionModel().getSelectedItem().getText(),
                    userToModify.getUserName(),
                    oldPasswordField.getText(),
                    newPasswordField.getText()
            );
        } catch (Exception e) {
            AlertUtilities.errorAlert(e.getMessage());
            return;
        }
        tableView.refresh();
        ((Stage) confirmButton.getScene().getWindow()).close();
    }
}
