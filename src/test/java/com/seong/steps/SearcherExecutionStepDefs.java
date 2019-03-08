package com.seong.steps;

import com.seong.SimpleSearcherApp;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class SearcherExecutionStepDefs {
    private static final String LINE = System.getProperty("line.separator");
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    public static final String TEST_FILES_LOCATION = "src/test/resources/files";
    private String keyword;

    @Given("^there are files containing '(I|no matching keyword)'$")
    public void thereAreFilesContainingArg(String arg) throws Throwable {
        //there are already files
    }

    @When("^the application runs and user types '(.*)'$")
    public void userTypesKeyword(String keyword) throws Throwable {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        this.keyword = keyword;
        final String data = keyword + "\r\n";
        ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
        System.setIn(in);
        SimpleSearcherApp.main(new String[]{TEST_FILES_LOCATION});
    }

    @Then("^files name list should be displayed with a matching percentage$")
    public void filesNameListShouldBeDisplayedWithAMatchingPercentage() throws Throwable {

        System.setOut(null);
        System.setErr(null);
    }

    //keyward is not saved in stream thus, can't be tested with the typed keyword
    @Then("^no matches found should be printed$")
    public void noMatchesFoundShouldBePrinted() throws Throwable {
        StringBuffer sb = new StringBuffer();
        sb
                .append("4 file(s) read in directory " + TEST_FILES_LOCATION + LINE + "search> " )
                .append("no matches found" + LINE + "search> ");
        assertEquals(sb.toString(), outContent.toString());

        System.setOut(null);
        System.setErr(null);
    }
}
