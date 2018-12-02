package model.music.state;

import model.Path;
import model.music.MusicData;
import model.music.iterator.FavoriteMusicIterator;
import model.music.iterator.ForwardDirection;
import model.music.iterator.MusicIterator;

import java.util.*;
import java.util.stream.Collectors;

public class FavoriteReferenceState extends ListReferenceState {
    public FavoriteReferenceState(Map<Path, MusicData> musicFiles) {
        super(musicFiles);
    }

    @Override
    public Set<Map.Entry<Path, MusicData>> getEntries() {
        return getMusicFiles().entrySet()
                              .stream()
                              .filter(entry -> entry.getValue().isFavorite())
                              .collect(Collectors.toSet());
    }

    @Override
    public MusicIterator makeIterator(Collection<MusicData> musicDataCollection) {
        return new FavoriteMusicIterator(musicDataCollection, new ForwardDirection());
    }
}
