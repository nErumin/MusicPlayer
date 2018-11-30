package model.music;

import model.music.iterator.MusicIterator;
import model.music.iterator.NoneMusicIterator;

import javax.sound.sampled.*;
import java.io.IOException;

public class MusicPlayer {
    private MusicIterator iterationMode;
    private Clip playingClip;
    private AudioInputStream playingInputStream;

    public MusicPlayer() throws LineUnavailableException {
        this(new NoneMusicIterator());
    }

    public MusicPlayer(MusicIterator iterationMode) throws LineUnavailableException {
        setIterationMode(iterationMode);

        playingClip = makeNewClip();
    }

    private Clip makeNewClip() throws LineUnavailableException {
        return AudioSystem.getClip();
    }

    public void setIterationMode(MusicIterator iterationMode) {
        this.iterationMode = iterationMode;
    }

    public void startPlay() {
        stopPlay();

        playingInputStream = iterationMode.getCurrentMusicData().getAudioStream();

        try {
            playingClip = makeNewClip();
            playingClip.addLineListener(this::handleClipFinished);

            playingClip.open(playingInputStream);
            playingClip.start();
        } catch (LineUnavailableException | IOException | IllegalStateException exception) {
            stopPlay();
        }
    }

    public void stopPlay() {
        playingClip.close();
    }

    private void handleClipFinished(LineEvent lineEvent) {
        playingClip.removeLineListener(this::handleClipFinished);

        if (lineEvent.getType() == LineEvent.Type.STOP &&
            lineEvent.getFramePosition() == playingInputStream.getFrameLength()) {

            playNextMusic();
        }
    }

    private void playNextMusic() {
        if (iterationMode.hasNext()) {
            iterationMode.moveNext();

            startPlay();
        } else {
            stopPlay();
        }
    }

    public PlayerMemento createMemento() {
        return new PlayerMemento(playingClip.getMicrosecondLength());
    }

    public void recoverState(PlayerMemento memento) {
        if (playingClip != null) {
            playingClip.setMicrosecondPosition(memento.getProgressedMilliSeconds());
        }
    }
}
