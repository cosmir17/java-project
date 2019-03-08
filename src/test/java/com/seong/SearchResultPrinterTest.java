package com.seong;

import com.seong.printers.SearchResultPrinter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class SearchResultPrinterTest {
    public static final String LINE = System.getProperty("line.separator");
    private SearchResultPrinter searchResultPrinter;

    @Test
    public void shouldPrintNoMatchesFoundMsgWhenInputIsEmptyMap() throws IOException {
        searchResultPrinter = new SearchResultPrinter(new HashMap<String, Integer>(){});
        searchResultPrinter.print();

        final String printedMsg = searchResultPrinter.getPrintedStr().toString();
        assertThat(printedMsg, is("no matches found" + LINE + "search> "));
    }

    @Test
    public void shouldPrintListFilesAndPercentageAndSearchPrompt() throws IOException {
        Map<String, Integer> testMap = new LinkedHashMap<>();
        testMap.put("file1", 100);
        testMap.put("file2", 30);

        searchResultPrinter = new SearchResultPrinter(testMap);
        searchResultPrinter.print();

        final String printedMsg = searchResultPrinter.getPrintedStr().toString();
        assertThat(printedMsg, is("file1 : 100%" + LINE + "file2 : 30%" + LINE + "search> "));
    }
}
