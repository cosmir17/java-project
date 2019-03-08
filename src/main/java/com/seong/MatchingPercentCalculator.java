package com.seong;

import java.util.*;

public class MatchingPercentCalculator {
    private final String inputStr;
    private final List<FileSearchData> fileSearchDataList;

    /**
     * @param inputStr           I, am, a, person
     * @param fileSearchDataList file1, file2, file3, file4
     */
    public MatchingPercentCalculator(String inputStr, List<FileSearchData> fileSearchDataList) {
        this.inputStr = inputStr;
        this.fileSearchDataList = Collections.unmodifiableList(fileSearchDataList);
    }

    public Map<String, Integer> computeMap() {
        List<String> wordList = Arrays.asList(inputStr.split("\\s+"));
        Map<String, Integer> map = new LinkedHashMap<>();

        for (FileSearchData fileData : fileSearchDataList) {
            final String filePath = fileData.getFile().getPath();
            StringBuilder sb = new StringBuilder();
            fileData.getLines().forEach(sb::append);
            final String appendedStr = sb.toString().toLowerCase();

            long wordOccurenceForOneFile = wordList.stream()
                    .map(String::toLowerCase)
                    .filter(appendedStr::contains)
                    .count();
            if (wordOccurenceForOneFile == 0) {
                continue;
            }

            int size = wordList.size();

            int percentage = (int) Math.round((double) wordOccurenceForOneFile / size * 100);
            map.put(filePath, percentage);
        }

        return map;
    }

}
