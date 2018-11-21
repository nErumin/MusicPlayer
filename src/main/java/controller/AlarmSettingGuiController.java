package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import thread.AlarmSystem;

public class AlarmSettingGuiController {
    @FXML
    private Button alarmSetBtn;
    @FXML
    private TextArea alarmTextArea;

    private ComboBox hourComboBox, minuteComboBox, ampmComboBox;

    public void initialize(){
    }

    public void setBtnOnClicked(Event e){
        Scene scene = alarmSetBtn.getScene();

        hourComboBox = (ComboBox)scene.lookup("#hourComboBox");
        minuteComboBox = (ComboBox)scene.lookup("#minuteComboBox");
        ampmComboBox = (ComboBox)scene.lookup("#ampmComboBox");

        String ampm = ampmComboBox.getSelectionModel().getSelectedItem().toString();
        String hour = hourComboBox.getSelectionModel().getSelectedItem().toString();
        String minute = minuteComboBox.getSelectionModel().getSelectedItem().toString();
        String text = alarmTextArea.getText();

        ((Node)e.getSource()).getScene().getWindow().hide();
        new AlarmSystem(ampm, hour, minute, text).run();

    }
}
