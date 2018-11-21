package controller;

import io.DirectoryReader;
import io.FileExtensionFilteredDirectoryReader;
import io.NonRecursiveDirectoryReader;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import model.Path;
import model.music.MusicData;
import model.music.MusicProxy;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javafx.scene.control.MenuItem;
import view.AlarmGui;

public class MainGuiController {
    @FXML
    private Button favoriteMusicListBtn, fullMusicListBtn, recentPlayedMusicListBtn;
    @FXML
    private ListView<String> musicListView;
    private ObservableMap<Path, MusicData> musicFiles;

    public void initialize() {
        musicFiles = FXCollections.observableHashMap();

        musicFiles.addListener(this::handleFileListChanged);
    }

    private void handleFileListChanged(MapChangeListener.Change<? extends Path, ? extends MusicData> changeArgs) {
        ObservableList<String> listViewItems = musicListView.getItems();

        listViewItems.clear();
        musicFiles.forEach(((path, musicData) -> listViewItems.add(path.getFileName())));
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

    @FXML
    private void handleOpenFolder() throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File chosenDirectory = directoryChooser.showDialog(null);

        DirectoryReader directoryReader = new FileExtensionFilteredDirectoryReader(
            new NonRecursiveDirectoryReader(), Arrays.asList("mp3", "wav")
        );

        musicFiles.clear();

        for (String path : directoryReader.getFiles(chosenDirectory.getPath())) {
            Path musicFilePath = new Path(path);
            MusicData musicData = new MusicProxy(musicFilePath.getFullPath());

            musicFiles.put(musicFilePath, musicData);
        }
    }

    public void clickAlarmMenuItem() {
        AlarmGui alarmGui = new AlarmGui();
        alarmGui.showAndWait();
    }
}
