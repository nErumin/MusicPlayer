package proxy;

import io.DirectoryReader;
import io.RecursiveDirectoryReader;
import model.music.MusicData;
import model.music.MusicProxy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setting.Environment;
import utility.IterableUtility;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class ProxyTest {
    private List<String> files;

    @Before
    public void setUp() throws IOException {
        DirectoryReader directoryReader = new RecursiveDirectoryReader();

        String path = Environment.getInstance().combinePath(".", "sample_musics");
        files = IterableUtility.toList(directoryReader.getFiles(path));
    }

    @Test(timeout = 400)
    public void testResourceNotLoaded() {
        List<MusicData> musics = files.stream()
                                      .map(MusicProxy::new)
                                      .collect(Collectors.toList());
    }

    @Test(timeout = 400)
    public void testFavoriteProxying() {
        List<MusicData> musics = files.stream()
            .map(MusicProxy::new)
            .collect(Collectors.toList());


        for (MusicData music : musics) {
            music.setFavorite(true);

            music.isFavorite();
        }
    }

    @Test(timeout = 400)
    public void testPlayedDateProxying() {
        List<MusicData> musics = files.stream()
            .map(MusicProxy::new)
            .collect(Collectors.toList());


        for (MusicData music : musics) {
            music.setRecentPlayedDate(new Date(0));

            music.getRecentPlayedDate();
        }
    }

    @Test
    public void testTitleProxying() {
        List<MusicData> musics = files.stream()
            .map(MusicProxy::new)
            .collect(Collectors.toList());

        Assert.assertThat(musics.get(2).getTitle(), is(equalTo("Happy-music")));
    }
}
