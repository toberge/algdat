package com.toberge.util;

public class Printer {

    public static void out(int[] array) {
        StringBuilder bldr = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) bldr.append(" ");
            bldr.append(array[i]);
        }
        System.out.println(bldr);
    }
}
