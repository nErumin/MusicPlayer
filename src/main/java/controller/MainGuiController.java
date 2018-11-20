package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import view.AlarmGui;

public class MainGuiController {
    @FXML
    private Button favoriteMusicListBtn, fullMusicListBtn, recentPlayedMusicListBtn;
    private Boolean isAlarmWindow;
    public void initialize(){
        isAlarmWindow = false;
    }
    public void fullMusicListBtnOnClicked(){ fullMusicListBtn.setText("full Clicked"); }
    public void favoriteMusicListBtnOnClicked(){
        favoriteMusicListBtn.setText("favorite Clicked");
    }
    public void recentPlayedMusicListBtnOnClicked(){
        recentPlayedMusicListBtn.setText("recent Clicked");
    }

    public void clickAlarmMenuItem(){
        if(!isAlarmWindow) {
            AlarmGui alarmGui = new AlarmGui();
            isAlarmWindow = true;
            alarmGui.showAndWait();
            isAlarmWindow = false;
        }
    }
}
