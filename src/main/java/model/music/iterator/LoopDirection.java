package model.music.iterator;

import java.util.Collection;

public class LoopDirection implements Direction {
    @Override
    public int nextPosition(Collection<?> collection, int currentPosition) {
        return currentPosition;
    }
}
