package view;

import controller.Controller;
import javafx.stage.Stage;
import model.ModelInterface;

public class GUI implements UserInterface {

    private ModelInterface controller;

    public GUI(Stage stage) {


    }

    @Override
    public void registerController(ModelInterface controller) {
        this.controller = controller;
    }
}
