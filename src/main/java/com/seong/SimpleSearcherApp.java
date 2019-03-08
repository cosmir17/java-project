package com.seong;

import com.seong.printers.FilesInFolderPrinter;
import com.seong.printers.SearchResultPrinter;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SimpleSearcherApp {
    private static String PREFIXED_WORD = "search> ";

    public static void main(String[] args) throws InterruptedException {
        if(args.length == 0) {
            throw new IllegalArgumentException("directory path is not supplied");
        }

        final boolean testMode;
        if (args.length>1) {
           testMode = args[1].equals("testmode");
        } else {
            testMode = false;
        }

        if (args.length > 1 && !testMode) {
            throw new IllegalArgumentException("too many argument(s) are provided");
        }

        FolderManipulator folderManipulator = new FolderManipulator(args[0]);
        List<FileSearchData> files = folderManipulator.getFiles();

        new FilesInFolderPrinter(files.size(), folderManipulator.filePath).print();

        if (testMode) {
            Thread.sleep(400);
        }

        Scanner scanner = new Scanner(System.in);
        String inputStr = "";

        while (!testMode && scanner.hasNextLine()) {
            inputStr = scanner.nextLine();

            if(inputStr.equals(":quit")){
                break;
            }

            if(inputStr.equals("")){
                System.out.print(PREFIXED_WORD);
                continue;
            }

            Map<String, Integer> stringIntegerMap = new MatchingPercentCalculator(inputStr, files).computeMap();
            new SearchResultPrinter(stringIntegerMap).print();
        }
    }
}
