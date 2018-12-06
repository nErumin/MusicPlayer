package model.music.iterator;

import model.music.MusicData;

import java.util.Collection;
import java.util.List;

public class RecentPlayedMusicIterator extends MusicIterator {
    private int currentPosition;

    public RecentPlayedMusicIterator(Collection<MusicData> musics, Direction iteratorDirection) {
        super(musics, iteratorDirection);

        reset();
    }

    @Override
    public void reset() {
        currentPosition = -1;

        moveNext();
    }

    @Override
    public void resetFor(MusicData musicData) {
        List<MusicData> musicDataCollection = getMusics();

        for (int position = 0; position < musicDataCollection.size(); ++position) {
            MusicData currentMusic = musicDataCollection.get(position);
            if (currentMusic.equals(musicData) && currentMusic.getRecentPlayedDate() != null) {
                currentPosition = position;
                setCurrentMusicData(musicData);
                return;
            }
        }
    }

    @Override
    public boolean hasNext() {
        List<MusicData> musicDataCollection = getMusics();
        int nextPosition = getNextMusicPosition();

        return nextPosition < musicDataCollection.size();
    }

    private int getNextMusicPosition() {
        Direction iteratorDirection = getIteratorDirection();
        List<MusicData> musicDataCollection = getMusics();

        int nextPosition = currentPosition;
        nextPosition = iteratorDirection.nextPosition(musicDataCollection, nextPosition);
        while (nextPosition < musicDataCollection.size() &&
               musicDataCollection.get(nextPosition).getRecentPlayedDate() == null) {
            nextPosition = iteratorDirection.nextPosition(musicDataCollection, nextPosition);
        }

        return nextPosition;
    }

    @Override
    public void moveNext() {
        List<MusicData> musicDataCollection = getMusics();

        currentPosition = getNextMusicPosition();
        setCurrentMusicData(musicDataCollection.get(currentPosition));
    }
}
