package model;

import org.apache.commons.io.FilenameUtils;

public class Path {
    private String fullPath;

    public Path(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getFullPath() {
        return fullPath;
    }

    public String getBaseFileName() {
        return FilenameUtils.getBaseName(getFullPath());
    }

    public String getFileName() {
        return FilenameUtils.getName(getFullPath());
    }

    public String getExtension() {
        return FilenameUtils.getExtension(getFullPath());
    }
}
