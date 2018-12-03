package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import thread.ShutdownSystem;

public class ShutdownSettingGuiController {
    @FXML
    Button shutdownSetBtn;

    public void initialize(){
    }

    @FXML
    private void okBtnClicked(){
        updateShutdown();
        ((Stage)shutdownSetBtn.getScene().getWindow()).close();
    }

    private void updateShutdown(){
        ComboBox timeComboBox;

        Scene scene = shutdownSetBtn.getScene();
        timeComboBox = (ComboBox)scene.lookup("#shutdownTimeComboBox");

        String selectTime = (String)timeComboBox.getSelectionModel().getSelectedItem();
        selectTime = selectTime.trim();

        ShutdownSystem shutdownInstance = ShutdownSystem.getInstance();
        shutdownInstance.setShutdown(selectTime);
        shutdownInstance.execute();
    }
}
