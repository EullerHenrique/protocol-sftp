package com.euller.sftp.utils;

public class CSVUtils {

    public static String stringArrayToCsvLine(String[] array) {
        return String.join(";",array).concat("\n");
    }
}
