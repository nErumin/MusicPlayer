package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import thread.AlarmSystem;

public class AlarmSettingGuiController {
    @FXML
    private Button alarmSetBtn;
    @FXML
    private TextArea alarmTextArea;

    public void initialize(){
    }

    @FXML
    private void setBtnOnClicked(){
        makeNewAlarm();
    }
    
    private void makeNewAlarm(){
        ComboBox hourComboBox, minuteComboBox, ampmComboBox;

        Scene scene = alarmSetBtn.getScene();

        hourComboBox = (ComboBox)scene.lookup("#hourComboBox");
        minuteComboBox = (ComboBox)scene.lookup("#minuteComboBox");
        ampmComboBox = (ComboBox)scene.lookup("#ampmComboBox");

        String ampm = ampmComboBox.getSelectionModel().getSelectedItem().toString();
        int hour = (int)hourComboBox.getSelectionModel().getSelectedItem();
        int minute = (int)minuteComboBox.getSelectionModel().getSelectedItem();
        String text = alarmTextArea.getText();

        ((Stage)alarmSetBtn.getScene().getWindow()).close();
        AlarmSystem alarmSystem = new AlarmSystem(ampm, hour, minute, text);
        alarmSystem.execute();
    }
}
