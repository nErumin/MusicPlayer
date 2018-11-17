package io;

public abstract class FilteredDirectoryReader extends DirectoryReader {
    private final DirectoryReader decoratedReader;

    public FilteredDirectoryReader(DirectoryReader decoratedReader) {
        this.decoratedReader = decoratedReader;
    }

    protected DirectoryReader getDecoratedReader() {
        return decoratedReader;
    }
}
