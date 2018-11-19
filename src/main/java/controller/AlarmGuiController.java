package controller;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;

public class AlarmGuiController {
    @FXML
    MenuButton ampmMenuBtn, minuteMenuBtn;
    public void initialize(){

    }
    public void alarmMenuItemClicked(){
        ampmMenuBtn.setText("Clicked");
    }
}
