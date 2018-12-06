package iterator;

import model.music.iterator.FavoriteMusicIterator;
import model.music.iterator.ForwardDirection;
import model.music.iterator.MusicIterator;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class FavoriteIteratorTest extends IteratorTest {
    @Test
    public void testInitFavoriteIterator() {
        MusicIterator musicIterator = new FavoriteMusicIterator(musics, new ForwardDirection());

        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(1))));
    }

    @Test
    public void testSpecificResetFavoriteIterator() {
        MusicIterator musicIterator = new FavoriteMusicIterator(musics, new ForwardDirection());

        musicIterator.resetFor(musics.get(3));
        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(3))));
    }

    @Test
    public void testResetFailedFavoriteIterator() {
        MusicIterator musicIterator = new FavoriteMusicIterator(musics, new ForwardDirection());

        musicIterator.resetFor(musics.get(2));
        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(1))));
    }

    @Test
    public void testResetFavoriteIterator() {
        MusicIterator musicIterator = new FavoriteMusicIterator(musics, new ForwardDirection());

        musicIterator.resetFor(musics.get(5));
        musicIterator.reset();

        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(1))));
    }

    @Test
    public void testSingleMoveNextFavoriteIterator() {
        MusicIterator musicIterator = new FavoriteMusicIterator(musics, new ForwardDirection());

        musicIterator.moveNext();
        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(3))));
    }

    @Test
    public void testMultipleMoveNextFavoriteIterator() {
        MusicIterator musicIterator = new FavoriteMusicIterator(musics, new ForwardDirection());

        musicIterator.moveNext();
        musicIterator.moveNext();
        musicIterator.moveNext();
        musicIterator.moveNext();

        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(9))));
    }

    @Test
    public void testMoveNextAndResetFavoriteIterator() {
        MusicIterator musicIterator = new FavoriteMusicIterator(musics, new ForwardDirection());

        musicIterator.moveNext();
        musicIterator.moveNext();
        musicIterator.resetFor(musics.get(1));
        musicIterator.moveNext();
        musicIterator.moveNext();

        Assert.assertThat(musicIterator.getCurrentMusicData(), is(equalTo(musics.get(5))));
    }

    @Test
    public void testHasNextNoElementFavoriteIterator() {
        MusicIterator musicIterator = new FavoriteMusicIterator(musics, new ForwardDirection());

        musicIterator.resetFor(musics.get(musics.size() - 1));

        Assert.assertThat(musicIterator.hasNext(), is(false));
    }

    @Test
    public void testHasNextElementFavoriteIterator() {
        MusicIterator musicIterator = new FavoriteMusicIterator(musics, new ForwardDirection());

        musicIterator.resetFor(musics.get(3));

        Assert.assertThat(musicIterator.hasNext(), is(true));
    }
}
