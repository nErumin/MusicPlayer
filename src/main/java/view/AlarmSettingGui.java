package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AlarmSettingGui extends Stage {
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

        AnchorPane alarmPane = (AnchorPane) scene.lookup("#alarmPane");
        alarmPane.getChildren().add(makeAmPmComboBox(15.0,39.0));
        alarmPane.getChildren().add(makeTimeComboBox(12,6,145.0,39.0));
        alarmPane.getChildren().add(makeTimeComboBox(59,6,261.0,39.0));
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

}
