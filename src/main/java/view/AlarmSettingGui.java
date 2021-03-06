package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;

public class AlarmSettingGui extends Stage implements JustOneWindow {
    private ComboBox<Integer> hourComboBox;
    private ComboBox<Integer> minuteComboBox;
    private ComboBox<String> ampmComboBox;

    public AlarmSettingGui(){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("alarm_setting_gui.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        this.setMinWidth(380);
        this.setMinHeight(230);
        this.setMaxWidth(380);
        this.setMaxHeight(230);
        this.setTitle("Alarm Window");

        ampmComboBox = makeAmPmComboBox(15.0,39.0);
        hourComboBox = makeTimeComboBox("hourComboBox",12,6,145.0,39.0);
        minuteComboBox = makeTimeComboBox("minuteComboBox",59,6,281.0,39.0);

        Calendar currentTime = Calendar.getInstance();

        int ampm = currentTime.get(Calendar.AM_PM);
        int hour = currentTime.get(Calendar.HOUR);
        int minute = currentTime.get(Calendar.MINUTE);

        if(hour == 0) hour = 12;

        ampmComboBox.getSelectionModel().select(ampm);
        hourComboBox.getSelectionModel().select(hour-1);
        minuteComboBox.getSelectionModel().select(minute);

        AnchorPane alarmPane = (AnchorPane) scene.lookup("#alarmPane");
        alarmPane.getChildren().add(ampmComboBox);
        alarmPane.getChildren().add(hourComboBox);
        alarmPane.getChildren().add(minuteComboBox);

        this.setScene(scene);
    }

    @Override
    public void makeJustOneWindow(Stage owner){
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(owner);
    }

    private ComboBox<String> makeAmPmComboBox(double layoutX, double layoutY){
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().add("A.M.");
        comboBox.getItems().add("P.M.");
        comboBox.getSelectionModel().select(0);
        comboBox.setLayoutX(layoutX);
        comboBox.setLayoutY(layoutY);
        comboBox.setId("ampmComboBox");
        return comboBox;
    }
    private ComboBox<Integer> makeTimeComboBox(String id, int size, int rowCount,double layoutX, double layoutY){
        ComboBox<Integer> comboBox = new ComboBox<>();
        for (int i = 1; i <= size; i++) {
            comboBox.getItems().add(i);
        }
        comboBox.getSelectionModel().select(0);
        comboBox.setVisibleRowCount(rowCount);
        comboBox.setLayoutX(layoutX);
        comboBox.setLayoutY(layoutY);
        comboBox.setId(id);
        return comboBox;
    }
}
