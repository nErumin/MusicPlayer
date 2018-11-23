package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ShutdownSettingGuiController {
    @FXML
    Button shutdownSetBtn;

    public void initialize(){
    }

    public void okBtnClicked(){
        ((Stage)shutdownSetBtn.getScene().getWindow()).close();
    }
}
