package direction;

import model.music.iterator.*;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;

public class ForwardDirectionTest extends DirectionTest {
    @Test
    public void testBasicForwardNextPosition() {
        Direction direction = new ForwardDirection();
        int nextPosition = direction.nextPosition(musics, 1);

        Assert.assertThat(nextPosition, is(equalTo(2)));
    }

    @Test
    public void testLastForwardNextPosition() {
        Direction direction = new ForwardDirection();
        int nextPosition = direction.nextPosition(musics, musics.size() - 1);

        Assert.assertThat(nextPosition, is(equalTo(musics.size())));
    }

    @Test
    public void testNegativeForwardNextPosition() {
        Direction direction = new ForwardDirection();
        int nextPosition = direction.nextPosition(musics, -1);

        Assert.assertThat(nextPosition, is(equalTo(0)));
    }

    @Test
    public void testApplyNormalIteratorTest() {
        MusicIterator normalIterator = new NormalMusicIterator(musics, new ForwardDirection());

        normalIterator.resetFor(musics.get(3));
        normalIterator.setIteratorDirection(new ForwardDirection());
        normalIterator.moveNext();

        Assert.assertThat(normalIterator.getCurrentMusicData(), is(equalTo(musics.get(4))));
    }

    @Test
    public void testApplyFavoriteIteratorTest() {
        MusicIterator favoriteIterator = new FavoriteMusicIterator(musics, new ForwardDirection());

        favoriteIterator.resetFor(musics.get(7));
        favoriteIterator.setIteratorDirection(new ForwardDirection());
        favoriteIterator.moveNext();

        Assert.assertThat(favoriteIterator.getCurrentMusicData(), is(equalTo(musics.get(9))));
    }

    @Test
    public void testApplyRecentIteratorTest() {
        MusicIterator recentPlayedIterator = new RecentPlayedMusicIterator(musics, new ForwardDirection());

        recentPlayedIterator.resetFor(musics.get(5));
        recentPlayedIterator.setIteratorDirection(new ForwardDirection());
        recentPlayedIterator.moveNext();

        Assert.assertThat(recentPlayedIterator.getCurrentMusicData(), is(equalTo(musics.get(8))));
    }
}
