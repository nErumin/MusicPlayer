package utility;

import java.util.ArrayList;
import java.util.List;

public final class IterableUtility {
    public static <T> List<T> toList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    private IterableUtility() {

    }
}
