package iterator;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsEqual.equalTo;

import model.music.iterator.*;
import org.junit.Assert;
import org.junit.Test;

public class NormalIteratorTest extends IteratorTest {
    @Test
    public void testInitNormalIterator() {
        MusicIterator musicIterator = new NormalMusicIterator(musics, new ForwardDirection());

        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(0))));
    }

    @Test
    public void testSpecificResetNormalIterator() {
        MusicIterator musicIterator = new NormalMusicIterator(musics, new ForwardDirection());

        musicIterator.resetFor(musics.get(5));
        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(5))));
    }

    @Test
    public void testResetNormalIterator() {
        MusicIterator musicIterator = new NormalMusicIterator(musics, new ForwardDirection());

        musicIterator.resetFor(musics.get(5));
        musicIterator.reset();

        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(0))));
    }

    @Test
    public void testSingleMoveNextNormalIterator() {
        MusicIterator musicIterator = new NormalMusicIterator(musics, new ForwardDirection());

        musicIterator.moveNext();
        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(1))));
    }

    @Test
    public void testMultipleMoveNextNormalIterator() {
        MusicIterator musicIterator = new NormalMusicIterator(musics, new ForwardDirection());

        musicIterator.moveNext();
        musicIterator.moveNext();
        musicIterator.moveNext();
        musicIterator.moveNext();

        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(4))));
    }

    @Test
    public void testMoveNextAndResetNormalIterator() {
        MusicIterator musicIterator = new NormalMusicIterator(musics, new ForwardDirection());

        musicIterator.moveNext();
        musicIterator.moveNext();
        musicIterator.resetFor(musics.get(1));
        musicIterator.moveNext();
        musicIterator.moveNext();

        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(3))));
    }

    @Test
    public void testHasNextNoElementNormalIterator() {
        MusicIterator musicIterator = new NormalMusicIterator(musics, new ForwardDirection());

        musicIterator.resetFor(musics.get(musics.size() - 1));

        Assert.assertThat(musicIterator.hasNext(), is(false));
    }

    @Test
    public void testHasNextElementNormalIterator() {
        MusicIterator musicIterator = new NormalMusicIterator(musics, new ForwardDirection());

        musicIterator.resetFor(musics.get(10));

        Assert.assertThat(musicIterator.hasNext(), is(true));
    }
}
