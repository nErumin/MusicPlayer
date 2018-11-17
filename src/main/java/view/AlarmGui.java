package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AlarmGui extends Stage {
    public AlarmGui(){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("alarm_gui.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);

        this.setMinWidth(330);
        this.setMinHeight(230);
        this.setMaxWidth(330);
        this.setMaxHeight(230);

        this.setScene(scene);
    }

}
