package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import view.AlarmGui;

public class MainGuiController {
    @FXML
    private Button favoriteMusicListBtn, fullMusicListBtn, recentPlayedMusicListBtn;

    public void initialize(){

    }
    public void fullMusicListBtnOnClicked(){ fullMusicListBtn.setText("full Clicked"); }
    public void favoriteMusicListBtnOnClicked(){
        favoriteMusicListBtn.setText("favorite Clicked");
    }
    public void recentPlayedMusicListBtnOnClicked(){
        recentPlayedMusicListBtn.setText("recent Clicked");
    }

    public void clickAlarmMenuItem(){
        AlarmGui alarmGui = new AlarmGui();
        alarmGui.showAndWait();
    }
}
