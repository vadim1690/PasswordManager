package controller;

import exceptions.ApplicationDoesNotExistException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ApplicationRecord;
import model.ManagementSystem;
import model.User;
import utilities.AlertUtilities;
import utilities.FontUtilities;
import utilities.StageFactory;
import utilities.WindowUtilities;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {
    private final String USER_NAME_PROPERTY = "userName";
    private final String USER_PASSWORD_PROPERTY = "password";
    private final String USER_LAST_MODIFIED_PROPERTY = "passwordLastModified";


    @FXML
    private ListView<Label> appList;
    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> lastModifiedColumn;


    @FXML
    private Button removeApp;
    @FXML
    private Button addUser;
    @FXML
    private Button removeUser;
    @FXML
    private Button modifyPassword;


    @FXML
    private Text appInformationText;
    @FXML
    private Text userInformationText;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;


    @FXML
    private Label appInformationLabel;
    @FXML
    private Label userInformationLabel;
    @FXML
    private Label passwordStrengthLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label editAppInformationLabel;
    @FXML
    private Label editUserInformationLabel;

    @FXML
    private Circle circle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableColumns();
        initAppList();
        disableTableNodes();
        disableListNodes();

    }

    private void initAppList() {
        try {
            for (ApplicationRecord app : ManagementSystem.getInstance().getAllApplications()) {
                Label appLabel = new Label(app.getOfficialName());
                appLabel.setFont(FontUtilities.APPS_LIST_FONT);
                appList.getItems().add(appLabel);
            }
        } catch (SQLException e) {
            AlertUtilities.sqlErrorAlert();
        }
    }

    @FXML
    private void generalInformationClicked() {
        try {
            Stage generalInformationStage = StageFactory.getStage(WindowUtilities.GENERAL_INFORMATION_WINDOW_NAME, appList, usersTable);
            generalInformationStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @FXML
    private void addApp() {
        try {
            Stage addAppStage = StageFactory.getStage(WindowUtilities.ADD_APP_WINDOW_NAME, appList, usersTable);
            addAppStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @FXML
    private void addUserToApp() {
        try {
            Stage addUserStage = StageFactory.getStage(WindowUtilities.ADD_USER_WINDOW_NAME, appList, usersTable);
            addUserStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void modifyPasswordClicked() {
        try {
            Stage modifyPasswordStage = StageFactory.getStage(WindowUtilities.MODIFY_PASSWORD_WINDOW_NAME, appList, usersTable);
            modifyPasswordStage.show();
            backgroundClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void tableClicked() {
        if (usersTable.getItems().isEmpty())
            disableListNodes();

        else if (usersTable.getSelectionModel().getSelectedItem() != null) {
            enableTableNodes();
        }
    }


    @FXML
    private void removeUser() {
        User userToDelete = usersTable.getSelectionModel().getSelectedItem();
        if (userToDelete == null)
            return;

        Optional result = AlertUtilities.deleteConfirmationAlert();
        if (result.get() == ButtonType.OK) {
            removeSelectedUserAfterConfirmation(userToDelete);
        } else {
            return;
        }
        disableTableNodes();
    }

    private void removeSelectedUserAfterConfirmation(User userToDelete) {
        try {
            ManagementSystem.getInstance().removeUserForApplication(
                    appList.getSelectionModel().getSelectedItem().getText(),
                    userToDelete.getUserName());
            usersTable.getItems().remove(userToDelete);
        } catch (Exception e) {
            AlertUtilities.errorAlert(e.getMessage());
        }
    }

    @FXML
    private void removeApp() {
        Label officialNameLabel = appList.getSelectionModel().getSelectedItem();
        if (officialNameLabel != null) {
            Optional result = AlertUtilities.deleteConfirmationAlert();
            if (result.get() == ButtonType.OK) {
                removeSelectedAppAfterConfirmation(officialNameLabel);
                disableListNodes();
            } else {
                return;
            }
        }
        disableTableNodes();
    }

    private void removeSelectedAppAfterConfirmation(Label officialNameLabel) {
        try {
            ManagementSystem.getInstance().removeApplication(officialNameLabel.getText());
            appList.getItems().remove(officialNameLabel);
        } catch (Exception e) {
            AlertUtilities.errorAlert(e.getMessage());
        }
    }


    @FXML
    private void selectedApp() {
        disableTableNodes();
        Label appName = appList.getSelectionModel().getSelectedItem();
        if (appName != null) {
            try {
                usersTable.getItems().clear();
                usersTable.getItems().addAll(ManagementSystem.getInstance().getAllUsersForApplication(appName.getText()));

            } catch (Exception e) {
                AlertUtilities.errorAlert(e.getMessage());
                return;
            }
            addUser.setDisable(false);
            enableListNodes();
        }
    }

    @FXML
    private void backgroundClicked() {
        disableTableNodes();
        disableListNodes();
    }

    @FXML
    private void editAppInformation(){
        // To complete
    }

    @FXML
    private void editUserInformation(){
        // To complete
    }

    private void disableListNodes() {
        appList.getSelectionModel().clearSelection();
        usersTable.getItems().clear();
        removeApp.setDisable(true);
        appInformationText.setText("");
        appInformationLabel.setVisible(false);
        editAppInformationLabel.setVisible(false);
    }

    private void enableListNodes() {
        removeApp.setDisable(false);
        editAppInformationLabel.setVisible(true);
        setAppInformation();
    }

    private void setAppInformation() {
        String information = null;
        try {
            information = ManagementSystem.getInstance().getApplicationInformation(appList.getSelectionModel().getSelectedItem().getText());
        } catch (ApplicationDoesNotExistException e) {
            AlertUtilities.errorAlert(e.getMessage());
        } catch (SQLException e) {
            AlertUtilities.sqlErrorAlert();
        }
        if (information == null || information.isEmpty()) {
            appInformationText.setText("");
            appInformationLabel.setVisible(false);

        } else {
            appInformationText.setText(information);
            appInformationText.setFont(FontUtilities.TEXT_INFORMATION_FONT);
            appInformationLabel.setVisible(true);
        }
    }

    private void setUserInformation() {
        String information = usersTable.getSelectionModel().getSelectedItem().getInformation();
        if (information == null || information.isEmpty()) {
            userInformationText.setText("");
            userInformationLabel.setVisible(false);
        } else {
            userInformationText.setText(information);
            userInformationText.setFont(FontUtilities.TEXT_INFORMATION_FONT);
            userInformationLabel.setVisible(true);

        }
    }

    private void setUsernameAndPasswordTextFields() {
        usernameTextField.setText(usersTable.getSelectionModel().getSelectedItem().getUserName());
        passwordTextField.setText(usersTable.getSelectionModel().getSelectedItem().getPassword());
    }


    private void initTableColumns() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>(USER_NAME_PROPERTY));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>(USER_PASSWORD_PROPERTY));
        lastModifiedColumn.setCellValueFactory(new PropertyValueFactory<>(USER_LAST_MODIFIED_PROPERTY));


    }


    private void disableTableNodes() {
        usersTable.getSelectionModel().clearSelection();
        addUser.setDisable(true);
        removeUser.setDisable(true);
        modifyPassword.setDisable(true);
        passwordStrengthLabel.setDisable(true);
        usernameLabel.setVisible(false);
        passwordLabel.setVisible(false);
        usernameTextField.setVisible(false);
        passwordTextField.setVisible(false);
        userInformationLabel.setVisible(false);
        circle.setFill(Color.WHITE);
        userInformationText.setText("");
        usernameTextField.clear();
        passwordTextField.clear();
        editUserInformationLabel.setVisible(false);
    }

    private void enableTableNodes() {
        addUser.setDisable(false);
        removeUser.setDisable(false);
        modifyPassword.setDisable(false);
        passwordStrengthLabel.setDisable(false);
        usernameLabel.setVisible(true);
        passwordLabel.setVisible(true);
        usernameTextField.setVisible(true);
        passwordTextField.setVisible(true);
        editUserInformationLabel.setVisible(true);
        setPasswordStrengthLight();
        setUserInformation();
        setUsernameAndPasswordTextFields();
    }


    private void setPasswordStrengthLight() {
        switch (usersTable.getSelectionModel().getSelectedItem().getPasswordStrength()) {
            case STRONG: {
                circle.setFill(Color.GREEN);
                break;
            }
            case MEDIUM: {
                circle.setFill(Color.ORANGE);
                break;
            }
            case WEAK: {
                circle.setFill(Color.RED);
                break;
            }
        }
    }

}