package com.toberge.util;

import java.util.Arrays;
import java.util.Random;

public class ArrayFactory {

    public static int[] generateIntegerArray(int n, int range) {
        return generateIntegerArray(n, range, true);
    }


    public static int[] generateIntegerArray(int n, int range, boolean includeNegative) {
        int[] toFill = new int[n];
        for (int i = 0; i < n; i++) {
            toFill[i] =  (new Random()).nextInt(range) - (includeNegative? range / 2 : 0);
        }
        return toFill;
    }

    public static int[] generateNonzeroIntegerArray(int n, int range) {
        int[] toFill = new int[n];
        for (int i = 0; i < n; i++) {
            toFill[i] = (new Random()).nextInt(range) + 1;
        }
        if (Arrays.binarySearch(toFill, 0) > -1) {
            System.err.println("WE FUCKED UP");
            return null;
        }
        return toFill;
    }
}
