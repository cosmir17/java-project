package com.seong;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

public class FolderManipulatorTest {
    private static final String TEMP_FOLDER_PATH = "src/test/resources/newFolderCreated";
    private FolderManipulator folderManipulator;

    private final static File FILE_1 = new File("src/test/resources/files/file1.txt");
    private final static File FILE_2 = new File("src/test/resources/files/file2.txt");
    private final static File FILE_3 = new File("src/test/resources/files/file3");
    private final static File FILE3_TXT = new File("src/test/resources/files/file3.txt");

    boolean folderCreated = false;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        folderManipulator = new FolderManipulator("src/test/resources/files");
    }

    @After
    public void postAction() {
        deleteFolderIfCreated(folderCreated);
    }

    @Test
    public void shouldProduceFileSearchDataList() {
        List<FileSearchData> files = folderManipulator.getFiles();

        assertThat(files, containsInAnyOrder(
                allOf(hasProperty("file", is(FILE_1))),
                allOf(hasProperty("file", is(FILE_2))),
                allOf(hasProperty("file", is(FILE_3))),
                allOf(hasProperty("file", is(FILE3_TXT)))));
    }

    @Test
    public void shouldThrowExceptionWhenFolderDoesNotExist() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("directory path is invalid"));

        new FolderManipulator("nonExistFolder").getFiles();
    }

    @Test
    public void shouldThrowExceptionWhenFolderIsEmpty() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("no files exist in the directory, therefore program terminates"));

        File dir = new File(TEMP_FOLDER_PATH);
        folderCreated = dir.mkdir();

        if (!folderCreated) {
            fail("can't create an empty directory");
        }

        new FolderManipulator(TEMP_FOLDER_PATH).getFiles();
    }

    private static void deleteFolderIfCreated(boolean folderCreated) {
        File dir = new File(TEMP_FOLDER_PATH);
        if (folderCreated && dir.exists()) {
            dir.delete();
        }
    }
}