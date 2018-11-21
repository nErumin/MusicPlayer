package model.music;

import javafx.scene.image.Image;
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

    private byte[] audioData;
    private AudioFormat audioFormat;
    private long audioSample;

    public Music(String title, String artist, String albumName, Image image, AudioInputStream audioStream) {
        this.title = title;
        this.artist = artist;
        this.albumName = albumName;
        this.image = image;

        this.audioData = extractAudioData(audioStream);
        this.audioFormat = audioStream.getFormat();
        this.audioSample = audioStream.getFrameLength();
    }

    private byte[] extractAudioData(AudioInputStream audioInputStream) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            IOUtils.copy(audioInputStream, outputStream);

            return outputStream.toByteArray();
        } catch (IOException exception) {
            return null;
        }
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
        ByteArrayInputStream inputStream = new ByteArrayInputStream(audioData);
        return new AudioInputStream(inputStream, audioFormat, audioSample);
    }
}
