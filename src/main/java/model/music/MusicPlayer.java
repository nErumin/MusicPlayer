package model.music;

import model.music.iterator.MusicIterator;
import model.music.iterator.NoneMusicIterator;

import javax.sound.sampled.*;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MusicPlayer {
    private MusicIterator iterationMode;
    private Clip playingClip;
    private AudioInputStream playingInputStream;

    private LineListener lineHandler = this::handleClipFinished;

    public MusicPlayer() throws LineUnavailableException {
        this(new NoneMusicIterator());
    }

    public MusicPlayer(MusicIterator iterationMode) throws LineUnavailableException {
        setIterationMode(iterationMode);

        playingClip = AudioSystem.getClip();
    }

    private Clip makeNewClip() throws LineUnavailableException {
        Clip newClip = AudioSystem.getClip();
        newClip.addLineListener(lineHandler);

        return newClip;
    }

    public void setIterationMode(MusicIterator iterationMode) {
        this.iterationMode = iterationMode;
    }

    public synchronized void startPlay() {
        stopPlay();

        MusicData currentPlayedMusic = getCurrentPlayedMusic();

        currentPlayedMusic.setRecentPlayedDate(Date.from(ZonedDateTime.now().toInstant()));
        playingInputStream = currentPlayedMusic.getAudioStream();

        try {
            playingClip.addLineListener(lineHandler);
            playingClip.open(playingInputStream);
            playingClip.start();
        } catch (LineUnavailableException | IOException | IllegalStateException exception) {
            exception.printStackTrace();

            stopPlay();
        }
    }

    public void pausePlay() {
        if (playingClip.isRunning()) {
            playingClip.stop();
        }
    }

    public void resumePlay() {
        if (playingClip.isRunning() == false) {
            playingClip.start();
        }
    }

    public void stopPlay() {
        playingClip.removeLineListener(lineHandler);

        playingClip.close();
    }

    public void skipSeconds(int seconds) {
        long milliseconds = TimeUnit.SECONDS.toMicros(seconds);
        long currentProgressedMilliSeconds = playingClip.getMicrosecondPosition();

        playFromMicroSeconds(currentProgressedMilliSeconds + milliseconds);
    }

    public void playFromLengthRatio(double ratio) {
        long milliPosition = (long) (playingClip.getMicrosecondLength() * ratio);

        playFromMicroSeconds(milliPosition);
    }

    private void playFromMicroSeconds(long microseconds) {
            microseconds = microseconds < playingClip.getMicrosecondLength() ?
                microseconds : playingClip.getMicrosecondLength();

            playingClip.setMicrosecondPosition(microseconds);
    }

    private void handleClipFinished(LineEvent lineEvent) {
        if (lineEvent.getType() == LineEvent.Type.STOP && isPlayingFinished()) {
            playingClip.removeLineListener(lineHandler);
            playNextMusic();
        } else if (lineEvent.getType() == LineEvent.Type.CLOSE) {
            playingClip.removeLineListener(lineHandler);
        } else {
            playingClip.start();
        }
    }

    private boolean isPlayingFinished() {
        return playingClip.getMicrosecondPosition() >= playingClip.getMicrosecondLength();
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
        return new PlayerMemento(playingInputStream, playingClip.getMicrosecondPosition());
    }

    public void recoverState(PlayerMemento memento) {
        if (playingClip != null &&
            playingInputStream != null &&
            memento.getPlayingStream().equals(playingInputStream)) {

            playFromMicroSeconds(memento.getProgressedMilliSeconds());
        }
    }

    public MusicData getCurrentPlayedMusic() {
        return iterationMode.getCurrentMusicData();
    }
}
