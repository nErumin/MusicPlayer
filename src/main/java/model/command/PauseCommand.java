package model.command;

import model.music.MusicPlayer;

public class PauseCommand implements Command {
    private MusicPlayer musicPlayer;

    public PauseCommand(MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
    }

    @Override
    public void execute() {
        musicPlayer.pausePlay();
    }

    @Override
    public void undo() {
        musicPlayer.resumePlay();
    }
}
