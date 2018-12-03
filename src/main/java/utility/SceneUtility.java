package utility;

import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public final class SceneUtility {
    public static void registerKey(Scene scene, KeyCombination key, Action handler) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (key.match(event)) {
                handler.act();
            }
        });
    }

    private SceneUtility() {

    }
}
