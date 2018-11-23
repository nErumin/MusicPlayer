package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ShutdownSettingGui extends Stage {
    private ComboBox timeComboBox;
    public ShutdownSettingGui(){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("shutdown_setting_gui.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        this.setMinWidth(260);
        this.setMinHeight(130);
        this.setMaxWidth(260);
        this.setMaxHeight(130);
        this.setTitle("Shutdown Window");

        timeComboBox = makeSetTimeComboBox("shutdownTimeComboBox", 80.0, 56.0);

        AnchorPane shutdownPane = (AnchorPane) scene.lookup("#shutdownPane");
        shutdownPane.getChildren().add(timeComboBox);

        this.setScene(scene);
    }
    public void makeJustOneWindow(Stage owner){
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(owner);
    }
    private ComboBox<String> makeSetTimeComboBox(String id, double layoutX, double layoutY){
        ComboBox<String> comboBox = new ComboBox<>();

        comboBox.getItems().add("1");
        comboBox.getItems().add("5");
        comboBox.getItems().add("10");
        comboBox.getItems().add("15");
        comboBox.getItems().add("30");
        comboBox.getItems().add("45");
        comboBox.getItems().add("60");
        comboBox.getItems().add("90");
        comboBox.getItems().add("120");

        comboBox.getSelectionModel().select(0);
        comboBox.setLayoutX(layoutX);
        comboBox.setLayoutY(layoutY);
        comboBox.setId(id);
        return comboBox;
    }
}
