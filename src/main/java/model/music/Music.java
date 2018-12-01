package model.music;

import javafx.scene.image.Image;
import model.AudioData;

import javax.sound.sampled.AudioInputStream;
import java.io.ByteArrayInputStream;
import java.util.Date;

public class Music implements MusicData {
    private String title;
    private String artist;
    private String albumName;
    private Image image;
    private AudioData audioData;
    private boolean isFavoriteSelected;
    private Date recentPlayedDate;

    public Music(String title, String artist, String albumName, Image image, AudioInputStream audioStream) {
        this.title = title;
        this.artist = artist;
        this.albumName = albumName;
        this.image = image;
        this.isFavoriteSelected = false;
        this.recentPlayedDate = null;

        audioData = new AudioData(audioStream);
    }

    @Override
    public boolean isFavorite() {
        return isFavoriteSelected;
    }

    @Override
    public void setFavorite(boolean isFavorite) {
        isFavoriteSelected = isFavorite;
    }

    @Override
    public Date getRecentPlayedDate() {
        return recentPlayedDate;
    }

    @Override
    public void setRecentPlayedDate(Date recentPlayedDate) {
        this.recentPlayedDate = recentPlayedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return  artist;
    }

    public String getAlbumName() {
        return albumName;
    }

    public Image getImage() {
        return image;
    }

    public AudioInputStream getAudioStream() {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(audioData.getAudioByteData());
            return new AudioInputStream(inputStream, audioData.getAudioFormat(), audioData.getSampleLength());
        } catch (NullPointerException exception) {
            return null;
        }
    }
}
