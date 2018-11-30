package model.music;

import javafx.scene.image.Image;
import model.music.parser.MusicParser;
import model.music.parser.ParserCreator;

import javax.sound.sampled.AudioInputStream;

public class MusicProxy implements MusicData {
    private Music music;
    private Lyric lyric;
    private String musicFilePath;
    private String lyricFilePath;

    public MusicProxy(String musicFilePath, String lyricFilePath){
        this.musicFilePath = musicFilePath;
        this.lyricFilePath = lyricFilePath;
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

    public AudioInputStream getAudioStream() {
        nullCheck();
        return music.getAudioStream();
    }

    public Image getImage(){
        nullCheck();
        return music.getImage();
    }
    private void nullCheck(){
        if(music == null) {
            this.music = ParserCreator.getInstance().parseMusic(musicFilePath);
        }
        if(lyric == null){
            //가사가 parsing 안되어있으면 parsing해주고, 가사 파일 자체가 없으면 ???
        }

    }
}
