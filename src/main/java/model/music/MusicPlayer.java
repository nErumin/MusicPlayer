package model.music;

import model.music.iterator.MusicIterator;
import model.music.iterator.NoneMusicIterator;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class MusicPlayer {
    private MusicIterator iterationMode;
    private Clip playingClip;
    private AudioInputStream playingInputStream;
    private List<Consumer<MusicData>> musicStartingListeners;
    private List<Consumer<MusicData>> musicEndingListeners;

    private LineListener lineHandler = this::handleClipFinished;

    public MusicPlayer() throws LineUnavailableException {
        this(new NoneMusicIterator());
    }

    public MusicPlayer(MusicIterator iterationMode) throws LineUnavailableException {
        setIterationMode(iterationMode);

        playingClip = AudioSystem.getClip();
        musicStartingListeners = new ArrayList<>();
        musicEndingListeners = new ArrayList<>();
    }

    public void registerStartListener(Consumer<MusicData> listener) {
        musicStartingListeners.add(listener);
    }

    public void UnregisterStartListener(Consumer<MusicData> listener) {
        musicStartingListeners.remove(listener);
    }

    public void registerEndListener(Consumer<MusicData> listener) {
        musicEndingListeners.add(listener);
    }

    public void UnregisterEndListener(Consumer<MusicData> listener) {
        musicEndingListeners.remove(listener);
    }

    public void setIterationMode(MusicIterator iterationMode) {
        this.iterationMode = iterationMode;
    }

    public synchronized void startPlay() {
        stopPlay();

        MusicData currentPlayedMusic = getCurrentPlayedMusic();
        playingInputStream = currentPlayedMusic.getAudioStream();

        musicStartingListeners.forEach(consumer -> consumer.accept(currentPlayedMusic));

        try {
            playingClip.addLineListener(lineHandler);
            playingClip.open(playingInputStream);
            playingClip.start();
        } catch (LineUnavailableException | IOException | IllegalStateException exception) {
            exception.printStackTrace();

            stopPlay();
        }
    }

    public Clip getPlayingClip(){
        try{
            return playingClip;
        } catch(NullPointerException e){
            return null;
        }
    }

    public boolean isPaused() {
        return playingClip.isRunning() == false;
    }

    public void pausePlay() {
        if (isPaused() == false) {
            playingClip.stop();
        }
    }

    public void resumePlay() {
        if (isPaused()) {
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

            musicEndingListeners.forEach(consumer -> consumer.accept(getCurrentPlayedMusic()));
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

    public void setVolumeRatio(float ratio) {
        FloatControl volumeControl = (FloatControl) playingClip.getControl(FloatControl.Type.MASTER_GAIN);

        float range = volumeControl.getMaximum() - volumeControl.getMinimum();
        float gain = (range * ratio) + volumeControl.getMinimum();
        volumeControl.setValue(gain);
    }

    public PlayerMemento createMemento() {
        return new PlayerMemento(playingInputStream, playingClip.getMicrosecondPosition());
    }

    public void recoverState(PlayerMemento memento) {
        if (playingClip != null &&
            playingInputStream != null &&
            memento.getPlayingStream().equals(playingInputStream)) {

            playFromMicroSeconds(memento.getProgressedMicroSeconds());
        }
    }

    public MusicData getCurrentPlayedMusic() {
        return iterationMode.getCurrentMusicData();
    }
}
