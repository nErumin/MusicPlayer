package environment;

import org.junit.Assert;
import org.junit.Test;
import setting.Environment;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

// Do not run this class's test with any other tests.
// It will fail because of the Environment is implemented through a singleton.

/**
 * Do not run this class's tests with any other tests.
 * It will fail because of the Environment is implemented through a singleton.
 */
public class EnvironmentTest {
    @Test
    public void testLinuxEnvironment() {
        System.setProperty("os.name", "Linux");

        String path =
            Environment.getInstance().combinePath("folder", "a.txt");

        Assert.assertThat(path, is(equalTo("folder/a.txt")));
    }

    @Test
    public void testMacOsEnvironment() {
        System.setProperty("os.name", "MacOs");

        String path =
            Environment.getInstance().combinePath("folder", "a.txt");

        Assert.assertThat(path, is(equalTo("folder/a.txt")));
    }

    @Test
    public void testWindowsEnvironment() {
        System.setProperty("os.name", "Windows");

        String path =
            Environment.getInstance().combinePath("folder", "a.txt");

        Assert.assertThat(path, is(equalTo("folder\\a.txt")));
    }

    @Test
    public void testUnknownEnvironment() {
        System.setProperty("os.name", "Solaris");

        String path =
            Environment.getInstance().combinePath("folder", "a.txt");

        Assert.assertThat(path, is(equalTo("folder/a.txt")));
    }
}
