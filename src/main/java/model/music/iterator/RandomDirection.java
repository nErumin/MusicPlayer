package model.music.iterator;

import java.util.Collection;
import java.util.Random;

public class RandomDirection implements Direction {
    private static Random randomGenerator = new Random();

    @Override
    public int nextPosition(Collection<?> collection, int currentPosition) {
        return randomGenerator.nextInt(collection.size());
    }
}
