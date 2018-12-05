package view;

import io.sentry.Sentry;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainGui extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main_gui.fxml"));
        primaryStage.setTitle("MP3 Player");
        primaryStage.setMinHeight(440);
        primaryStage.setMinWidth(620);
        primaryStage.setMaxHeight(440);
        primaryStage.setMaxWidth(620);

        primaryStage.setScene(new Scene(root, 600, 400));

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                Platform.exit();
            }
        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        final String sentryDSN =
            "https://e917f0b7f74a47cd92685dd89b0bcecd@sentry.io/1321363";

        try {
            Sentry.init(sentryDSN);
            launch(args);
        } catch (Exception exception) {
            Sentry.capture(exception);
        }
    }
}
