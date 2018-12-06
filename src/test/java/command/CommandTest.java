package command;

import io.DirectoryReader;
import io.RecursiveDirectoryReader;
import model.command.Command;
import model.command.PauseCommand;
import model.command.SkipCommand;
import model.command.VolumeIncreaseCommand;
import model.music.MusicData;
import model.music.MusicPlayer;
import model.music.MusicProxy;
import model.music.iterator.ForwardDirection;
import model.music.iterator.MusicIterator;
import model.music.iterator.NormalMusicIterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setting.Environment;
import utility.IterableUtility;
import utility.MathUtility;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class CommandTest {
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

        musics.get(2).getTitle();
    }

    @Test
    public void testPauseCommandExecuted() throws InterruptedException {
        MusicIterator musicIterator = new NormalMusicIterator(musics, new ForwardDirection());
        musicIterator.resetFor(musics.get(2));

        musicPlayer.setIterationMode(musicIterator);
        musicPlayer.startPlay();
        Thread.sleep(1000);

        Command command = new PauseCommand(musicPlayer);
        command.execute();

        Thread.sleep(1000);
        Assert.assertThat(musicPlayer.isPaused(), is(true));

        musicPlayer.stopPlay();
    }

    @Test
    public void testPauseCommandUndo() throws InterruptedException {
        MusicIterator musicIterator = new NormalMusicIterator(musics, new ForwardDirection());
        musicIterator.resetFor(musics.get(2));

        musicPlayer.setIterationMode(musicIterator);
        musicPlayer.startPlay();
        Thread.sleep(1000);

        Command command = new PauseCommand(musicPlayer);

        command.execute();

        Thread.sleep(1000);
        command.undo();

        Thread.sleep(1000);
        Assert.assertThat(musicPlayer.isPaused(), is(false));

        musicPlayer.stopPlay();
    }

    @Test
    public void testVolumeCommandExecuted() throws InterruptedException {
        MusicIterator musicIterator = new NormalMusicIterator(musics, new ForwardDirection());
        musicIterator.resetFor(musics.get(2));

        musicPlayer.setIterationMode(musicIterator);
        musicPlayer.startPlay();
        Thread.sleep(1000);

        float currentVolumeRatio = 0;

        Command command = new VolumeIncreaseCommand(musicPlayer, 0.3f);
        command.execute();
        Thread.sleep(1000);

        Assert.assertThat(musicPlayer.getVolumeRatio(),
            is(equalTo(MathUtility.clamp(currentVolumeRatio + 0.3f, 0.0f, 1.0f))));

        Thread.sleep(1000);
        musicPlayer.stopPlay();
    }

    @Test
    public void testVolumeCommandUndo() throws InterruptedException {
        MusicIterator musicIterator = new NormalMusicIterator(musics, new ForwardDirection());
        musicIterator.resetFor(musics.get(2));

        musicPlayer.setIterationMode(musicIterator);
        musicPlayer.startPlay();
        Thread.sleep(1000);

        float currentVolumeRatio = musicPlayer.getVolumeRatio();

        Command command = new VolumeIncreaseCommand(musicPlayer, 0.3f);
        command.execute();
        Thread.sleep(1000);
        command.undo();

        Thread.sleep(1000);
        Assert.assertThat(musicPlayer.getVolumeRatio(), is(equalTo(currentVolumeRatio)));

        musicPlayer.stopPlay();
    }

    @Test
    public void testSkipCommandExecuted() throws InterruptedException {
        MusicIterator musicIterator = new NormalMusicIterator(musics, new ForwardDirection());
        musicIterator.resetFor(musics.get(2));

        musicPlayer.setIterationMode(musicIterator);
        musicPlayer.startPlay();
        Thread.sleep(1000);

        long currentMicroPosition = musicPlayer.getPlayingClip().getMicrosecondPosition();

        Command command = new SkipCommand(musicPlayer, 4);
        command.execute();

        Thread.sleep(1000);
        Assert.assertTrue(
            musicPlayer.getPlayingClip().getMicrosecondPosition() >= currentMicroPosition + 4000000
        );

        musicPlayer.stopPlay();
    }

    @Test
    public void testSkipCommandUndo() throws InterruptedException {
        MusicIterator musicIterator = new NormalMusicIterator(musics, new ForwardDirection());
        musicIterator.resetFor(musics.get(2));

        musicPlayer.setIterationMode(musicIterator);
        musicPlayer.startPlay();
        Thread.sleep(1000);

        long currentMicroPosition = musicPlayer.getPlayingClip().getMicrosecondPosition();

        Command command = new SkipCommand(musicPlayer, 4);
        command.execute();

        Thread.sleep(1000);

        command.undo();
        Assert.assertTrue(
            musicPlayer.getPlayingClip().getMicrosecondPosition() < currentMicroPosition + 4000000
        );

        musicPlayer.stopPlay();
    }
}
