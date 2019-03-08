package com.seong;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;


@RunWith(MockitoJUnitRunner.class)
public class MatchingPercentageCalculatorTest {
    public static final String TEST_FOLDER_PATH = "src/test/resources/files";
    private MatchingPercentCalculator matchingPercentCalculator;

    private List<FileSearchData> fileSearchDataList;

    @Before
    public void setUp() {
        fileSearchDataList = new FolderManipulator(TEST_FOLDER_PATH).getFiles();
    }

    @Test
    public void shouldProduceFilePathAndPercentageMap100ForSingleWord() throws IOException {
        String inputStr = "I";
        matchingPercentCalculator = new MatchingPercentCalculator(inputStr, fileSearchDataList);

        Map<String, Integer> stringIntegerMap = matchingPercentCalculator.computeMap();

        assertThat(stringIntegerMap.size(), is(4));
        assertThat(stringIntegerMap, hasEntry(is(TEST_FOLDER_PATH + "/file1.txt"), is(100)));
        assertThat(stringIntegerMap, hasEntry(is(TEST_FOLDER_PATH + "/file2.txt"), is(100)));
        assertThat(stringIntegerMap, hasEntry(is(TEST_FOLDER_PATH + "/file3"), is(100)));
        assertThat(stringIntegerMap, hasEntry(is(TEST_FOLDER_PATH + "/file3.txt"), is(100)));
    }

    @Test
    public void shouldProduceFilePathAndPercentageMapNoneForSingleWord() throws IOException {
        String inputStr = "mobilephone";
        matchingPercentCalculator = new MatchingPercentCalculator(inputStr, fileSearchDataList);

        Map<String, Integer> stringIntegerMap = matchingPercentCalculator.computeMap();
        assertThat(stringIntegerMap.size(), is(0));
    }

    @Test
    public void shouldProduceFilePathAndPercentageMapIcecreamForSingleWord() throws IOException {
        String inputStr = "icecream";
        matchingPercentCalculator = new MatchingPercentCalculator(inputStr, fileSearchDataList);

        Map<String, Integer> stringIntegerMap = matchingPercentCalculator.computeMap();
        assertThat(stringIntegerMap.size(), is(1));
        assertThat(stringIntegerMap, hasEntry(is(TEST_FOLDER_PATH + "/file3"), is(100)));
    }

    @Test
    public void shouldProduceFilePathAndPercentageMapFourFilesForSentence() throws IOException {
        String inputStr = "I love speakers";
        matchingPercentCalculator = new MatchingPercentCalculator(inputStr, fileSearchDataList);

        Map<String, Integer> stringIntegerMap = matchingPercentCalculator.computeMap();

        assertThat(stringIntegerMap.size(), is(4));
        assertThat(stringIntegerMap, hasEntry(is(TEST_FOLDER_PATH + "/file1.txt"), is(33)));
        assertThat(stringIntegerMap, hasEntry(is(TEST_FOLDER_PATH + "/file2.txt"), is(67)));
        assertThat(stringIntegerMap, hasEntry(is(TEST_FOLDER_PATH + "/file3"), is(67)));
        assertThat(stringIntegerMap, hasEntry(is(TEST_FOLDER_PATH + "/file3.txt"), is(67)));
    }

    @Test
    public void shouldProduceFilePathAndPercentageMapThreeFilesForSentence() throws IOException {
        String inputStr = "love speakers";
        matchingPercentCalculator = new MatchingPercentCalculator(inputStr, fileSearchDataList);

        Map<String, Integer> stringIntegerMap = matchingPercentCalculator.computeMap();

        assertThat(stringIntegerMap.size(), is(3));
        assertThat(stringIntegerMap, hasEntry(is(TEST_FOLDER_PATH + "/file2.txt"), is(50)));
        assertThat(stringIntegerMap, hasEntry(is(TEST_FOLDER_PATH + "/file3"), is(50)));
        assertThat(stringIntegerMap, hasEntry(is(TEST_FOLDER_PATH + "/file3.txt"), is(50)));
    }

    @Test
    public void shouldProduceFilePathAndPercentageMapNoneForSentence() throws IOException {
        String inputStr = "station road";
        matchingPercentCalculator = new MatchingPercentCalculator(inputStr, fileSearchDataList);
        Map<String, Integer> stringIntegerMap = matchingPercentCalculator.computeMap();

        assertThat(stringIntegerMap.size(), is(0));
    }
}