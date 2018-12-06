package iterator;

import model.music.iterator.ForwardDirection;
import model.music.iterator.MusicIterator;
import model.music.iterator.RecentPlayedMusicIterator;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class RecentPlayedIteratorTest extends IteratorTest {
    @Test
    public void testInitRecentPlayedIterator() {
        MusicIterator musicIterator = new RecentPlayedMusicIterator(musics, new ForwardDirection());

        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(2))));
    }

    @Test
    public void testSpecificResetRecentPlayedIterator() {
        MusicIterator musicIterator = new RecentPlayedMusicIterator(musics, new ForwardDirection());

        musicIterator.resetFor(musics.get(11));
        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(11))));
    }

    @Test
    public void testResetFailedRecentPlayedIterator() {
        MusicIterator musicIterator = new RecentPlayedMusicIterator(musics, new ForwardDirection());

        musicIterator.resetFor(musics.get(4));
        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(2))));
    }

    @Test
    public void testResetRecentPlayedIterator() {
        MusicIterator musicIterator = new RecentPlayedMusicIterator(musics, new ForwardDirection());

        musicIterator.resetFor(musics.get(17));
        musicIterator.reset();

        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(2))));
    }

    @Test
    public void testSingleMoveNextRecentPlayedIterator() {
        MusicIterator musicIterator = new RecentPlayedMusicIterator(musics, new ForwardDirection());

        musicIterator.moveNext();
        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(5))));
    }

    @Test
    public void testMultipleMoveNextRecentPlayedIterator() {
        MusicIterator musicIterator = new RecentPlayedMusicIterator(musics, new ForwardDirection());

        musicIterator.moveNext();
        musicIterator.moveNext();
        musicIterator.moveNext();
        musicIterator.moveNext();

        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(14))));
    }

    @Test
    public void testMoveNextAndResetRecentPlayedIterator() {
        MusicIterator musicIterator = new RecentPlayedMusicIterator(musics, new ForwardDirection());

        musicIterator.moveNext();
        musicIterator.moveNext();
        musicIterator.resetFor(musics.get(2));
        musicIterator.moveNext();
        musicIterator.moveNext();

        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(8))));
    }

    @Test
    public void testHasNextNoElementRecentPlayedIterator() {
        MusicIterator musicIterator = new RecentPlayedMusicIterator(musics, new ForwardDirection());

        musicIterator.resetFor(musics.get(musics.size() - 2));

        Assert.assertThat(musicIterator.hasNext(), is(false));
    }

    @Test
    public void testHasNextElementRecentPlayedIterator() {
        MusicIterator musicIterator = new RecentPlayedMusicIterator(musics, new ForwardDirection());

        musicIterator.resetFor(musics.get(5));

        Assert.assertThat(musicIterator.hasNext(), is(true));
    }
}
