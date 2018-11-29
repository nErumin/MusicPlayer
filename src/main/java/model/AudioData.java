package model;

import org.apache.commons.io.IOUtils;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AudioData {
    private byte[] audioByteData;
    private AudioFormat audioFormat;
    private long sampleLength;

    public AudioData(AudioInputStream audioStream) {
        resetData(audioStream);
    }

    private void resetData(AudioInputStream audioStream) {
        audioByteData = extractAudioData(audioStream);

        if (audioStream != null) {
            audioFormat = audioStream.getFormat();
            sampleLength = audioStream.getFrameLength();
        }
    }

    private byte[] extractAudioData(AudioInputStream audioInputStream) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            IOUtils.copy(audioInputStream, outputStream);

            return outputStream.toByteArray();
        } catch (NullPointerException | IOException exception) {
            return new byte[] { };
        }
    }

    public byte[] getAudioByteData() {
        return audioByteData.clone();
    }

    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    public long getSampleLength() {
        return sampleLength;
    }
}
