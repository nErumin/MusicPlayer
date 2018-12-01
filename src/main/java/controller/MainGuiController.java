package controller;

import io.DirectoryReader;
import io.FileExtensionFilteredDirectoryReader;
import io.NonRecursiveDirectoryReader;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import model.Path;
import model.music.MusicData;
import model.music.MusicPlayer;
import model.music.MusicProxy;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javafx.stage.Stage;
import model.music.PlayerMemento;
import model.music.iterator.BackwardDirection;
import model.music.iterator.ForwardDirection;
import model.music.iterator.MusicIterator;
import model.music.iterator.NormalMusicIterator;
import model.music.state.FullReferenceState;
import model.music.state.ListReferenceState;
import view.AlarmSettingGui;
import view.ShutdownSettingGui;

import javax.sound.sampled.*;

public class MainGuiController {
    private FullReferenceState fullReferenceState;
    private ListReferenceState currentReferenceState;

    @FXML
    private Button favoriteMusicListBtn, fullMusicListBtn, recentPlayedMusicListBtn;

    @FXML
    private ListView<String> musicListView;
    private ObservableMap<Path, MusicData> musicFiles;
    private MusicPlayer musicPlayer;

    public void initialize() throws LineUnavailableException {
        musicPlayer = new MusicPlayer();

        musicFiles = FXCollections.observableHashMap();
        musicFiles.addListener(this::handleFileListChanged);

        fullReferenceState = new FullReferenceState(musicFiles);
        setReferenceState(fullReferenceState);
    }

    private void setReferenceState(ListReferenceState newState) {
        currentReferenceState = newState;
    }

    private void handleFileListChanged(MapChangeListener.Change<? extends Path, ? extends MusicData> changeArgs) {
        refreshListViewItems();
    }

    private void refreshListViewItems() {
        ObservableList<String> listViewItems = musicListView.getItems();

        listViewItems.clear();
        listViewItems.addAll(currentReferenceState.getSortedFileNames());
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

    @FXML
    private void handleOpenFolder() throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File chosenDirectory = directoryChooser.showDialog(null);

        DirectoryReader directoryReader = new FileExtensionFilteredDirectoryReader(
            new NonRecursiveDirectoryReader(), Arrays.asList("mp3", "wav")
        );

        musicFiles.clear();

        if (chosenDirectory != null) {
            fillMusicFileListView(directoryReader.getFiles(chosenDirectory.getPath()));
        }
    }

    private void fillMusicFileListView(Iterable<String> filePaths) {
        for (String path : filePaths) {
            Path musicFilePath = new Path(path);
            MusicData musicData = new MusicProxy(musicFilePath.getFullPath());

            musicFiles.put(musicFilePath, musicData);
        }
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

    @FXML
    private void handleMusicListItemClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            String selectedFileName = musicListView.getSelectionModel().getSelectedItem();

            processMusicFilesForPlaying(selectedFileName);
        }
    }

    private void processMusicFilesForPlaying(String selectedFileName) {
        Optional<MusicData> selectedMusic
            = currentReferenceState.getSortedEntries()
                                   .stream()
                                   .filter(entry -> entry.getKey().getFileName().equals(selectedFileName))
                                   .map(Map.Entry::getValue)
                                   .findFirst();

        MusicIterator iterator = currentReferenceState.makeIterator(currentReferenceState.getSortedMusics());

        if (selectedMusic.isPresent()) {
            iterator.resetFor(selectedMusic.get());
            startMusicPlayer(iterator);
        }
    }

    private void startMusicPlayer(MusicIterator musicIterator) {
        musicPlayer.stopPlay();

        musicPlayer.setIterationMode(musicIterator);
        musicPlayer.startPlay();
    }
}
