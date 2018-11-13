package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainGuiController {
    @FXML
    private Button favoriteMusicListBtn, fullMusicListBtn, recentPlayedMusicListBtn;
    public void initialize(){

    }
    public void fullMusicListBtnOnClicked(){
        fullMusicListBtn.setText("full Clicked");
    }
    public void favoriteMusicListBtnOnClicked(){
        favoriteMusicListBtn.setText("favorite Clicked");
    }
    public void recentPlayedMusicListBtnOnClicked(){
        recentPlayedMusicListBtn.setText("recent Clicked");
    }
}
