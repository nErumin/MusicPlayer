package model.command;

import model.music.MusicPlayer;
import model.music.PlayerMemento;

public class VolumeIncreaseCommand implements Command {
    private PlayerMemento memento;
    private MusicPlayer musicPlayer;
    private float increasingRatio;

    public VolumeIncreaseCommand(MusicPlayer musicPlayer, float increasingRatio) {
        this.musicPlayer = musicPlayer;
        this.increasingRatio = increasingRatio;
    }

    @Override
    public void execute() {
        memento = musicPlayer.createMemento();
        musicPlayer.setVolumeRatio(musicPlayer.getVolumeRatio() + increasingRatio);
    }

    @Override
    public void undo() {
        if (memento != null) {
            musicPlayer.recoverVolumeState(memento);
        }
    }
}
