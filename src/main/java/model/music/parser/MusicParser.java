package model.music.parser;

import javafx.scene.image.Image;
import model.music.Music;

public abstract class MusicParser {
    protected String filePath;
    protected String title;
    protected String albumName;
    protected String artist;
    protected Image image;

    abstract MusicParser setTitle(String title);

    abstract public MusicParser setImage(Image image);

    abstract public MusicParser setArtist(String artist);

    abstract public MusicParser setAlbumName(String albumName);

    public Music build(){ return new Music(this.title, this.artist, this.albumName, this.image); }
}
