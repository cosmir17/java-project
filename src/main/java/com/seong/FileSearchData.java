package com.seong;

import java.io.File;
import java.util.List;

public class FileSearchData {
    private File file;
    private List<String> lines;

    public FileSearchData(File file, List<String> lines) {
        this.file = file;
        this.lines = lines;
    }

    public File getFile() {
        return file;
    }

    public List<String> getLines() {
        return lines;
    }
}
