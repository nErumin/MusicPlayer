package controller;

import io.DirectoryReader;
import io.FileExtensionFilteredDirectoryReader;
import io.NonRecursiveDirectoryReader;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Path;
import model.music.MusicData;
import model.music.MusicProxy;
import view.AlarmSettingGui;
import view.ShutdownSettingGui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MainGuiController {
    @FXML
    private Button favoriteMusicListBtn, fullMusicListBtn, recentPlayedMusicListBtn;

    @FXML
    private ImageView musicImage;

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
        listViewItems.addAll(musicFiles.keySet()
                                       .stream()
                                       .map(Path::getFileName)
                                       .sorted()
                                       .collect(Collectors.toList()));
    }

    @FXML
    private void fullMusicListBtnOnClicked() {
        fullMusicListBtn.setText("full Clicked");
    }

    @FXML
    private void favoriteMusicListBtnOnClicked() {
        favoriteMusicListBtn.setText("favorite Clicked");
    }

    @FXML
    private void recentPlayedMusicListBtnOnClicked() {
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
        fillMusicFileListView(directoryReader.getFiles(chosenDirectory.getPath()));
    }

    private void fillMusicFileListView(Iterable<String> filePaths) {
        for (String path : filePaths) {
            Path musicFilePath = new Path(path);
            MusicData musicData = new MusicProxy(musicFilePath.getFullPath());

            musicFiles.put(musicFilePath, musicData);
        }
    }

    @FXML
    private void clickAlarmMenuItem() {
        Stage stage = (Stage)favoriteMusicListBtn.getScene().getWindow();
        AlarmSettingGui alarmSettingGui = new AlarmSettingGui();
        alarmSettingGui.makeJustOneWindow(stage);
        alarmSettingGui.showAndWait();
    }

    @FXML
    private void clickShutdownMenuItem(){
        Stage stage = (Stage)favoriteMusicListBtn.getScene().getWindow();
        ShutdownSettingGui shutdownSettingGui = new ShutdownSettingGui();
        shutdownSettingGui.makeJustOneWindow(stage);
        shutdownSettingGui.showAndWait();
    }

    @FXML
    private void handleMusicListItemClicked() {
        String selectedFileName = musicListView.getSelectionModel().getSelectedItem();

        playMusic(selectedFileName);


    }

    private void playMusic(String selectedFileName) {
        musicFiles.forEach(((path, musicData) -> {
            try {
                if (path.getFileName().equals(selectedFileName)) {
                    startClip(musicData.getAudioStream());
                    musicImage.setImage(musicData.getImage());
                }
            } catch (IOException | LineUnavailableException exception) {
                exception.printStackTrace();
            }
        }));
    }

    private void startClip(AudioInputStream audioStream) throws LineUnavailableException, IOException {
        if (audioStream != null) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        }
    }
}
