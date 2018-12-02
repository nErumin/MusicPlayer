package model.music.parser.music_parser;

import javafx.scene.image.Image;
import model.music.Music;

import javax.sound.sampled.AudioInputStream;

public abstract class MusicParser {
    protected String filePath;
    protected String title;
    protected String albumName;
    protected String artist;
    protected Image image;
    protected AudioInputStream audioStream;


    abstract public MusicParser buildTitle();

    abstract public MusicParser buildImage();

    abstract public MusicParser buildArtist();

    abstract public MusicParser buildAlbumName();

    abstract public MusicParser buildAudioStream();

    public Music build() {
        return new Music(this.title, this.artist, this.albumName, this.image, this.audioStream);
    }
}
