package model.music;

import javax.sound.sampled.AudioInputStream;

public class PlayerMemento {
    private long progressedMilliSeconds;
    private AudioInputStream playingStream;

    public PlayerMemento(AudioInputStream playingStream, long progressedMilliSeconds) {
        this.progressedMilliSeconds = progressedMilliSeconds;
        this.playingStream = playingStream;
    }

    public long getProgressedMilliSeconds() {
        return progressedMilliSeconds;
    }

    public AudioInputStream getPlayingStream() {
        return playingStream;
    }
}
