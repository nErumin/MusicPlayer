package model.music.parser;

import javafx.embed.swing.SwingFXUtils;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.images.*;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;


public class MP3Parser extends MusicParser {

    String decoding = "ISO-8859-1";
    String encoding = "EUC-KR";
    Tag tag;
    File file;

    public MP3Parser(String filePath) {
        super.filePath = filePath;
        MP3File mp3;
        file = new File(filePath);
        try {
            mp3 = (MP3File) AudioFileIO.read(file);
            tag = mp3.getTag();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("파일 읽기 오류\n");
        }
    }

    @Override
    public MusicParser buildTitle() {
        super.title = tag.getFirst(FieldKey.TITLE);
        return this;
    }

    @Override
    public MusicParser buildImage() {
        Artwork awk = tag.getFirstArtwork();
        BufferedImage image = null;
        try {
            image = (BufferedImage) awk.getImage();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("이미지가 없습니다.\n");
        }
        super.image = SwingFXUtils.toFXImage(image, null);
        return this;
    }

    @Override
    public MusicParser buildArtist() {
        super.artist = tag.getFirst(FieldKey.ARTIST);
        return this;
    }

    @Override
    public MusicParser buildAlbumName() {
        super.albumName = tag.getFirst(FieldKey.ALBUM);
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
