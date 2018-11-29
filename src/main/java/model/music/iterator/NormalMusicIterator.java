package model.music.iterator;

import model.music.MusicData;

import java.util.Collection;
import java.util.List;

public class NormalMusicIterator extends MusicIterator {
    private int currentPosition;

    public NormalMusicIterator(Collection<MusicData> musics, Direction iteratorDirection) {
        super(musics, iteratorDirection);

        reset();
    }

    @Override
    public boolean moveNext() {
        Direction iteratorDirection = getIteratorDirection();
        List<MusicData> musicDataCollection = getMusics();
        int nextPosition = iteratorDirection.nextPosition(musicDataCollection, currentPosition);

        if (nextPosition < musicDataCollection.size()) {
            setCurrentMusicData(musicDataCollection.get(nextPosition));
            return true;
        }

        return false;
    }


    @Override
    public void reset() {
        currentPosition = 0;

        List<MusicData> musicDataCollection = getMusics();
        MusicData defaultMusicData =
            musicDataCollection.size() > 0 ? musicDataCollection.get(currentPosition) : null;

        setCurrentMusicData(defaultMusicData);
    }

    @Override
    public void resetFor(MusicData musicData) {
        List<MusicData> musicDataCollection = getMusics();

        for (currentPosition = 0; currentPosition < musicDataCollection.size(); ++currentPosition) {
            if (musicDataCollection.get(currentPosition).equals(musicData)) {
                setCurrentMusicData(musicData);
                return;
            }
        }
    }
}
