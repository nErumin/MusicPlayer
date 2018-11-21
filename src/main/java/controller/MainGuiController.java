package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import thread.AlarmSystem;
import view.AlarmSettingGui;

public class MainGuiController {
    @FXML
    private Button favoriteMusicListBtn, fullMusicListBtn, recentPlayedMusicListBtn;
    private Boolean isAlarmWindow;

    public void initialize() {
        isAlarmWindow = false;
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

            String ampm = alarmSettingGui.getAmpmString();
            String hour = alarmSettingGui.getHourString();
            String minute = alarmSettingGui.getMinuteString();
            String text = alarmSettingGui.getTextAreaString();
            System.out.println(ampm + "   " + hour + "   " + minute + "   " + text);

            new AlarmSystem(ampm, hour, minute, text).run();
        }
    }
}
