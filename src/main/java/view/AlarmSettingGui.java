package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AlarmSettingGui extends Stage {
    private ComboBox<Integer> hourCombobox;
    private ComboBox<Integer> minuteCombobox;
    private ComboBox<String> ampmCombobox;

    public AlarmSettingGui(){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("alarm_setting_gui.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        this.setMinWidth(350);
        this.setMinHeight(230);
        this.setMaxWidth(350);
        this.setMaxHeight(230);
        this.setTitle("Alarm Window");

        ampmCombobox = makeAmPmComboBox(15.0,39.0);
        hourCombobox = makeTimeComboBox(12,6,145.0,39.0);
        minuteCombobox = makeTimeComboBox(59,6,261.0,39.0);

        AnchorPane alarmPane = (AnchorPane) scene.lookup("#alarmPane");
        alarmPane.getChildren().add(ampmCombobox);
        alarmPane.getChildren().add(hourCombobox);
        alarmPane.getChildren().add(minuteCombobox);
        this.setScene(scene);
    }
    private ComboBox<String> makeAmPmComboBox(double layoutX, double layoutY){
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().add("A.M.");
        comboBox.getItems().add("P.M.");
        comboBox.getSelectionModel().select(0);
        comboBox.setLayoutX(layoutX);
        comboBox.setLayoutY(layoutY);
        return comboBox;
    }
    private ComboBox<Integer> makeTimeComboBox(int size, int rowCount,double layoutX, double layoutY){
        ComboBox<Integer> comboBox = new ComboBox<>();
        for (int i = 1; i <= size; i++) {
            comboBox.getItems().add(i);
        }
        comboBox.getSelectionModel().select(0);
        comboBox.setVisibleRowCount(rowCount);
        comboBox.setPrefSize(10.0,5.0);
        comboBox.setLayoutX(layoutX);
        comboBox.setLayoutY(layoutY);

        return comboBox;
    }

    public String getAmpmString(){
        return ampmCombobox.getSelectionModel().getSelectedItem().toString();
    }
    public String getHourString(){
        return hourCombobox.getSelectionModel().getSelectedItem().toString();
    }
    public String getMinuteString(){
        return minuteCombobox.getSelectionModel().getSelectedItem().toString();
    }
    public String getTextAreaString(){
        return ((TextArea)this.getScene().lookup("#alarmTextArea")).getText();
    }
}
