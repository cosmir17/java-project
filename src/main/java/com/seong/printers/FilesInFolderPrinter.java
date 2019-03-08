package com.seong.printers;

public class FilesInFolderPrinter extends AbstractPrinter {
    private final int fileCount;
    private final String filePath;

    public FilesInFolderPrinter(int fileCount, String filePath) {
        this.fileCount = fileCount;
        this.filePath = filePath;
    }

    @Override
    public void print() {
        StringBuffer sb = new StringBuffer();
        sb.append(fileCount)
                .append(" file(s) read in directory ")
                .append(filePath)
                .append(LINE)
                .append(PREFIXED_WORD);

        System.out.print(sb.toString());
    }
}
