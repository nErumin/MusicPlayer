package model.music;

import javafx.scene.image.Image;
import model.music.parser.ParserCreator;

import javax.sound.sampled.AudioInputStream;

public class Music implements MusicData {
    private String title;
    private String artist;
    private String albumName;
    private Image image;
    private AudioInputStream audioStream;

    public Music(String title, String artist, String albumName, Image image, AudioInputStream audioStream){
        this.title = title;
        this.artist = artist;
        this.albumName = albumName;
        this.image = image;
        this.audioStream = audioStream;
    }
    public String getTitle(){
        return title;
    }
    public String getArtist(){
        return  artist;
    }
    public String getAlbumName(){
        return albumName;
    }
    public Image getImage(){
        return image;
    }
    public AudioInputStream getAudioStream() { return audioStream; }
}
