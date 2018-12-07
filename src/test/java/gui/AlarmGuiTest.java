package gui;

import com.google.common.util.concurrent.SettableFuture;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;
import org.loadui.testfx.utils.UserInputDetector;
import view.MainGui;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assume.assumeTrue;

public class AlarmGuiTest extends GuiTest {
    private static final SettableFuture<Stage> stageFuture = SettableFuture.create();

    /**
     * The type Test main window.
     */
    protected static class TestMainWindow extends MainGui {
        /**
         * Instantiates a new Test.
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

        FXTestUtils.launchApp(AlarmGuiTest.TestMainWindow.class); // You can add start parameters here
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
        click("#alarmMenu").click("Set Alarm");

        Calendar currentTime = Calendar.getInstance();

        int ampm = currentTime.get(Calendar.AM_PM);
        int hour = currentTime.get(Calendar.HOUR);
        int minute = currentTime.get(Calendar.MINUTE);

        if( hour == 0 ) hour = 12;
        assertTrue(GuiTest.find("#alarmPane").isVisible());
        assertTrue(((TextArea)GuiTest.find("#alarmTextArea")).getText().equals("Alarm"));
        assertTrue(((ComboBox)GuiTest.find("#ampmComboBox")).getSelectionModel().getSelectedIndex() == ampm);
        assertTrue(((ComboBox)GuiTest.find("#hourComboBox")).getSelectionModel().getSelectedIndex() == hour -1);
        assertTrue(((ComboBox)GuiTest.find("#minuteComboBox")).getSelectionModel().getSelectedIndex() == minute);
        click("#alarmSetBtn");
    }
    @Test
    public void stage1_testAlarm(){
        click("#alarmMenu").click("Set Alarm");

        click("#alarmTextArea").type("TestTest");
        click("#alarmSetBtn");

        sleep(62000);
        assertTrue(GuiTest.find("#alarmShowPane").isVisible());
        assertTrue(((TextArea)GuiTest.find("#alarmShowTextArea")).getText().equals("AlarmTestTest"));
    }
}
