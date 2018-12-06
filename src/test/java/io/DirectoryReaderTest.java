package io;

import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.junit.Test;
import setting.Environment;
import utility.IterableUtility;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class DirectoryReaderTest {
    @Test
    public void testNonRecursiveReading() throws IOException {
        DirectoryReader directoryReader = new NonRecursiveDirectoryReader();

        String path = Environment.getInstance().combinePath(".", "sample_musics");
        List<String> files = IterableUtility.toList(directoryReader.getFiles(path));

        Assert.assertThat(FilenameUtils.getName(files.get(0)), is(equalTo("applause3.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(1)), is(equalTo("DropSword.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(2)), is(equalTo("Happy-music.mp3")));
        Assert.assertThat(FilenameUtils.getName(files.get(3)), is(equalTo("Laser.wav")));

        Assert.assertThat(files.size(), is(equalTo(4)));
    }

    @Test
    public void testRecursiveReading() throws IOException {
        DirectoryReader directoryReader = new RecursiveDirectoryReader();

        String path = Environment.getInstance().combinePath(".", "sample_musics");
        List<String> files = IterableUtility.toList(directoryReader.getFiles(path));

        Assert.assertThat(FilenameUtils.getName(files.get(0)), is(equalTo("applause3.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(1)), is(equalTo("DropSword.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(2)), is(equalTo("Happy-music.mp3")));
        Assert.assertThat(FilenameUtils.getName(files.get(3)), is(equalTo("Ding-dong.wav")));

        Assert.assertThat(FilenameUtils.getName(files.get(4)), is(equalTo("Loud_Bang.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(5)), is(equalTo("Roar.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(6)), is(equalTo("Wrong-alert-beep-sound.mp3")));
        Assert.assertThat(FilenameUtils.getName(files.get(7)), is(equalTo("Laser.wav")));

        Assert.assertThat(files.size(), is(equalTo(8)));
    }

    @Test
    public void testNonRecursiveMp3FilteredReading() throws IOException {
        DirectoryReader directoryReader = new FileExtensionFilteredDirectoryReader(
            new NonRecursiveDirectoryReader(), Collections.singletonList("mp3")
        );

        String path = Environment.getInstance().combinePath(".", "sample_musics");
        List<String> files = IterableUtility.toList(directoryReader.getFiles(path));

        Assert.assertThat(FilenameUtils.getName(files.get(0)), is(equalTo("Happy-music.mp3")));

        Assert.assertThat(files.size(), is(equalTo(1)));
    }

    @Test
    public void testNonRecursiveWavFilteredReading() throws IOException {
        DirectoryReader directoryReader = new FileExtensionFilteredDirectoryReader(
            new NonRecursiveDirectoryReader(), Collections.singletonList("wav")
        );

        String path = Environment.getInstance().combinePath(".", "sample_musics");
        List<String> files = IterableUtility.toList(directoryReader.getFiles(path));

        Assert.assertThat(FilenameUtils.getName(files.get(0)), is(equalTo("applause3.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(1)), is(equalTo("DropSword.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(2)), is(equalTo("Laser.wav")));

        Assert.assertThat(files.size(), is(equalTo(3)));
    }

    @Test
    public void testNonRecursiveFullFilteredReading() throws IOException {
        DirectoryReader directoryReader = new FileExtensionFilteredDirectoryReader(
            new NonRecursiveDirectoryReader(), Arrays.asList("mp3", "wav")
        );

        String path = Environment.getInstance().combinePath(".", "sample_musics");
        List<String> files = IterableUtility.toList(directoryReader.getFiles(path));

        Assert.assertThat(FilenameUtils.getName(files.get(0)), is(equalTo("applause3.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(1)), is(equalTo("DropSword.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(2)), is(equalTo("Happy-music.mp3")));
        Assert.assertThat(FilenameUtils.getName(files.get(3)), is(equalTo("Laser.wav")));

        Assert.assertThat(files.size(), is(equalTo(4)));
    }


    @Test
    public void testRecursiveMp3FilteredReading() throws IOException {
        DirectoryReader directoryReader = new FileExtensionFilteredDirectoryReader(
            new RecursiveDirectoryReader(), Collections.singletonList("mp3")
        );

        String path = Environment.getInstance().combinePath(".", "sample_musics");
        List<String> files = IterableUtility.toList(directoryReader.getFiles(path));

        Assert.assertThat(FilenameUtils.getName(files.get(0)), is(equalTo("Happy-music.mp3")));
        Assert.assertThat(FilenameUtils.getName(files.get(1)), is(equalTo("Wrong-alert-beep-sound.mp3")));

        Assert.assertThat(files.size(), is(equalTo(2)));
    }


    @Test
    public void testRecursiveWavFilteredReading() throws IOException {
        DirectoryReader directoryReader = new FileExtensionFilteredDirectoryReader(
            new RecursiveDirectoryReader(), Collections.singletonList("wav")
        );

        String path = Environment.getInstance().combinePath(".", "sample_musics");
        List<String> files = IterableUtility.toList(directoryReader.getFiles(path));

        Assert.assertThat(FilenameUtils.getName(files.get(0)), is(equalTo("applause3.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(1)), is(equalTo("DropSword.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(2)), is(equalTo("Ding-dong.wav")));

        Assert.assertThat(FilenameUtils.getName(files.get(3)), is(equalTo("Loud_Bang.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(4)), is(equalTo("Roar.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(5)), is(equalTo("Laser.wav")));

        Assert.assertThat(files.size(), is(equalTo(6)));
    }


    @Test
    public void testRecursiveFullFilteredReading() throws IOException {
        DirectoryReader directoryReader = new FileExtensionFilteredDirectoryReader(
            new RecursiveDirectoryReader(), Arrays.asList("mp3", "wav")
        );

        String path = Environment.getInstance().combinePath(".", "sample_musics");
        List<String> files = IterableUtility.toList(directoryReader.getFiles(path));

        Assert.assertThat(FilenameUtils.getName(files.get(0)), is(equalTo("applause3.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(1)), is(equalTo("DropSword.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(2)), is(equalTo("Happy-music.mp3")));
        Assert.assertThat(FilenameUtils.getName(files.get(3)), is(equalTo("Ding-dong.wav")));

        Assert.assertThat(FilenameUtils.getName(files.get(4)), is(equalTo("Loud_Bang.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(5)), is(equalTo("Roar.wav")));
        Assert.assertThat(FilenameUtils.getName(files.get(6)), is(equalTo("Wrong-alert-beep-sound.mp3")));
        Assert.assertThat(FilenameUtils.getName(files.get(7)), is(equalTo("Laser.wav")));

        Assert.assertThat(files.size(), is(equalTo(8)));
    }
}
