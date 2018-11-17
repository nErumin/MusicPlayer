package model.music.parser;

import javafx.scene.image.Image;
import model.music.Music;

public abstract class MusicParser {
    private String title;
    private Image image;

    public MusicParser setTitle(String title){
        this.title = title;
        return this;
    }
    public MusicParser setImage(Image imgae){
        this.image = image;
        return this;
    }
    abstract public Music build();
}
