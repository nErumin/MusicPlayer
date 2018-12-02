package model.music;

import javafx.scene.image.Image;
import model.music.parser.music_parser.ParserCreator;
import model.music.parser.lyric_parser.LyricParser;

import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.util.Date;

public class MusicProxy implements MusicData {
    private Music music;
    private Lyric lyric;
    private String filePath;
    private boolean isLyric = false;

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

    @Override
    public Lyric getLyric() {
        if(isLyric()) {
            return lyric;
        }
        return null;
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

    @Override
    public boolean isLyric() {
        nullCheck();

        return isLyric;
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
                try {
                    this.lyric = new LyricParser(filePath).getLyric();
                    this.isLyric = true;
                } catch (NullPointerException e) {
                    return;
                }
            //가사가 parsing 안되어있으면 parsing해주고, 가사 파일 자체가 없으면 ???
        }

    }
}
