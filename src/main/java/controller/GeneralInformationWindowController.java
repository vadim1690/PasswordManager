package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import model.ManagementSystem;
import utilities.FontUtilities;
import utilities.PasswordRegex;

import java.net.URL;
import java.util.ResourceBundle;

public class GeneralInformationWindowController implements Initializable {


    @FXML
    private Text text;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text.setFont(FontUtilities.TEXT_GENERAL_INFORMATION_FONT);
        text.setText(ManagementSystem.getInstance().getGeneralInformation());
    }

    @FXML
    private void generalButtonClicked(){
        text.setText(ManagementSystem.getInstance().getGeneralInformation());
    }

    @FXML
    private void passwordStrengthButtonClicked(){
        text.setText(PasswordRegex.PASSWORD_CATEGORIES_INFORMATION);
    }
}
