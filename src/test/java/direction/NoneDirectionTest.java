package direction;

import model.music.iterator.*;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class NoneDirectionTest extends DirectionTest {
    @Test
    public void testBasicNoneNextPosition() {
        Direction direction = new NoneDirection();
        int nextPosition = direction.nextPosition(musics, 1);

        Assert.assertThat(nextPosition, is(equalTo(-1)));
    }

    @Test
    public void testLastNoneNextPosition() {
        Direction direction = new NoneDirection();
        int nextPosition = direction.nextPosition(musics, musics.size() - 1);

        Assert.assertThat(nextPosition, is(equalTo(-1)));
    }

    @Test
    public void testNegativeNoneNextPosition() {
        Direction direction = new NoneDirection();
        int nextPosition = direction.nextPosition(musics, -111);

        Assert.assertThat(nextPosition, is(equalTo(-1)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testApplyNormalIteratorTest() {
        MusicIterator normalIterator = new NormalMusicIterator(musics, new ForwardDirection());

        normalIterator.resetFor(musics.get(3));
        normalIterator.setIteratorDirection(new NoneDirection());
        normalIterator.moveNext();

        Assert.assertThat(normalIterator.getCurrentMusicData(), is(equalTo(musics.get(4))));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testApplyFavoriteIteratorTest() {
        MusicIterator favoriteIterator = new FavoriteMusicIterator(musics, new ForwardDirection());

        favoriteIterator.resetFor(musics.get(7));
        favoriteIterator.setIteratorDirection(new NoneDirection());
        favoriteIterator.moveNext();

        Assert.assertThat(favoriteIterator.getCurrentMusicData(), is(equalTo(musics.get(9))));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testApplyRecentIteratorTest() {
        MusicIterator recentPlayedIterator = new RecentPlayedMusicIterator(musics, new ForwardDirection());

        recentPlayedIterator.resetFor(musics.get(5));
        recentPlayedIterator.setIteratorDirection(new NoneDirection());
        recentPlayedIterator.moveNext();

        Assert.assertThat(recentPlayedIterator.getCurrentMusicData(), is(equalTo(musics.get(5))));
    }
}
