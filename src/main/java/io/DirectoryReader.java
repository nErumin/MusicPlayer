package io;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;

public abstract class DirectoryReader {
    public abstract Iterable<String> getFiles(String directoryPath) throws IOException;

    protected File makeFolder(String directoryPath) throws IOException {
        final File folder = new File(directoryPath);

        if (folder.isDirectory() == false) {
            throw new NotDirectoryException("The provided path is not a directory.");
        }

        return folder;
    }
}
