package com.seong;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FolderManipulator {

  private static final IllegalArgumentException DIRECTORY_PATH_IS_INVALID = new IllegalArgumentException("directory path is invalid");
  private static final IllegalArgumentException NO_FILES_IN_THE_DIRECTORY = new IllegalArgumentException("no files exist in the directory, therefore program terminates");
  public final String filePath;

  public FolderManipulator(String filePath) {
    this.filePath = filePath;
  }

  public List<FileSearchData> getFiles() {
    File folder = new File(filePath);
    File[] files = folder.listFiles();

    if (!folder.exists()) {
      throw DIRECTORY_PATH_IS_INVALID;
    }

    if(files.length == 0) {
      throw NO_FILES_IN_THE_DIRECTORY;
    }

    return Arrays.stream(files)
      .filter(file -> !file.isDirectory())
      .map(file -> {
        try {
          return new FileSearchData(
                  file,
                  Files.readAllLines(file.toPath())
          );
        } catch (IOException e) {
          throw DIRECTORY_PATH_IS_INVALID;
        }
      })
      .collect(Collectors.toList());
  }

}