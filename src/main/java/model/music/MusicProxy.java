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
        if(music == null) {
            Music music = ParserCreator.getInstance().parseMusic(filePath);
        }
        return music.getTitle();
    }
    public String getAlbumName(){
        if(music == null) {
            Music music = ParserCreator.getInstance().parseMusic(filePath);
        }
        return music.getAlbumName();
    }
    public String getArtist(){
        if(music == null) {
            Music music = ParserCreator.getInstance().parseMusic(filePath);
        }
        return music.getArtist();
    }
    public Image getImage(){
        if(music == null) {
            Music music = ParserCreator.getInstance().parseMusic(filePath);
        }
        return music.getImage();
    }
}
