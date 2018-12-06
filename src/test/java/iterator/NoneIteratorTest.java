package iterator;

import model.music.iterator.ForwardDirection;
import model.music.iterator.MusicIterator;
import model.music.iterator.NoneMusicIterator;
import model.music.iterator.NormalMusicIterator;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;

public class NoneIteratorTest extends IteratorTest {
    @Test
    public void testInitNoneIterator() {
        MusicIterator musicIterator = new NoneMusicIterator();

        Assert.assertThat(musicIterator.getCurrentMusicData(), is(nullValue()));
    }

    @Test
    public void testSpecificResetNoneIterator() {
        MusicIterator musicIterator = new NoneMusicIterator();

        musicIterator.resetFor(musics.get(5));
        Assert.assertThat(musicIterator.getCurrentMusicData(), is(nullValue()));
    }

    @Test
    public void testResetNoneIterator() {
        MusicIterator musicIterator = new NoneMusicIterator();

        musicIterator.resetFor(musics.get(5));
        musicIterator.reset();

        Assert.assertThat(musicIterator.getCurrentMusicData(), is(nullValue()));
    }

    @Test
    public void testSingleMoveNextNoneIterator() {
        MusicIterator musicIterator = new NoneMusicIterator();

        musicIterator.moveNext();
        Assert.assertThat(musicIterator.getCurrentMusicData(), is(nullValue()));
    }

    @Test
    public void testMultipleMoveNextNoneIterator() {
        MusicIterator musicIterator = new NoneMusicIterator();

        musicIterator.moveNext();
        musicIterator.moveNext();
        musicIterator.moveNext();
        musicIterator.moveNext();

        Assert.assertThat(musicIterator.getCurrentMusicData(), is(nullValue()));
    }

    @Test
    public void testMoveNextAndResetNoneIterator() {
        MusicIterator musicIterator = new NoneMusicIterator();

        musicIterator.moveNext();
        musicIterator.moveNext();
        musicIterator.resetFor(musics.get(1));
        musicIterator.moveNext();
        musicIterator.moveNext();

        Assert.assertThat(musicIterator.getCurrentMusicData(), is(nullValue()));
    }

    @Test
    public void testHasNextNoElementNoneIterator() {
        MusicIterator musicIterator = new NoneMusicIterator();

        musicIterator.resetFor(musics.get(musics.size() - 1));

        Assert.assertThat(musicIterator.hasNext(), is(false));
    }

    @Test
    public void testHasNextElementNoneIterator() {
        MusicIterator musicIterator = new NoneMusicIterator();

        musicIterator.resetFor(musics.get(10));

        Assert.assertThat(musicIterator.hasNext(), is(false));
    }
}
