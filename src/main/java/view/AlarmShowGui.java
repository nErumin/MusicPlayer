package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class AlarmShowGui extends Stage {
    public AlarmShowGui(String Ampm, int hour, int minute, String alarmText){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("alarm_show_gui.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (
            IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        this.setMinWidth(360);
        this.setMinHeight(230);
        this.setMaxWidth(360);
        this.setMaxHeight(230);
        ((Label)scene.lookup("#alarmShowAmpmLabel")).setText(Ampm);
        ((Label)scene.lookup("#alarmShowTimeLabel")).setText(padZero(hour) + " : " + padZero(minute));

        TextArea textArea = (TextArea)scene.lookup("#alarmShowTextArea");
        textArea.setText(alarmText);
        textArea.setEditable(false);
        textArea.setStyle("-fx-text-fill:black;");

        this.setTitle("Alarm Ringing");
        this.setScene(scene);
    }

    private String padZero(int time){
        String paddedTime;
        if(time<10){
            paddedTime = "0"+ Integer.toString(time);
        }
        else{
            paddedTime = Integer.toString(time);
        }
        return paddedTime;
    }
}
