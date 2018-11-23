package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
        if (!isAlarmWindow) {
            AlarmSettingGui alarmSettingGui = new AlarmSettingGui();
            isAlarmWindow = true;
            alarmSettingGui.showAndWait();
            isAlarmWindow = false;
        }
    }
    public void clickShutdownMenuItem(){
        if (!isShutdownWindow) {
            ShutdownSettingGui shutdownSettingGui = new ShutdownSettingGui();
            isShutdownWindow = true;
            shutdownSettingGui.showAndWait();
            isShutdownWindow = false;
        }
    }
}
