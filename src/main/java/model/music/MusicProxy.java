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
        nullCheck(music);
        return music.getTitle();
    }
    public String getAlbumName(){
        nullCheck(music);
        return music.getAlbumName();
    }
    public String getArtist(){
        nullCheck(music);
        return music.getArtist();
    }
    public Image getImage(){
        nullCheck(music);
        return music.getImage();
    }
    private void nullCheck(Music music){
        if(music == null) {
            this.music = ParserCreator.getInstance().parseMusic(filePath);
        }
    }
}
