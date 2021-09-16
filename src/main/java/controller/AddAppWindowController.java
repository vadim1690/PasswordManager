package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.ManagementSystem;
import utilities.AlertUtilities;
import utilities.FontUtilities;

import java.net.URL;
import java.util.ResourceBundle;

public class AddAppWindowController implements Initializable {


    private ListView<Label> appsList;

    @FXML
    private TextField tfOfficialName;

    @FXML
    private TextField tfInformation;

    @FXML
    private Button addButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfOfficialName.setFocusTraversable(false);
        tfInformation.setFocusTraversable(false);
    }

    @FXML
    public void addApplication() {
        try {
            ManagementSystem.getInstance().addApplication(tfOfficialName.getText(), tfInformation.getText());
        } catch (Exception e) {
            AlertUtilities.errorAlert(e.getMessage());
            return;
        }
        addToAppsList();

    }

    private void addToAppsList() {
        Label newAppLabel = new Label(tfOfficialName.getText());
        newAppLabel.setFont(FontUtilities.APPS_LIST_FONT);
        appsList.getItems().add(newAppLabel);
        ((Stage) addButton.getScene().getWindow()).close();
    }


    public void setAppList(ListView list) {
        appsList = list;
    }
}
