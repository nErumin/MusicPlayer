package direction;

import model.music.iterator.*;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class LoopDirectionTest extends DirectionTest {
    @Test
    public void testBasicLoopNextPosition() {
        Direction direction = new LoopDirection();
        int nextPosition = direction.nextPosition(musics, 1);

        Assert.assertThat(nextPosition, is(equalTo(1)));
    }

    @Test
    public void testLastForLoopNextPosition() {
        Direction direction = new LoopDirection();
        int nextPosition = direction.nextPosition(musics, musics.size() - 1);

        Assert.assertThat(nextPosition, is(equalTo(musics.size() - 1)));
    }

    @Test
    public void testNegativeLoopNextPosition() {
        Direction direction = new LoopDirection();
        int nextPosition = direction.nextPosition(musics, -1);

        Assert.assertThat(nextPosition, is(equalTo(-1)));
    }

    @Test
    public void testApplyNormalIteratorTest() {
        MusicIterator normalIterator = new NormalMusicIterator(musics, new ForwardDirection());

        normalIterator.resetFor(musics.get(3));
        normalIterator.setIteratorDirection(new LoopDirection());
        normalIterator.moveNext();

        Assert.assertThat(normalIterator.getCurrentMusicData(), is(equalTo(musics.get(3))));
    }

    @Test
    public void testApplyFavoriteIteratorTest() {
        MusicIterator favoriteIterator = new FavoriteMusicIterator(musics, new ForwardDirection());

        favoriteIterator.resetFor(musics.get(7));
        favoriteIterator.setIteratorDirection(new LoopDirection());
        favoriteIterator.moveNext();

        Assert.assertThat(favoriteIterator.getCurrentMusicData(), is(equalTo(musics.get(7))));
    }

    @Test
    public void testApplyRecentIteratorTest() {
        MusicIterator recentPlayedIterator = new RecentPlayedMusicIterator(musics, new ForwardDirection());

        recentPlayedIterator.resetFor(musics.get(5));
        recentPlayedIterator.setIteratorDirection(new LoopDirection());
        recentPlayedIterator.moveNext();

        Assert.assertThat(recentPlayedIterator.getCurrentMusicData(), is(equalTo(musics.get(5))));
    }
}
