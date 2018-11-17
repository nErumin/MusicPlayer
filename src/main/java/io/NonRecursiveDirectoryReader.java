package io;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class NonRecursiveDirectoryReader extends DirectoryReader {
    @Override
    public Iterable<String> getFiles(String directoryPath) throws IOException {
        if (directoryPath == null) {
            throw new NullPointerException("The provided path is null.");
        }

        final File folder = makeFolder(directoryPath);

        return Arrays.stream(folder.listFiles())
                     .filter(file -> file.isFile() && file.canRead())
                     .map(File::getPath)
                     .collect(Collectors.toList());
    }
}
