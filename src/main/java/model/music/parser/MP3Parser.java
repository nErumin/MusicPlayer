package model.music.parser;

import javafx.embed.swing.SwingFXUtils;
import org.apache.commons.io.FilenameUtils;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.*;

import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.*;

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
        try {
            Artwork awk = tag.getFirstArtwork();
            BufferedImage image = (BufferedImage) awk.getImage();
            super.image = SwingFXUtils.toFXImage(image, null);
        } catch (NullPointerException | IOException exception) {
            super.image = null;
        }

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

            AudioInputStream mp3InputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat decodedFormat = makeDecodedFormat(mp3InputStream.getFormat());

            super.audioStream = AudioSystem.getAudioInputStream(decodedFormat, mp3InputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    private AudioFormat makeDecodedFormat(AudioFormat baseFormat) {
        return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                               baseFormat.getSampleRate(), 16,
                               baseFormat.getChannels(),
                               baseFormat.getChannels() * 2,
                               baseFormat.getSampleRate(), false);
    }
}
