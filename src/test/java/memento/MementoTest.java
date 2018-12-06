package memento;

import io.DirectoryReader;
import io.RecursiveDirectoryReader;
import model.music.MusicData;
import model.music.MusicPlayer;
import model.music.MusicProxy;
import model.music.PlayerMemento;
import model.music.iterator.ForwardDirection;
import model.music.iterator.MusicIterator;
import model.music.iterator.NormalMusicIterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setting.Environment;
import utility.IterableUtility;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class MementoTest {
    private List<MusicData> musics;
    private MusicPlayer musicPlayer;

    @Before
    public void setUp() throws IOException, LineUnavailableException {
        DirectoryReader directoryReader = new RecursiveDirectoryReader();

        String path = Environment.getInstance().combinePath(".", "sample_musics");
        musics = IterableUtility.toList(directoryReader.getFiles(path))
                                .stream()
                                .map(MusicProxy::new)
                                .collect(Collectors.toList());

        musicPlayer = new MusicPlayer();
    }

    @Test
    public void testMementoCreating() throws InterruptedException {
        MusicIterator musicIterator = new NormalMusicIterator(musics, new ForwardDirection());
        musicIterator.resetFor(musics.get(2));

        musicPlayer.setIterationMode(musicIterator);
        musicPlayer.startPlay();

        musicPlayer.setVolumeRatio(0.4f);
        Thread.sleep(2500);

        PlayerMemento playerMemento = musicPlayer.createMemento();
        musicPlayer.stopPlay();

        Assert.assertThat(playerMemento.getVolumeRatio(), is(equalTo(0.4f)));
        Assert.assertTrue(playerMemento.getProgressedMicroSeconds() > 1500000);
    }

    @Test
    public void testRecoverVolumeFromMemento() throws InterruptedException {
        MusicIterator musicIterator = new NormalMusicIterator(musics, new ForwardDirection());
        musicIterator.resetFor(musics.get(2));

        musicPlayer.setIterationMode(musicIterator);
        musicPlayer.startPlay();

        musicPlayer.setVolumeRatio(0.6f);
        PlayerMemento playerMemento = musicPlayer.createMemento();

        musicPlayer.setVolumeRatio(0.7f);
        Thread.sleep(2500);

        musicPlayer.setVolumeRatio(0.8f);

        musicPlayer.recoverVolumeState(playerMemento);
        Assert.assertThat(musicPlayer.getVolumeRatio(), is(equalTo(0.6f)));

        musicPlayer.stopPlay();
    }

    @Test
    public void testRecoverProgressFromMemento() throws InterruptedException {
        MusicIterator musicIterator = new NormalMusicIterator(musics, new ForwardDirection());
        musicIterator.resetFor(musics.get(2));

        musicPlayer.setIterationMode(musicIterator);
        musicPlayer.startPlay();

        Thread.sleep(1500);
        PlayerMemento playerMemento = musicPlayer.createMemento();
        Thread.sleep(1000);

        musicPlayer.recoverPlayState(playerMemento);

        Assert.assertTrue(musicPlayer.getPlayingClip().getMicrosecondPosition() < 2000000);

        musicPlayer.stopPlay();
    }
}
