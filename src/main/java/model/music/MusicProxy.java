package model.music;

import javafx.scene.image.Image;
import model.music.parser.ParserCreator;

import javax.sound.sampled.AudioInputStream;
import java.util.Date;

public class MusicProxy implements MusicData {
    private Music music;
    private Lyric lyric;
    private String musicFilePath;
    private String lyricFilePath;
    private String filePath;

    public MusicProxy(String filePath) {
        this.filePath = filePath;
    }

    public String getTitle() {
        nullCheck();

        return music.getTitle();
    }

    public String getAlbumName() {
        nullCheck();

        return music.getAlbumName();
    }

    public String getArtist() {
        nullCheck();

        return music.getArtist();
    }

    public AudioInputStream getAudioStream() {
        nullCheck();

        return music.getAudioStream();
    }

    public Image getImage() {
        nullCheck();

        return music.getImage();
    }

    @Override
    public boolean isFavorite() {
        nullCheck();

        return music.isFavorite();
    }

    @Override
    public void setFavorite(boolean isFavorite) {
        nullCheck();

        music.setFavorite(isFavorite);
    }

    @Override
    public Date getRecentPlayedDate() {
        nullCheck();

        return music.getRecentPlayedDate();
    }

    @Override
    public void setRecentPlayedDate(Date recentPlayedDate) {
        nullCheck();

        music.setRecentPlayedDate(recentPlayedDate);
    }

    private void nullCheck() {
        if (music == null) {
            this.music = ParserCreator.getInstance()
                                      .createParser(filePath)
                                      .buildTitle()
                                      .buildImage()
                                      .buildArtist()
                                      .buildAlbumName()
                                      .buildAudioStream()
                                      .build();
        }
        if(lyric == null){
            //가사가 parsing 안되어있으면 parsing해주고, 가사 파일 자체가 없으면 ???
        }

    }
}
