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

    private ComboBox hourComboBox, minuteComboBox, ampmComboBox;

    public void initialize(){
    }

    public void setBtnOnClicked(){
        Scene scene = alarmSetBtn.getScene();

        hourComboBox = (ComboBox)scene.lookup("#hourComboBox");
        minuteComboBox = (ComboBox)scene.lookup("#minuteComboBox");
        ampmComboBox = (ComboBox)scene.lookup("#ampmComboBox");

        String ampm = ampmComboBox.getSelectionModel().getSelectedItem().toString();
        String hour = hourComboBox.getSelectionModel().getSelectedItem().toString();
        String minute = minuteComboBox.getSelectionModel().getSelectedItem().toString();
        String text = alarmTextArea.getText();

        ((Stage)alarmSetBtn.getScene().getWindow()).close();
        new AlarmSystem(ampm, hour, minute, text).run();
    }
}
