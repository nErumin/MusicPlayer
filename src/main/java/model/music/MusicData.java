package model.music;

import javafx.scene.image.Image;

import javax.sound.sampled.AudioInputStream;

public interface MusicData {
    public Image getImage();
    public String getTitle();
    public String getAlbumName();
    public String getArtist();
    public AudioInputStream getAudioStream();
}

