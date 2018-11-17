package model.music.parser;

import javafx.scene.image.Image;

public class MP3Parser extends MusicParser {

    public MP3Parser(String filePath){
        super.filePath = filePath;
    }

    @Override
    MusicParser setTitle(String title) {
        super.title = title;
        return null;
    }

    @Override
    public MusicParser setImage(Image image) {
        super.image = image;
        return null;
    }

    @Override
    public MusicParser setArtist(String artist) {
        super.artist = artist;
        return null;
    }

    @Override
    public MusicParser setAlbumName(String albumName) {
        super.albumName = albumName;
        return null;
    }

}
