package model.command;

import model.music.MusicPlayer;
import model.music.PlayerMemento;

public class SkipCommand implements Command {
    private PlayerMemento memento;
    private MusicPlayer musicPlayer;
    private int skipSeconds;

    public SkipCommand(MusicPlayer musicPlayer, int skipSeconds) {
        this.musicPlayer = musicPlayer;
        this.skipSeconds = skipSeconds;
    }

    @Override
    public void execute() {
        memento = musicPlayer.createMemento();

        musicPlayer.skipSeconds(skipSeconds);
    }

    @Override
    public void undo() {
        if (memento != null) {
            musicPlayer.recoverPlayState(memento);
        }
    }
}
