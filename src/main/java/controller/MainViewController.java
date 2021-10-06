package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ApplicationRecord;
import model.ManagementSystem;
import model.User;
import starter.Main;
import utilities.AlertUtilities;
import utilities.FileUtilities;
import utilities.FontUtilities;
import utilities.WindowUtilities;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {
    private final String USER_NAME_PROPERTY = "userName";
    private final String USER_PASSWORD_PROPERTY = "password";
    private final String USER_LAST_MODIFIED_PROPERTY = "passwordLastModified";


    @FXML
    private ListView<Label> appsList;
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> lastModifiedColumn;

    @FXML
    private Button addApp;
    @FXML
    private Button removeApp;
    @FXML
    private Button addUser;
    @FXML
    private Button removeUser;
    @FXML
    private Button modifyPassword;
    @FXML
    private Button generalInformation;


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
    private Circle circle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableColumns();
        initAppList();
        disableTableNodes();
        disableListNodes();

    }

    private void initAppList() {

        for (ApplicationRecord app : ManagementSystem.getInstance().getAllApplications()) {
            Label appLabel = new Label(app.getOfficialName());
            appLabel.setFont(FontUtilities.APPS_LIST_FONT);
            appsList.getItems().add(appLabel);
        }
    }

    @FXML
    private void generalInformationClicked() {
        Stage generalInformationStage = getStage(
                WindowUtilities.GENERAL_INFORMATION_WINDOW_NAME,
                WindowUtilities.GENERAL_INFORMATION_WINDOW_WIDTH,
                WindowUtilities.GENERAL_INFORMATION_WINDOW_HEIGHT,
                FileUtilities.GENERAL_INFORMATION_WINDOW_FXML);

        generalInformationStage.show();

    }


    @FXML
    private void addApp() {
        Stage addAppStage = getStage(
                WindowUtilities.ADD_APP_WINDOW_NAME,
                WindowUtilities.ADD_APP_WINDOW_WIDTH,
                WindowUtilities.ADD_APP_WINDOW_HEIGHT,
                FileUtilities.ADD_APP_WINDOW_FXML);

        addAppStage.show();

    }


    @FXML
    private void addUserToApp() {

        Stage addUserStage = getStage(
                WindowUtilities.ADD_USER_WINDOW_NAME,
                WindowUtilities.ADD_USER_WINDOW_WIDTH,
                WindowUtilities.ADD_USER_WINDOW_HEIGHT,
                FileUtilities.ADD_USER_WINDOW_FXML);
        addUserStage.show();

    }

    @FXML
    private void modifyPasswordClicked() {
        Stage ModifyPasswordStage = getStage(
                WindowUtilities.MODIFY_PASSWORD_WINDOW_NAME,
                WindowUtilities.MODIFY_PASSWORD_WINDOW_WIDTH,
                WindowUtilities.MODIFY_PASSWORD_WINDOW_HEIGHT,
                FileUtilities.MODIFY_PASSWORD_WINDOW_FXML);
        ModifyPasswordStage.show();
        disableTableNodes();

    }


    @FXML
    private void tableClicked() {
        if (tableView.getItems().isEmpty())
            disableListNodes();

        else if (tableView.getSelectionModel().getSelectedItem() != null) {
            enableTableNodes();
        }
    }


    @FXML
    private void removeUser() {
        User userToDelete = tableView.getSelectionModel().getSelectedItem();
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
                    appsList.getSelectionModel().getSelectedItem().getText(),
                    userToDelete.getUserName(),
                    userToDelete.getPassword());
            tableView.getItems().remove(userToDelete);
        } catch (Exception e) {
            AlertUtilities.errorAlert(e.getMessage());
        }
    }

    @FXML
    private void removeApp() {
        Label officialNameLabel = appsList.getSelectionModel().getSelectedItem();
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
            appsList.getItems().remove(officialNameLabel);
        } catch (Exception e) {
            AlertUtilities.errorAlert(e.getMessage());
        }
    }


    @FXML
    private void selectedApp() {
        disableTableNodes();
        Label appName = appsList.getSelectionModel().getSelectedItem();
        if (appName != null) {
            try {
                tableView.getItems().clear();
                tableView.getItems().addAll(ManagementSystem.getInstance().getUserNameAndPasswordForApplication(appName.getText()));

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

    private void disableListNodes() {
        appsList.getSelectionModel().clearSelection();
        tableView.getItems().clear();
        removeApp.setDisable(true);
        appInformationText.setText("");
        appInformationLabel.setVisible(false);
    }

    private void enableListNodes() {
        removeApp.setDisable(false);
        setAppInformation();

    }

    private void setAppInformation() {
        String information = ManagementSystem.getInstance().getApplicationRecordByOfficialName(appsList.getSelectionModel().getSelectedItem().getText()).getInformation();
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
        String information = tableView.getSelectionModel().getSelectedItem().getInformation();
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
        usernameTextField.setText(tableView.getSelectionModel().getSelectedItem().getUserName());
        passwordTextField.setText(tableView.getSelectionModel().getSelectedItem().getPassword());
    }


    private void initTableColumns() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>(USER_NAME_PROPERTY));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>(USER_PASSWORD_PROPERTY));
        lastModifiedColumn.setCellValueFactory(new PropertyValueFactory<>(USER_LAST_MODIFIED_PROPERTY));


    }


    private void disableTableNodes() {
        tableView.getSelectionModel().clearSelection();
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
        setPasswordStrengthLight();
        setUserInformation();
        setUsernameAndPasswordTextFields();
    }


    private void setPasswordStrengthLight() {
        switch (tableView.getSelectionModel().getSelectedItem().getPasswordStrength()) {
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

    private Stage getStage(String stageName, double width, double height, String FXML) {
        //stage initialize
        Stage stage = new Stage();
        stage.setTitle(stageName);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);


        //loading the fxml file
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(FXML));
        try {
            //setting the scene
            Scene scene = new Scene(loader.load(), width, height);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }


        switch (stageName) {
            case WindowUtilities.ADD_APP_WINDOW_NAME: {
                //send the apps list to the add App Controller
                AddAppWindowController addAppWindowController = loader.getController();
                addAppWindowController.setAppList(appsList);
                break;
            }
            case WindowUtilities.ADD_USER_WINDOW_NAME: {
                AddUserWindowController addUserWindowController = loader.getController();
                addUserWindowController.setUserTable(tableView);
                addUserWindowController.setAppList(appsList);
                addUserWindowController.setAppName(appsList.getSelectionModel().getSelectedItem().getText());
                break;

            }

            case WindowUtilities.MODIFY_PASSWORD_WINDOW_NAME: {
                ModifyPasswordWindowController ModifyPasswordController = loader.getController();
                ModifyPasswordController.setUserTable(tableView);
                ModifyPasswordController.setAppList(appsList);
                ModifyPasswordController.setUserName(tableView.getSelectionModel().getSelectedItem().getUserName());
                break;

            }
        }

        return stage;
    }


}