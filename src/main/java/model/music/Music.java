package model.music;

import javafx.scene.image.Image;
import model.AudioData;
import org.apache.commons.io.IOUtils;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Music implements MusicData {
    private String title;
    private String artist;
    private String albumName;
    private Image image;
    private AudioData audioData;

    public Music(String title, String artist, String albumName, Image image, AudioInputStream audioStream) {
        this.title = title;
        this.artist = artist;
        this.albumName = albumName;
        this.image = image;

        audioData = new AudioData(audioStream);
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
        if (audioData.isDataValid() == false) {
            return null;
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(audioData.getAudioByteData());
        return new AudioInputStream(inputStream, audioData.getAudioFormat(), audioData.getSampleLength());
    }
}
