package gui;


import com.google.common.util.concurrent.SettableFuture;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;
import org.loadui.testfx.utils.UserInputDetector;
import view.MainGui;

import java.util.concurrent.TimeUnit;

import static org.junit.Assume.assumeTrue;

;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainGuiTest extends GuiTest {

    private static final SettableFuture<Stage> stageFuture = SettableFuture.create();

    /**
     * The type Test main window.
     */
    protected static class TestMainWindow extends MainGui {
        /**
         * Instantiates a new Test main window.
         */
        public TestMainWindow() {
            super();
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            super.start(primaryStage);
            stageFuture.set(primaryStage);
        }
    }
    @Before
    @Override
    public void setupStage() throws Throwable {
        assumeTrue(!UserInputDetector.instance.hasDetectedUserInput());

        FXTestUtils.launchApp(MainGuiTest.TestMainWindow.class); // You can add start parameters here
        try {
            stage = targetWindow(stageFuture.get(25, TimeUnit.SECONDS));
            FXTestUtils.bringToFront(stage);
        } catch (Exception e) {
            throw new RuntimeException("Unable to show stage", e);
        }
    }

    @Override
    protected Parent getRootNode() {
        return stage.getScene().getRoot();
    }
    /*
    @Test
    public void stage0_testInitialSetting(){
        assertTrue(((Slider)GuiTest.find("#musicVolumeBar")).getValue() == 80);
        assertTrue(((Slider)GuiTest.find("#musicProgressBar")).getValue() == 0);
    }
    @Test
    public void stage1_testGetMusic(){
        click("#fileMenu").click("Open Folder");

        type("sample").push(KeyCode.SHIFT, KeyCode.MINUS).type("musics").type(KeyCode.ENTER).type(KeyCode.ENTER);
        sleep(500);

        ListView musicListView = GuiTest.find("#musicListView");
        assertTrue(musicListView.getItems().get(0).toString().equals("DropSword.wav"));
        assertTrue(musicListView.getItems().get(1).toString().equals("Happy-music.mp3"));
        assertTrue(musicListView.getItems().get(2).toString().equals("Laser.wav"));
        assertTrue(musicListView.getItems().get(3).toString().equals("applause3.wav"));

        click("#fileMenu").click("Open Folder");

        type("sample").push(KeyCode.SHIFT, KeyCode.MINUS).type("musics").type(KeyCode.ENTER);
        type("inner").push(KeyCode.SHIFT, KeyCode.MINUS).type("directory").type(KeyCode.ENTER).type(KeyCode.ENTER);
        sleep(500);

        assertTrue(musicListView.getItems().get(0).toString().equals("Ding-dong.wav"));
        assertTrue(musicListView.getItems().get(1).toString().equals("Loud_Bang.wav"));
        assertTrue(musicListView.getItems().get(2).toString().equals("Roar.wav"));
        assertTrue(musicListView.getItems().get(3).toString().equals("Wrong-alert-beep-sound.mp3"));
    }
    @Test
    public void stage2_testPlayingMusic(){
        click("#fileMenu").click("Open Folder");

        type("sample").push(KeyCode.SHIFT, KeyCode.MINUS).type("musics").type(KeyCode.ENTER).type(KeyCode.ENTER);
        sleep(500);

        ListView musicListView = GuiTest.find("#musicListView");
        String testName = musicListView.getItems().get(1).toString();
        doubleClick(testName);

        this.sleep(500);
        assertTrue(((Label)GuiTest.find("#nameLabel")).getText().equals("Happy-music"));
    }
    @Test
    public void stage3_testFavoriteMusic(){
        click("#fileMenu").click("Open Folder");

        type("sample").push(KeyCode.SHIFT, KeyCode.MINUS).type("musics").type(KeyCode.ENTER).type(KeyCode.ENTER);
        sleep(500);

        ListView musicListView = GuiTest.find("#musicListView");
        String testName = musicListView.getItems().get(1).toString();
        doubleClick(testName);

        this.sleep(4000);

        click("#favoriteBtn");
        click("#favoriteMusicListBtn");
        assertTrue(musicListView.getItems().get(0).toString().equals(testName));

        click("#fullMusicListBtn");
    }
    @Test
    public void stage4_testRecentPlayedMusicTab(){
        click("#fileMenu").click("Open Folder");

        type("sample").push(KeyCode.SHIFT, KeyCode.MINUS).type("musics").type(KeyCode.ENTER).type(KeyCode.ENTER);
        sleep(500);

        ListView musicListView = GuiTest.find("#musicListView");
        String testName = musicListView.getItems().get(1).toString();
        doubleClick(testName);

        this.sleep(4000);

        click("#recentPlayedMusicListBtn");
        assertTrue(musicListView.getItems().get(0).toString().equals(testName));
    }
    */
}
