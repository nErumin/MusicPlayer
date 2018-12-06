package model.music.parser.music_parser;

import javafx.scene.image.Image;
import org.apache.commons.io.FilenameUtils;

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
        this.title = FilenameUtils.getBaseName(file.getPath());
        return this;
    }

    @Override
    public MusicParser buildImage(){
        this.image = new Image(getClass().getClassLoader().getResourceAsStream("image/defaultImage.jpg"));
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
