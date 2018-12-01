package model.music.state;

import model.Path;
import model.music.MusicData;
import model.music.iterator.MusicIterator;

import java.util.*;
import java.util.stream.Collectors;

public abstract class ListReferenceState {
    private Map<Path, MusicData> musicFiles;

    public ListReferenceState(Map<Path, MusicData> musicFiles) {
        this.musicFiles = musicFiles;
    }

    protected final Map<Path, MusicData> getMusicFiles() {
        return musicFiles;
    }

    public List<Map.Entry<Path, MusicData>> getSortedEntries() {
        return getEntries().stream()
                           .sorted(Comparator.comparing(leftPair -> leftPair.getKey().getFileName()))
                           .collect(Collectors.toList());
    }

    public List<MusicData> getSortedMusics() {
        return getSortedEntries().stream()
                                 .map(Map.Entry::getValue)
                                 .collect(Collectors.toList());
    }

    public Collection<String> getSortedFileNames() {
        return getSortedEntries().stream()
                                 .map(Map.Entry::getKey)
                                 .map(Path::getFileName)
                                 .collect(Collectors.toList());
    }

    public abstract Set<Map.Entry<Path, MusicData>> getEntries();
    public abstract MusicIterator makeIterator(Collection<MusicData> musicDataCollection);
}
