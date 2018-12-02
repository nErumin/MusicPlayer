package model.music;

import javafx.scene.image.Image;

import javax.sound.sampled.AudioInputStream;
import java.util.Date;

public interface MusicData {
    Image getImage();
    String getTitle();
    String getAlbumName();
    String getArtist();
    AudioInputStream getAudioStream();

    boolean isFavorite();
    void setFavorite(boolean isFavorite);
    Date getRecentPlayedDate();
    void setRecentPlayedDate(Date recentPlayedDate);
}

