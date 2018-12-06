package direction;

import Utility.SampleUtility;
import model.music.MusicData;
import model.music.iterator.FavoriteMusicIterator;
import model.music.iterator.ForwardDirection;
import model.music.iterator.MusicIterator;
import model.music.iterator.NormalMusicIterator;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DirectionTest {
    List<MusicData> musics;

    @Before
    public void setUp() {
        musics = SampleUtility.getMusicSamples();
    }
}
