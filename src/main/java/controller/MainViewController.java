package controller;

import exceptions.ApplicationDoesNotExistException;
import exceptions.IllegalApplicationNameException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ManagementSystem;
import model.PasswordStrength;
import model.User;
import softwareStarter.Main;
import utilities.AlertUtilities;
import utilities.FileUtilities;
import utilities.FontUtilities;
import utilities.WindowUtilities;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {
    private final String USER_NAME_PROPERTY = "userName";
    private final String USER_PASSWORD_PROPERTY = "password";
    private final String USER_LAST_MODIFIED_PROPERTY = "passwordLastModified";


    private Parent root;
    private FXMLLoader loader;
    @FXML
    private ListView<Label> appsList;
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, LocalDate> lastModifiedColumn;
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
    private Label passwordStrengthLabel;
    @FXML
    private Circle circle;
    @FXML
    private Text appInformationText;
    @FXML
    private Text userInformationText;

    @FXML
    private Label appInformationLabel;
    @FXML
    private Label userInformationLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableColumns();
        disableTableNodes();
        disableListNodes();
    }


    @FXML
    public void addApp() {
        Stage addAppStage = getStage(
                WindowUtilities.ADD_APP_WINDOW_NAME,
                WindowUtilities.ADD_APP_WINDOW_WIDTH,
                WindowUtilities.ADD_APP_WINDOW_HEIGHT,
                FileUtilities.ADD_APP_WINDOW_FXML);

        addAppStage.show();
    }


    @FXML
    public void addUserToApp() {

        Stage addUserStage = getStage(
                WindowUtilities.ADD_USER_WINDOW_NAME,
                WindowUtilities.ADD_USER_WINDOW_WIDTH,
                WindowUtilities.ADD_USER_WINDOW_HEIGHT,
                FileUtilities.ADD_USER_WINDOW_FXML);
        addUserStage.show();

    }


    @FXML
    private void tableClicked() {
        if (tableView.getItems().isEmpty())
            disableListNodes();

        if (tableView.getSelectionModel().getSelectedItem() != null) {
            enableTableNodes();
            userInformationText.setText(tableView.getSelectionModel().getSelectedItem().getInformation());
            userInformationText.setFont(FontUtilities.TEXT_INFORMATION_FONT);
        }
    }


    @FXML
    private void removeUser() {
        tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
        disableTableNodes();
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

        tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
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
        appInformationText.setText(ManagementSystem.getInstance().getApplicationRecordByOfficialName(appsList.getSelectionModel().getSelectedItem().getText()).getInformation());
        appInformationText.setFont(FontUtilities.TEXT_INFORMATION_FONT);
        appInformationLabel.setVisible(true);
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
        circle.setFill(Color.WHITE);
        userInformationLabel.setVisible(false);
        userInformationText.setText("");

    }

    private void enableTableNodes() {
        addUser.setDisable(false);
        removeUser.setDisable(false);
        modifyPassword.setDisable(false);
        passwordStrengthLabel.setDisable(false);
        setPasswordStrengthLight();
        userInformationLabel.setVisible(true);

    }

    private void setPasswordStrengthLight() {
        tableView.getSelectionModel().getSelectedItem().setPasswordStrength(PasswordStrength.MEDIUM);
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
        }

        return stage;
    }


}