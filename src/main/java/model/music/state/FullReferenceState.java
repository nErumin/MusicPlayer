package model.music.state;

import model.Path;
import model.music.MusicData;
import model.music.iterator.ForwardDirection;
import model.music.iterator.MusicIterator;
import model.music.iterator.NormalMusicIterator;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FullReferenceState extends ListReferenceState {
    public FullReferenceState(Map<Path, MusicData> musicFiles) {
        super(musicFiles);
    }

    @Override
    public Collection<String> getSortedFileNames() {
        return getEntries().stream()
                           .map(Map.Entry::getKey)
                           .map(Path::getFileName)
                           .sorted()
                           .collect(Collectors.toList());
    }

    @Override
    public Set<Map.Entry<Path, MusicData>> getEntries() {
        return getMusicFiles().entrySet();
    }

    @Override
    public MusicIterator makeIterator(Collection<MusicData> musicDataCollection) {
        return new NormalMusicIterator(musicDataCollection, new ForwardDirection());
    }
}
