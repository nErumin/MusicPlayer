package io;

import utility.IterableUtility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecursiveDirectoryReader extends DirectoryReader {

    @Override
    public Iterable<String> getFiles(String directoryPath) throws IOException {
        final File folder = makeFolder(directoryPath);
        final List<String> files = new ArrayList<>();

        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                final List<String> subDirectoryFiles = IterableUtility.toList(
                    getFiles(directoryPath + '/' + file.getName())
                );

                files.addAll(subDirectoryFiles);
            } else {
                files.add(file.getPath());
            }
        }

        return files;
    }
}
