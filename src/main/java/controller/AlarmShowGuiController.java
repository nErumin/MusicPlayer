package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AlarmShowGuiController {
    @FXML
    Button alarmShowOkBtn;

    public void initialize(){
    }

    @FXML
    private void okBtnClicked(){
        ((Stage)alarmShowOkBtn.getScene().getWindow()).close();
    }
}
