package model.music.parser;

import javafx.scene.image.Image;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.Tag;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;

public class WAVParser extends MusicParser {
    String decoding = "ISO-8859-1";
    String encoding = "EUC-KR";
    File file;

    public WAVParser(String filePath){
        super.filePath = filePath;
        file = new File(filePath);
    }
    @Override
    public MusicParser buildTitle() {
        return this;
    }

    @Override
    public MusicParser buildImage(){
        return this;
    }

    @Override
    public MusicParser buildArtist() {
        return this;
    }

    @Override
    public MusicParser buildAlbumName() {
        return this;
    }

    @Override
    public MusicParser buildAudioStream() {

        try {
            super.audioStream = getAudioInputStream(file);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
