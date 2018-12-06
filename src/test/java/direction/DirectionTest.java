package direction;

import utility.SampleUtility;
import model.music.MusicData;
import org.junit.Before;

import java.util.List;

public class DirectionTest {
    List<MusicData> musics;

    @Before
    public void setUp() {
        musics = SampleUtility.getMusicSamples();
    }
}
