package controller;

import io.DirectoryReader;
import io.FileExtensionFilteredDirectoryReader;
import io.NonRecursiveDirectoryReader;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Path;
import model.command.Command;
import model.command.SkipCommand;
import model.command.VolumeIncreaseCommand;
import model.music.MusicData;
import model.music.MusicPlayer;
import model.music.MusicProxy;
import model.music.iterator.LoopDirection;
import model.music.iterator.MusicIterator;
import model.music.state.FavoriteReferenceState;
import model.music.state.FullReferenceState;
import model.music.state.ListReferenceState;
import model.music.state.RecentPlayedReferenceState;
import thread.LyricPrintSystem;
import utility.SceneUtility;
import view.AlarmSettingGui;
import view.ShutdownSettingGui;

import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

public class MainGuiController {
    private ListReferenceState fullReferenceState;
    private ListReferenceState recentPlayedReferenceState;
    private ListReferenceState favoriteReferenceState;
    private ListReferenceState currentReferenceState;


    @FXML
    private Button favoriteMusicListBtn;
    @FXML
    private ImageView playImageView, favoriteImageView, musicImageView, loopImageView;

    @FXML
    private Slider musicProgressBar, musicVolumeBar;
    @FXML
    private ListView<String> musicListView;
    @FXML
    private Label nameLabel, lyricLabel;

    private ObservableMap<Path, MusicData> musicFiles;
    private MusicPlayer musicPlayer;
    private List<Command> executedCommands;

    private LyricPrintSystem lyricPrintSystem;

