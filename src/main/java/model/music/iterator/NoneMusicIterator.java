package model.music.iterator;

import model.music.MusicData;

import java.util.ArrayList;
import java.util.Collection;

public class NoneMusicIterator extends MusicIterator {
    public NoneMusicIterator() {
        super(new ArrayList<>(), new NoneDirection());
    }

    @Override
    public void reset() {

    }

    @Override
    public void resetFor(MusicData musicData) {

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public void moveNext() {

    }
}
