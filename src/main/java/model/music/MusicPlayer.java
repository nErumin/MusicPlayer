package model.music;

import model.music.iterator.MusicIterator;
import model.music.iterator.NoneMusicIterator;
import utility.MathUtility;

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
    private float currentVolumeRatio = 1.0f;
    private boolean looping = false;

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
            setVolumeRatio(currentVolumeRatio);

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

    public boolean isLooping() {
        return looping;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
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
        long microSeconds = TimeUnit.SECONDS.toMicros(seconds);
        long currentProgressedMicroSeconds = playingClip.getMicrosecondPosition();

        playFromMicroSeconds(currentProgressedMicroSeconds + microSeconds);
    }

    public void playFromLengthRatio(double ratio) {
        long microPosition = (long) (playingClip.getMicrosecondLength() * ratio);

        playFromMicroSeconds(microPosition);
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
        } else if (lineEvent.getType() != LineEvent.Type.STOP){
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

    public float getVolumeRatio() {
        return currentVolumeRatio;
    }

    public void setVolumeRatio(float ratio) {
        FloatControl volumeControl = (FloatControl) playingClip.getControl(FloatControl.Type.MASTER_GAIN);

        currentVolumeRatio = MathUtility.clamp(ratio, 0.0f, 1.0f);
        float range = volumeControl.getMaximum() - volumeControl.getMinimum();
        float gain = (range * currentVolumeRatio) + volumeControl.getMinimum();
        volumeControl.setValue(gain);
    }

    public PlayerMemento createMemento() {
        return new PlayerMemento(playingInputStream, playingClip.getMicrosecondPosition(), getVolumeRatio());
    }

    public void recoverVolumeState(PlayerMemento memento) {
        if (playingClip != null &&
            playingInputStream != null &&
            memento.getPlayingStream().equals(playingInputStream)) {

            setVolumeRatio(memento.getVolumeRatio());
        }
    }

    public void recoverPlayState(PlayerMemento memento) {
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
