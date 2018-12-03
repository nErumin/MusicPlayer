package model.music;

import javax.sound.sampled.AudioInputStream;

public class PlayerMemento {
    private long progressedMilliSeconds;
    private float volumeRatio;
    private AudioInputStream playingStream;

    public PlayerMemento(AudioInputStream playingStream, long progressedMilliSeconds, float volumeRatio) {
        this.progressedMilliSeconds = progressedMilliSeconds;
        this.playingStream = playingStream;
        this.volumeRatio = volumeRatio;
    }

    public long getProgressedMilliSeconds() {
        return progressedMilliSeconds;
    }

    public AudioInputStream getPlayingStream() {
        return playingStream;
    }

    public float getVolumeRatio() {
        return volumeRatio;
    }
}
