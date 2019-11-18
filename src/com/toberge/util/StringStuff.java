package com.toberge.util;

public class StringStuff {

    public static final String[] fields = new String[10];

    /**
     * From Helge Hafting himself, our lord and saviour
     */
    public static void split(String string, int fieldCount) {
        int position = 0;
        int length = string.length();
        for (int i = 0; i < fieldCount; i++) {
            while (string.charAt(position) <= ' ') ++position; // skip whitespace, < space is also whitespace
            int startPos = position;
            while (position < length && string.charAt(position) > ' ') ++position; // then skip all content
            fields[i] = string.substring(startPos, position); // finding end index.
        }
    }
}
