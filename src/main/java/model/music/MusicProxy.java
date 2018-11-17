package model.music;

import javafx.scene.image.Image;
import model.music.parser.MusicParser;
import model.music.parser.ParserCreator;

public class MusicProxy implements MusicData {
    private Music music;
    private String filePath;

    public MusicProxy(String filePath){
        this.filePath = filePath;
    }

    public String getTitle(){
        nullCheck();
        return music.getTitle();
    }
    public String getAlbumName(){
        nullCheck();
        return music.getAlbumName();
    }
    public String getArtist(){
        nullCheck();
        return music.getArtist();
    }
    public Image getImage(){
        nullCheck();
        return music.getImage();
    }
    public void nullCheck(){
        if(music == null) {
            Music music = ParserCreator.getInstance().parseMusic(filePath);
        }
    }
}
