package com.toberge.data;

import com.toberge.util.ArrayFactory;

import java.util.Arrays;

public class IntegerArray implements DataSet {

    // storing a backup
    private int[] array, backup;

    private final int n;

    public IntegerArray(int n, int range, boolean includeNegative/*, boolean nonzero*/) {
        if (n < 1) throw new IllegalArgumentException("Invalid n");
        this.n = n;
        //this.nonzero = nonzero;
        array = ArrayFactory.generateIntegerArray(n, range, includeNegative);
        backup = Arrays.copyOf(array, n);
    }

    public IntegerArray(int n, int range) {
        this(n, range, true/*, false*/);
    }
    /*public IntegerArray(int n, int range, boolean includeNegative) {
        this(n, range, includeNegative, false);
    }*/


    public IntegerArray(int[] array) {
        if (array == null || array.length == 0) throw new IllegalArgumentException("Array is empty plz fix");
        this.array = array;
        this.n = array.length;
    }

    public int[] getArray() {
        return array;
    }

    @Override
    public void recreate() {
        array = Arrays.copyOf(backup, n);
    }
}
