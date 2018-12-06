package gui;


import com.google.common.util.concurrent.SettableFuture;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;
import org.loadui.testfx.utils.UserInputDetector;
import view.MainGui;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;
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

    @Test
    public void stage0_testInitialSetting(){
        assertTrue(((Slider)GuiTest.find("#musicVolumeBar")).getValue() == 80);
        assertTrue(((Slider)GuiTest.find("#musicProgressBar")).getValue() == 0);
    }
    @Test
    public void stage1_testGetMusic(){
        click("#fileMenu").click("Open Folder");

        type("D");
        sleep(500);
        type(KeyCode.DOWN).type(KeyCode.ENTER);
        type("EtcDocument").type(KeyCode.ENTER);
        type("Music").type(KeyCode.ENTER).type(KeyCode.ENTER);
        sleep(500);

        ListView musicListView = GuiTest.find("#musicListView");
        assertTrue(musicListView.getItems().get(0).toString().equals("Havana by Camila Cabello.mp3"));
        assertTrue(musicListView.getItems().get(1).toString().equals("언니네 이발관-10-산들산들.mp3"));
        assertTrue(musicListView.getItems().get(2).toString().equals("엑소_Tempo.mp3"));
    }
    @Test
    public void stage2_testPlayingMusic(){
        click("#fileMenu").click("Open Folder");

        type("D");
        sleep(500);
        type(KeyCode.DOWN).type(KeyCode.ENTER);
        type("EtcDocument").type(KeyCode.ENTER);
        type("Music").type(KeyCode.ENTER).type(KeyCode.ENTER);
        sleep(500);

        ListView musicListView = GuiTest.find("#musicListView");
        doubleClick(musicListView.getItems().get(0).toString());

        this.sleep(5000);
        assertTrue(((Label)GuiTest.find("#nameLabel")).getText().equals("Havana by Camila Cabello"));
    }
    @Test
    public void stage3_testFavoriteMusic(){
        click("#fileMenu").click("Open Folder");

        type("D");
        sleep(500);
        type(KeyCode.DOWN).type(KeyCode.ENTER);
        type("EtcDocument").type(KeyCode.ENTER);
        type("Music").type(KeyCode.ENTER).type(KeyCode.ENTER);
        sleep(500);

        doubleClick("Havana by Camila Cabello.mp3");

        this.sleep(5000);

        click("#favoriteBtn");
        click("#favoriteMusicListBtn");
        ListView musicListView = GuiTest.find("#musicListView");
        System.out.println(musicListView.getItems().get(0).toString());
        assertTrue(musicListView.getItems().get(0).toString().equals("Havana by Camila Cabello.mp3"));

    }
    @Test
    public void stage4_testRecentPlayedMusicTab(){
        click("#fileMenu").click("Open Folder");

        type("D");
        sleep(500);
        type(KeyCode.DOWN).type(KeyCode.ENTER);
        type("EtcDocument").type(KeyCode.ENTER);
        type("Music").type(KeyCode.ENTER).type(KeyCode.ENTER);
        sleep(500);

        doubleClick("언니네 이발관-10-산들산들.mp3");

        this.sleep(4000);

        click("#recentPlayedMusicListBtn");
        ListView musicListView = GuiTest.find("#musicListView");
        System.out.println(musicListView.getItems().get(0).toString());
        assertTrue(musicListView.getItems().get(0).toString().equals("언니네 이발관-10-산들산들.mp3"));
    }
    @Test
    public void stage5_testAlarm(){
        click("#alarmMenu").click("Set Alarm");

        click("#alarmTextArea").type("TestTest");
        click("#alarmSetBtn");

        sleep(62000);
        assertTrue(GuiTest.find("#alarmShowPane").isVisible());
        assertTrue(((TextArea)GuiTest.find("#alarmShowTextArea")).getText().equals("AlarmTestTest"));
    }
}
