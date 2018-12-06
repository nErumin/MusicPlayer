package direction;

import model.music.iterator.*;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

public class RandomDirectionTest extends DirectionTest {
    @Test
    public void testBasicRandomNextPosition() {
        Direction direction = new RandomDirection();
        int nextPosition = direction.nextPosition(musics, 1);

        Assert.assertTrue(nextPosition >= 0);
        Assert.assertTrue(nextPosition < musics.size());
    }

    @Test
    public void testLastRandomNextPosition() {
        Direction direction = new RandomDirection();
        int nextPosition = direction.nextPosition(musics, musics.size() - 1);

        Assert.assertTrue(nextPosition >= 0);
        Assert.assertTrue(nextPosition < musics.size());
    }

    @Test
    public void testNegativeRandomNextPosition() {
        Direction direction = new RandomDirection();
        int nextPosition = direction.nextPosition(musics, -1);

        Assert.assertTrue(nextPosition >= 0);
        Assert.assertTrue(nextPosition < musics.size());
    }

    @Test
    public void testApplyNormalIteratorTest() {
        MusicIterator normalIterator = new NormalMusicIterator(musics, new ForwardDirection());

        normalIterator.resetFor(musics.get(3));
        normalIterator.setIteratorDirection(new RandomDirection());
        normalIterator.moveNext();


        Assert.assertThat(normalIterator.getCurrentMusicData(), is(notNullValue()));
    }

    @Test
    public void testApplyFavoriteIteratorTest() {
        MusicIterator favoriteIterator = new FavoriteMusicIterator(musics, new ForwardDirection());

        favoriteIterator.resetFor(musics.get(7));
        favoriteIterator.setIteratorDirection(new RandomDirection());
        favoriteIterator.moveNext();

        Assert.assertThat(favoriteIterator.getCurrentMusicData(), is(notNullValue()));
    }

    @Test
    public void testApplyRecentIteratorTest() {
        MusicIterator recentPlayedIterator = new RecentPlayedMusicIterator(musics, new ForwardDirection());

        recentPlayedIterator.resetFor(musics.get(5));
        recentPlayedIterator.setIteratorDirection(new RandomDirection());
        recentPlayedIterator.moveNext();

        Assert.assertThat(recentPlayedIterator.getCurrentMusicData(), is(notNullValue()));
    }
}
