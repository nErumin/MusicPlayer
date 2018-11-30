package model.music.parser;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.media.Media;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.*;

import javax.sound.sampled.*;
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
            super.image = null;
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
            File file = new File(super.filePath);
            System.out.println(file);
            AudioFileFormat baseFileFormat = null;
            AudioFormat baseFormat = null;
            baseFileFormat = AudioSystem.getAudioFileFormat(file);
            baseFormat = baseFileFormat.getFormat();
            AudioFileFormat.Type type = baseFileFormat.getType();
            float frequency = baseFormat.getSampleRate();


//            File file = new File(super.filePath);
//            AudioInputStream in = AudioSystem.getAudioInputStream(file);
//            AudioFormat baseFormat = in.getFormat();
//            AudioFormat decodedFormat = new AudioFormat(
//                AudioFormat.Encoding.PCM_SIGNED,
//                baseFormat.getSampleRate(),
//                16,
//                baseFormat.getChannels(),
//                baseFormat.getChannels() * 2,
//                baseFormat.getSampleRate(),
//                false);


            return this;
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
