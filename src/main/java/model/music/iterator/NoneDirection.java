package model.music.iterator;

import java.util.Collection;

public class NoneDirection implements Direction {
    @Override
    public int nextPosition(Collection<?> collection, int currentPosition) {
        return -1;
    }
}
