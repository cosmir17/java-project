package com.seong.printers;

import java.util.Collections;
import java.util.Map;

public class SearchResultPrinter extends AbstractPrinter {
    private final Map<String, Integer> filePathPercentageMap;
    private StringBuffer printedStr;

    public SearchResultPrinter(Map<String, Integer> filePathPercentageMap) {
        this.filePathPercentageMap = Collections.unmodifiableMap(filePathPercentageMap);
        this.printedStr = new StringBuffer();
    }

    public void print() {
        if (filePathPercentageMap.isEmpty()) {
            printedStr
                    .append(NO_MATCHES_FOUND)
                    .append(LINE)
                    .append(PREFIXED_WORD);
        }

        else {
            filePathPercentageMap.forEach((key, value) -> {
                printedStr.append(key).append(" : ");
                printedStr.append(value).append("%");
                printedStr.append(LINE);
            });

            printedStr.append(PREFIXED_WORD);
        }

        System.out.print(printedStr.toString());
    }

    public StringBuffer getPrintedStr() {
        return printedStr;
    }
}
