package controller;

import exceptions.IllegalPasswordException;
import exceptions.IllegalUserNameException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ManagementSystem;
import model.User;
import utilities.AlertUtilities;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddUserWindowController implements Initializable {


    private TableView tableView;

    @FXML
    private TextField tfUserName;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfInformation;


    @FXML
    private Label appName;


    public void setUserTable(TableView tableView) {
        this.tableView = tableView;
    }

    public void setAppName(String officialName) {
        appName.setText(officialName);
    }

    @FXML
    public void addUser() {

        try {
            checkIllegalUserName(tfUserName.getText());
            checkIllegalPassword(pfPassword.getText());
            User user = ManagementSystem.getInstance().addUser(appName.getText(), tfUserName.getText(), pfPassword.getText(), tfInformation.getText());
            if (user != null)
                tableView.getItems().add(user);
            ((Stage) tfUserName.getScene().getWindow()).close();
        } catch (SQLException e) {
            AlertUtilities.sqlErrorAlert();
        } catch (Exception e) {
            AlertUtilities.errorAlert(e.getMessage());

        }


    }

    private void checkIllegalUserName(String userName) throws IllegalUserNameException {
        if (userName == null || userName.isEmpty())
            throw new IllegalUserNameException();
    }

    private void checkIllegalPassword(String password) throws IllegalPasswordException {
        if (password == null || password.isEmpty())
            throw new IllegalPasswordException();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfUserName.setFocusTraversable(false);
        pfPassword.setFocusTraversable(false);
        tfInformation.setFocusTraversable(false);
    }
}
