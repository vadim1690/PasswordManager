package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.ManagementSystem;
import model.User;
import utilities.AlertUtilities;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserWindowController implements Initializable {


    private ListView<Label> appsList;

    private TableView<User> tableView;

    @FXML
    private TextField tfUserName;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfInformation;

    @FXML
    private Button addButton;

    @FXML
    private Label appName;


    public void setUserTable(TableView tableView) {
        this.tableView = tableView;
    }

    public void setAppList(ListView appsList) {
        this.appsList = appsList;

    }

    public void setAppName(String officialName) {
        appName.setText(officialName);
    }

    @FXML
    public void addUser() {
        User user;
        try {
            user = ManagementSystem.getInstance().addUserToApplication(appName.getText(), tfUserName.getText(), pfPassword.getText(), tfInformation.getText());
        } catch (Exception e) {
            AlertUtilities.errorAlert(e.getMessage());
            return;
        }
        if (user != null)
            tableView.getItems().add(user);
        ((Stage) addButton.getScene().getWindow()).close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfUserName.setFocusTraversable(false);
        pfPassword.setFocusTraversable(false);
        tfInformation.setFocusTraversable(false);


    }
}
