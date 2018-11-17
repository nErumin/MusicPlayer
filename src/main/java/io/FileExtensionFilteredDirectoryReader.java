package io;

import org.apache.commons.io.FilenameUtils;
import utility.IterableUtility;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FileExtensionFilteredDirectoryReader extends FilteredDirectoryReader {
    private List<String> filterExtensions;

    public FileExtensionFilteredDirectoryReader(DirectoryReader decoratedReader, Iterable<String> filterExtensions) {
        super(decoratedReader);

        this.filterExtensions = IterableUtility.toList(filterExtensions);
    }

    @Override
    public Iterable<String> getFiles(String directoryPath) throws IOException {
        DirectoryReader decoratedReader = getDecoratedReader();

        Iterable<String> files = decoratedReader.getFiles(directoryPath);
        return IterableUtility.toList(files)
                              .stream()
                              .filter(file -> filterExtensions.contains(FilenameUtils.getExtension(file)))
                              .collect(Collectors.toList());
    }
}
