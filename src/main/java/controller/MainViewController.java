package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.PasswordStrength;
import model.User;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {
    @FXML
    private ListView<String> appsList;

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
    public void addApp(ActionEvent actionEvent) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableColumns();
        disableTableNodes();
        disableListNodes();
        tableView.getItems().add(new User("vadim1690", "Tqcv1234"));

    }



    @FXML
    private void tableClicked() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            enableTableNodes();
        }
    }


    @FXML
    private void removeUser() {
        tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
        disableTableNodes();
    }




    @FXML
    private void selectedApp() {
        disableTableNodes();
        if (appsList.getSelectionModel().getSelectedItem()!=null){
            enableListNodes();
        }
    }

    @FXML
    private void backgroundClicked(){
        disableTableNodes();
        disableListNodes();
    }

    private void disableListNodes() {
        addApp.setDisable(true);
        removeApp.setDisable(true);
    }

    private void enableListNodes() {
        addApp.setDisable(false);
        removeApp.setDisable(false);
    }


    private void initTableColumns() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        lastModifiedColumn.setCellValueFactory(new PropertyValueFactory<>("passwordLastModified"));


    }


    private void disableTableNodes() {
        tableView.getSelectionModel().clearSelection();
        addUser.setDisable(true);
        removeUser.setDisable(true);
        modifyPassword.setDisable(true);
        passwordStrengthLabel.setDisable(true);
        circle.setFill(Color.WHITE);

    }

    private void enableTableNodes() {
        addUser.setDisable(false);
        removeUser.setDisable(false);
        modifyPassword.setDisable(false);
        passwordStrengthLabel.setDisable(false);
        setPasswordStrengthLight();

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



}