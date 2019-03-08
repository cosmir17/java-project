package com.seong.steps;

import com.seong.SimpleSearcherApp;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.concurrent.*;

import static org.junit.Assert.*;

public class AppInitTermStepDefs {
    private static final String LINE = System.getProperty("line.separator");
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    private static final String EMPTY = "empty";
    private static final String INVALID = "an invalid";
    private static final String NO = "no";
    private static final String NEW_TEST_EMPTY_FOLDER = "newTestEmptyFolder";
    private static final String CORRECT_PATH = "a correct path";
    private static final String TEST_FILE_FOLDER = "src/test/resources/files";
    private boolean folderCreated = false;
    private String argumentStr;

    public AppInitTermStepDefs() {
    }

    @When("^the program runs with (empty|an invalid|a correct path) argument$")
    public void specifyingMainAppStrArgument(String cucumberArg) throws Throwable {
        if (cucumberArg.contains(EMPTY)) {
            argumentStr = EMPTY;
        }

        if (cucumberArg.contains(INVALID)) {
            argumentStr = INVALID;
        }

        if (cucumberArg.contains(CORRECT_PATH)) {
            argumentStr = TEST_FILE_FOLDER;
        }
    }

    @Then("^the program terminates with a message '(.*)'$")
    public void theProgramTerminatesWithAMessageDirectoryPathIsNotSupplied(String errorMsg) throws Throwable {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future;

        try {
            if (argumentStr.equals(EMPTY)) {
                future = executor.submit(() -> {
                    SimpleSearcherApp.main(new String[]{});
                    return "";
                });
            } else {
                future = executor.submit(() -> {
                    SimpleSearcherApp.main(new String[]{argumentStr});
                    return argumentStr;
                });
            }
            future.get(500, TimeUnit.MILLISECONDS);
            future.cancel(true);
        } catch (ExecutionException ex) {
            assertTrue(ex.getMessage().contains(errorMsg));
            assertTrue(ex.getMessage().contains("IllegalArgumentException"));
        } catch (TimeoutException e) {
            fail();
        } finally {
            executor.shutdownNow();
            deleteFolderIfCreated(folderCreated);
        }
    }

    @Given("^there are (no files|files) in search directory$")
    public void specifyingSearchDirectory(String cucumberArg) throws Throwable {
        if (cucumberArg.contains(NO)) {
            argumentStr = NEW_TEST_EMPTY_FOLDER;
            File dir = new File(NEW_TEST_EMPTY_FOLDER);

            boolean successful = dir.mkdir();
            if (!successful) {
                fail("can't create an empty directory");
            } else {
                folderCreated = true;
            }
        } else {
            argumentStr = "src/test/resources/files";
        }
    }

    @When("^the program runs and user types ':quit'$")
    public void theProgramRunsAndUserTypesQuit() throws Throwable {
        final String data = ":quit\r\n";
        ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
        System.setIn(in);
        SimpleSearcherApp.main(new String[]{argumentStr});
        System.setOut(null);
        System.setErr(null);
    }

    @Then("^the program prints how many files are there and showing 'search> ' prompt in the next line and the app gets terminated$")
    public void theProgramPrintsHowManyFilesAreThereAndShowingSearchPromptInTheNextLineAndTheAppGetsTerminated() throws Throwable {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        SimpleSearcherApp.main(new String[]{argumentStr, "testmode"});
        assertEquals("4 file(s) read in directory " + TEST_FILE_FOLDER + LINE + "search> ", outContent.toString());
    }

    /**
     * this does not run if the main method doesn't terminates (since it's using Scanner.
     *
     * @throws Throwable
     */
    @Then("^the program terminates$")
    public void theProgramTerminates() throws Throwable {
    }

    private static void deleteFolderIfCreated(boolean folderCreated) {
        File dir = new File(NEW_TEST_EMPTY_FOLDER);
        if (folderCreated && dir.exists()) {
            dir.delete();
        }
    }


}