package model.music.iterator;

import model.music.MusicData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class MusicIterator {
    private Collection<MusicData> musics;
    private Direction iteratorDirection;
    private MusicData currentMusicData;

    public MusicIterator(Collection<MusicData> musics, Direction iteratorDirection) {
        this.musics = new ArrayList<>(musics);
        currentMusicData = null;

        setIteratorDirection(iteratorDirection);
    }

    protected List<MusicData> getMusics() {
        return new ArrayList<>(musics);
    }

    protected void setCurrentMusicData(MusicData newCurrentMusicData) {
        currentMusicData = newCurrentMusicData;
    }

    protected Direction getIteratorDirection() {
        return iteratorDirection;
    }

    public void setIteratorDirection(Direction newDirection) {
        iteratorDirection = newDirection;
    }

    public MusicData getCurrentMusicData() {
        return currentMusicData;
    }

    public abstract void reset();
    public abstract void resetFor(MusicData musicData);
    public abstract boolean moveNext();
}
