package io;

import setting.Environment;
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
                String subdirectoryPath = Environment.getInstance().combinePath(directoryPath, file.getName());

                final List<String> subdirectoryFiles = IterableUtility.toList(
                    getFiles(subdirectoryPath)
                );

                files.addAll(subdirectoryFiles);
            } else {
                files.add(file.getPath());
            }
        }

        return files;
    }
}
