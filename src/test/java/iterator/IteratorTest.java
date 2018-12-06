package iterator;

import Utility.SampleUtility;
import model.music.MusicData;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public abstract class IteratorTest {
    List<MusicData> musics = new ArrayList<>();

    @Before
    public void setUp() {
        musics = SampleUtility.getMusicSamples();
    }
}
