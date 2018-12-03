package model.music;

import javax.sound.sampled.AudioInputStream;

public class PlayerMemento {
    private long progressedMicroSeconds;
    private float volumeRatio;
    private AudioInputStream playingStream;

    public PlayerMemento(AudioInputStream playingStream, long progressedMicroSeconds, float volumeRatio) {
        this.progressedMicroSeconds = progressedMicroSeconds;
        this.playingStream = playingStream;
        this.volumeRatio = volumeRatio;
    }

    public long getProgressedMicroSeconds() {
        return progressedMicroSeconds;
    }

    public AudioInputStream getPlayingStream() {
        return playingStream;
    }

    public float getVolumeRatio() {
        return volumeRatio;
    }
}