    public void initialize() throws LineUnavailableException {
        musicPlayer = new MusicPlayer();

        musicFiles = FXCollections.observableHashMap();
        musicFiles.addListener(this::handleFileListChanged);

        lyricLabel.setWrapText(true);

        registerToMusicPlayer();

        musicVolumeBar.setValue(80); // default value
        musicVolumeBar.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {

                musicPlayer.setVolumeRatio((new_val.floatValue())/100);
            }
        });
        musicProgressBar.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                musicPlayer.playFromLengthRatio((double)new_val/100);
            }
        });

        initializeStates();
    }

    private void initializeStates() {
        fullReferenceState = new FullReferenceState(musicFiles);
        recentPlayedReferenceState = new RecentPlayedReferenceState(musicFiles);
        favoriteReferenceState = new FavoriteReferenceState(musicFiles);

        setReferenceState(fullReferenceState);
    }

    private void registerToMusicPlayer(){
        musicPlayer.registerStartListener(this::handleMusicPlayStarting);
        musicPlayer.registerStartListener(this::handlePlayBtn);
        musicPlayer.registerStartListener(this::handleFavoriteBtn);
        musicPlayer.registerStartListener(this::handleLyricSystem);
        musicPlayer.registerStartListener(this::handleMusicNameLabel);
        musicPlayer.registerStartListener(this::handleMusicImageView);
        musicPlayer.registerStartListener(this::handleLyricLabel);
        musicPlayer.registerStartListener(this::handleMusicProgressBar);
    }

    private void initializeShortcut() {
        Scene scene = favoriteMusicListBtn.getScene();

        KeyCombination volumeIncreaseShortcutKey =
            new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN);

        KeyCombination volumeDecreaseShortcutKey =
            new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);

        KeyCombination skipForwardShortcutKey =
            new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.CONTROL_DOWN);

        KeyCombination skipBackwardShortcutKey =
            new KeyCodeCombination(KeyCode.LEFT, KeyCombination.CONTROL_DOWN);

        KeyCombination undoShortcutKey =
            new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);

        SceneUtility.registerKey(
            scene,
            volumeIncreaseShortcutKey,
            () -> {
                Command command = new VolumeIncreaseCommand(musicPlayer, 0.1f);
                command.execute();

                executedCommands.add(command);
            }
        );

        SceneUtility.registerKey(
            scene,
            volumeDecreaseShortcutKey,
            () -> {
                Command command = new VolumeIncreaseCommand(musicPlayer, -0.1f);
                command.execute();

                executedCommands.add(command);
            }
        );

        SceneUtility.registerKey(
            scene,
            skipForwardShortcutKey,
            () -> {
                Command command = new SkipCommand(musicPlayer, 10);
                command.execute();

                executedCommands.add(command);
            }
        );

        SceneUtility.registerKey(
            scene,
            skipBackwardShortcutKey,
            () -> {
                Command command = new SkipCommand(musicPlayer, -10);
                command.execute();

                executedCommands.add(command);
            }
        );

        SceneUtility.registerKey(
            scene,
            undoShortcutKey,
            () -> {
                if (executedCommands.size() > 0) {
                    Command command = executedCommands.get(executedCommands.size() - 1);
                    command.undo();

                    executedCommands.remove(command);
                }
            }
        );
    }

    private void handleLyricLabel(MusicData musicData){
        if(musicData.isLyric()) {
            lyricLabel.setStyle("-fx-background-color:white;");
        }
        else {
            lyricLabel.setStyle("-fx-background-color:transparent;");
        }
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

    private void handleLyricSystem(MusicData musicData) {
        if (musicPlayer.getCurrentPlayedMusic().getLyric() != null) {
            if (lyricPrintSystem != null) {
                lyricPrintSystem.cancel(true);
            }
            lyricPrintSystem = new LyricPrintSystem();
            lyricPrintSystem.setCurrentMusicPlayer(musicPlayer);
            lyricPrintSystem.setLyricLabel(lyricLabel);
            lyricPrintSystem.execute();
        }
    }

    private void handleMusicPlayStarting(MusicData musicData) {
        executedCommands.clear();

        if (currentReferenceState.equals(recentPlayedReferenceState) == false) {
            Date currentDate = Date.from(ZonedDateTime.now().toInstant());
            musicData.setRecentPlayedDate(currentDate);
        }
    }

    private void handleMusicNameLabel(MusicData musicData){
        Platform.runLater(()->{
            nameLabel.setText(musicData.getTitle());
        });
    }

    private void handleMusicProgressBar(MusicData musicData){
        musicProgressBar.setValue(0);
    }

    private void handleMusicImageView(MusicData musicData){
        musicImageView.setImage(musicData.getImage());
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

        if (executedCommands == null) {
            executedCommands = new ArrayList<>();
            initializeShortcut();
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
        if(musicPlayer.getCurrentPlayedMusic() == null){
            System.out.println("Plz set music");
        }
        else {
            if (musicPlayer.isPaused()) {
                musicPlayer.resumePlay();
                Image image = new Image(getClass().getClassLoader().getResourceAsStream("image/pause.png"));
                playImageView.setImage(image);
            } else {
                musicPlayer.pausePlay();
                Image image = new Image(getClass().getClassLoader().getResourceAsStream("image/play.jpg"));
                playImageView.setImage(image);
            }
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
        if(musicPlayer.getCurrentPlayedMusic() == null){
            System.out.println("Plz set music");
        }
        else {
            if (musicPlayer.getCurrentPlayedMusic().isFavorite()) {
                Image image = new Image(getClass().getClassLoader().getResourceAsStream("image/unfavorite-star.png"));
                favoriteImageView.setImage(image);
                musicPlayer.getCurrentPlayedMusic().setFavorite(false);
            } else {
                Image image = new Image(getClass().getClassLoader().getResourceAsStream("image/favorite-star.png"));
                favoriteImageView.setImage(image);
                musicPlayer.getCurrentPlayedMusic().setFavorite(true);
            }
        }
    }
    @FXML
    private void clickLoopBtn(){
        MusicIterator iterator = currentReferenceState.makeIterator(currentReferenceState.getSortedMusics());
        iterator.resetFor(musicPlayer.getCurrentPlayedMusic());

        Image loopImage = musicPlayer.isLooping() ?
            new Image(getClass().getClassLoader().getResourceAsStream("image/loop.png")) :
            new Image(getClass().getClassLoader().getResourceAsStream("image/unloop.png"));

        if (musicPlayer.isLooping() == false) {
            iterator.setIteratorDirection(new LoopDirection());
        }

        loopImageView.setImage(loopImage);

        musicPlayer.setLooping(!musicPlayer.isLooping());
        musicPlayer.setIterationMode(iterator);
    }
}
