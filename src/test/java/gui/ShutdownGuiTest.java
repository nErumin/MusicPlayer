package gui;

import com.google.common.util.concurrent.SettableFuture;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.Before;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;
import org.loadui.testfx.utils.UserInputDetector;
import view.MainGui;

import java.util.concurrent.TimeUnit;

import static org.junit.Assume.assumeTrue;

public class ShutdownGuiTest extends GuiTest {
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

        FXTestUtils.launchApp(ShutdownGuiTest.TestMainWindow.class); // You can add start parameters here
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
        click("#shutdownMenu").click("Set Shutdown");

        assertTrue(GuiTest.find("#shutdownPane").isVisible());
        assertTrue(((ComboBox)GuiTest.find("#shutdownTimeComboBox")).getSelectionModel().getSelectedIndex() == 0);
    }
    */
}
