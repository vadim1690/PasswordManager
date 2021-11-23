package controller;

import exceptions.IllegalApplicationNameException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.ManagementSystem;
import utilities.AlertUtilities;
import utilities.FontUtilities;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddAppWindowController implements Initializable {


    private ListView<Label> appsList;

    @FXML
    private TextField tfOfficialName;

    @FXML
    private TextField tfInformation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfOfficialName.setFocusTraversable(false);
        tfInformation.setFocusTraversable(false);
    }

    @FXML
    public void addApplication() {
        try {

            checkIfApplicationNameIsIllegal(tfOfficialName.getText());
            ManagementSystem.getInstance().addApplication(tfOfficialName.getText(), tfInformation.getText());
        } catch (SQLException e) {
            AlertUtilities.sqlErrorAlert();
        } catch (Exception e) {
            AlertUtilities.errorAlert(e.getMessage());
            return;
        }
        addToAppsList();

    }
    private void checkIfApplicationNameIsIllegal(String officialName) throws IllegalApplicationNameException {
        if (officialName == null || officialName.isEmpty())
            throw new IllegalApplicationNameException();
    }

    private void addToAppsList() {
        Label newAppLabel = new Label(tfOfficialName.getText());
        newAppLabel.setFont(FontUtilities.APPS_LIST_FONT);
        appsList.getItems().add(newAppLabel);
        ((Stage) tfOfficialName.getScene().getWindow()).close();
    }


    public void setAppList(ListView list) {
        appsList = list;
    }
}
