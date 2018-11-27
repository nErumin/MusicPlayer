package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.AlarmSettingGui;
import view.ShutdownSettingGui;

public class MainGuiController {
    @FXML
    private Button favoriteMusicListBtn, fullMusicListBtn, recentPlayedMusicListBtn;
    private Boolean isAlarmWindow, isShutdownWindow;

    public void initialize() {
        isAlarmWindow = false;
        isShutdownWindow = false;
    }

    public void fullMusicListBtnOnClicked() {
        fullMusicListBtn.setText("full Clicked");
    }

    public void favoriteMusicListBtnOnClicked() {
        favoriteMusicListBtn.setText("favorite Clicked");
    }

    public void recentPlayedMusicListBtnOnClicked() {
        recentPlayedMusicListBtn.setText("recent Clicked");
    }

    public void clickAlarmMenuItem() {
        Stage stage = (Stage)favoriteMusicListBtn.getScene().getWindow();
        AlarmSettingGui alarmSettingGui = new AlarmSettingGui();
        alarmSettingGui.makeJustOneWindow(stage);
        alarmSettingGui.showAndWait();
    }

    public void clickShutdownMenuItem(){
        Stage stage = (Stage)favoriteMusicListBtn.getScene().getWindow();
        ShutdownSettingGui shutdownSettingGui = new ShutdownSettingGui();
        shutdownSettingGui.makeJustOneWindow(stage);
        shutdownSettingGui.showAndWait();
    }
}
