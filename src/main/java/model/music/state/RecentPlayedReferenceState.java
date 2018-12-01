package model.music.state;

import model.Path;
import model.music.MusicData;
import model.music.iterator.ForwardDirection;
import model.music.iterator.MusicIterator;
import model.music.iterator.RecentPlayedMusicIterator;

import java.util.*;
import java.util.stream.Collectors;

public class RecentPlayedReferenceState extends ListReferenceState {
    public RecentPlayedReferenceState(Map<Path, MusicData> musicFiles) {
        super(musicFiles);
    }

    @Override
    public Collection<String> getSortedFileNames() {
        return getSortedEntries().stream()
                                 .map(Map.Entry::getKey)
                                 .map(Path::getFileName)
                                 .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<Path, MusicData>> getSortedEntries() {
        return getEntries().stream()
                           .sorted((leftEntry, rightEntry) -> rightEntry.getValue().getRecentPlayedDate().compareTo(leftEntry.getValue().getRecentPlayedDate()))
                           .collect(Collectors.toList());
    }

    @Override
    public Set<Map.Entry<Path, MusicData>> getEntries() {
        return getMusicFiles().entrySet()
                              .stream()
                              .filter(entry -> entry.getValue().getRecentPlayedDate() != null)
                              .collect(Collectors.toSet());
    }

    @Override
    public MusicIterator makeIterator(Collection<MusicData> musicDataCollection) {
        return new RecentPlayedMusicIterator(musicDataCollection, new ForwardDirection());
    }
}
