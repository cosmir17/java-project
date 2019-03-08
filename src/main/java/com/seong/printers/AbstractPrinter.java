package com.seong.printers;

public abstract class AbstractPrinter {
    public static final String LINE = System.getProperty("line.separator");
    protected static final String NO_MATCHES_FOUND = "no matches found";
    protected static String PREFIXED_WORD = "search> ";

    public abstract void print();
}
