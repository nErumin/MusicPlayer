package model.music.iterator;

import java.util.Collection;

public interface Direction {
    int nextPosition(Collection<?> collection, int currentPosition);
}
