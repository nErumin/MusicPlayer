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
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Path;
import model.music.MusicData;
import model.music.MusicPlayer;
import model.music.MusicProxy;
import model.music.iterator.MusicIterator;
import model.music.state.FavoriteReferenceState;
import model.music.state.FullReferenceState;
import model.music.state.ListReferenceState;
import model.music.state.RecentPlayedReferenceState;
import view.AlarmSettingGui;
import view.ShutdownSettingGui;

import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public class MainGuiController {
    private ListReferenceState fullReferenceState;
    private ListReferenceState recentPlayedReferenceState;
    private ListReferenceState favoriteReferenceState;
    private ListReferenceState currentReferenceState;


    @FXML
    private Button favoriteMusicListBtn, stopBtn, playBtn, seekPreviousBtn, seekNextBtn, loopBtn, favoriteBtn;
    @FXML
    private ImageView playImageView, favoriteImageView;
    @FXML
    private Slider musicProgressBar, musicVolumeVar;
    @FXML
    private ListView<String> musicListView;

    private ObservableMap<Path, MusicData> musicFiles;
    private MusicPlayer musicPlayer;
    private boolean isPause = false;
    private boolean isFavorite = false;

    public void initialize() throws LineUnavailableException {
        musicPlayer = new MusicPlayer();

        musicFiles = FXCollections.observableHashMap();
        musicFiles.addListener(this::handleFileListChanged);

        fullReferenceState = new FullReferenceState(musicFiles);
        recentPlayedReferenceState = new RecentPlayedReferenceState(musicFiles);
        favoriteReferenceState = new FavoriteReferenceState(musicFiles);

        setReferenceState(fullReferenceState);

        musicPlayer.registerStartListener(this::handleMusicPlayStarting);
        musicPlayer.registerStartListener(this::handlePlayBtn);
        musicPlayer.registerStartListener(this::handleFavoriteBtn);
    }

    private void handlePlayBtn(MusicData musicData){
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("image/pause.png"));
        playImageView.setImage(image);
    }
    private void handleFavoriteBtn(MusicData musicData){
        if(musicData.isFavorite()){
            Image image = new Image(getClass().getClassLoader().getResourceAsStream("image/favorite-star.png"));
            favoriteImageView.setImage(image);

        }
        else{
            Image image = new Image(getClass().getClassLoader().getResourceAsStream("image/unfavorite-star.png"));
            favoriteImageView.setImage(image);

        }
    }

    private void handleMusicPlayStarting(MusicData musicData) {
        if (currentReferenceState.equals(recentPlayedReferenceState) == false) {
            Date currentDate = Date.from(ZonedDateTime.now().toInstant());
            musicData.setRecentPlayedDate(currentDate);
        }
    }

    private void setReferenceState(ListReferenceState newState) {
        currentReferenceState = newState;
    }

    private void handleFileListChanged(MapChangeListener.Change<? extends Path, ? extends MusicData> changeArgs) {
        refreshListViewItems();
    }

    private void refreshListViewItems() {
        musicPlayer.stopPlay();

        ObservableList<String> listViewItems = musicListView.getItems();

        listViewItems.clear();
        listViewItems.addAll(currentReferenceState.getSortedFileNames());
    }

    @FXML
    private void fullMusicListBtnOnClicked() {
        setReferenceState(fullReferenceState);

        refreshListViewItems();
    }

    @FXML
    private void favoriteMusicListBtnOnClicked() {
        setReferenceState(favoriteReferenceState);

        refreshListViewItems();
    }

    @FXML
    private void recentPlayedMusicListBtnOnClicked() {
        setReferenceState(recentPlayedReferenceState);

        refreshListViewItems();
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

    @FXML
    private void clickStopBtn(){
        musicPlayer.stopPlay();
    }
    @FXML
    private void clickPlayBtn(){
        if(musicPlayer.isPaused()) {
            musicPlayer.resumePlay();
            Image image = new Image(getClass().getClassLoader().getResourceAsStream("image/play.jpg"));
            playImageView.setImage(image);
        }
        else{
            musicPlayer.pausePlay();
            Image image = new Image(getClass().getClassLoader().getResourceAsStream("image/pause.png"));
            playImageView.setImage(image);
        }
    }
    @FXML
    private void clickSeekNextBtn(){
        musicPlayer.skipSeconds(10);
    }
    @FXML
    private void clickSeekPreviousBtn(){
        musicPlayer.skipSeconds(-10);
    }
    @FXML
    private void clickFavoriteBtn(){
        if(musicPlayer.getCurrentPlayedMusic().isFavorite()) {
            Image image = new Image(getClass().getClassLoader().getResourceAsStream("image/unfavorite-star.png"));
            favoriteImageView.setImage(image);
            musicPlayer.getCurrentPlayedMusic().setFavorite(false);
        }
        else{
            Image image = new Image(getClass().getClassLoader().getResourceAsStream("image/favorite-star.png"));
            favoriteImageView.setImage(image);
            musicPlayer.getCurrentPlayedMusic().setFavorite(true);
        }
    }
    @FXML
    private void clickLoopBtn(){
        System.out.println("click loop btn");
    }
}
