package controller;

import io.DirectoryReader;
import io.FileExtensionFilteredDirectoryReader;
import io.NonRecursiveDirectoryReader;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import model.Path;
import model.command.Command;
import model.command.SkipCommand;
import model.command.VolumeIncreaseCommand;
import model.music.MusicData;
import model.music.MusicPlayer;
import model.music.MusicProxy;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javafx.stage.Stage;
import model.music.PlayerMemento;
import model.music.iterator.BackwardDirection;
import model.music.iterator.ForwardDirection;
import model.music.iterator.MusicIterator;
import model.music.iterator.NormalMusicIterator;
import model.music.state.FavoriteReferenceState;
import model.music.state.FullReferenceState;
import model.music.state.ListReferenceState;
import model.music.state.RecentPlayedReferenceState;
import utility.SceneUtility;
import view.AlarmSettingGui;
import view.ShutdownSettingGui;

import javax.sound.sampled.*;

public class MainGuiController {
    private ListReferenceState fullReferenceState;
    private ListReferenceState recentPlayedReferenceState;
    private ListReferenceState favoriteReferenceState;
    private ListReferenceState currentReferenceState;

    @FXML
    private Button favoriteMusicListBtn;

    @FXML
    private ListView<String> musicListView;
    private ObservableMap<Path, MusicData> musicFiles;
    private MusicPlayer musicPlayer;
    private List<Command> executedCommands;

    public void initialize() throws LineUnavailableException {
        musicPlayer = new MusicPlayer();

        musicFiles = FXCollections.observableHashMap();
        musicFiles.addListener(this::handleFileListChanged);

        musicPlayer.registerStartListener(this::handleMusicPlayStarting);

        initializeStates();
    }

    private void initializeStates() {
        fullReferenceState = new FullReferenceState(musicFiles);
        recentPlayedReferenceState = new RecentPlayedReferenceState(musicFiles);
        favoriteReferenceState = new FavoriteReferenceState(musicFiles);

        setReferenceState(fullReferenceState);
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

    private void handleMusicPlayStarting(MusicData musicData) {
        executedCommands.clear();

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

    public void fullMusicListBtnOnClicked() {
        setReferenceState(fullReferenceState);

        refreshListViewItems();
    }

    public void favoriteMusicListBtnOnClicked() {
        setReferenceState(favoriteReferenceState);

        refreshListViewItems();
    }

    public void recentPlayedMusicListBtnOnClicked() {
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
