package model.music;

import javax.sound.sampled.AudioInputStream;

public class PlayerMemento {
    private long progressedMicroSeconds;
    private AudioInputStream playingStream;

    public PlayerMemento(AudioInputStream playingStream, long progressedMicroSeconds) {
        this.progressedMicroSeconds = progressedMicroSeconds;
        this.playingStream = playingStream;
    }

    public long getProgressedMicroSeconds() {
        return progressedMicroSeconds;
    }

    public AudioInputStream getPlayingStream() {
        return playingStream;
    }
}
