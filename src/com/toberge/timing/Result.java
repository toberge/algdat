package com.toberge.timing;

import java.util.Date;

@Deprecated
public class Result {

    private int[] n;
    private long[] ms;
    private int i = 0;

    /*public Result(int n, Date start, Date end) {
        this.n = n;
        ms = end.getTime()-start.getTime();
    }*/

    public Result(int iterations) {
        n = new int[iterations];
        ms = new long[iterations];
    }

    public void record(int n, Date start, Date end) {
        this.n[i] = n;
        ms[i] = end.getTime()-start.getTime();
        i++;
    }

    public void record(int n, long ms) {
        this.n[i] = n;
        this.ms[i] = ms;
        i++;
    }

    @Override
    public String toString() {

        String NL = System.lineSeparator();
        StringBuilder builder = new StringBuilder("Test results after " + i + " iterations" + NL);
        builder.append("    n    \t\t  ms  ");

        for (int j = 0; j < i; j++) {
            builder.append(NL);
            builder.append(String.format("%9d", n[j]));
            builder.append("\t\t");
            builder.append(String.format("%6d", ms[j]));
        }

        return builder.toString();
    }

    public String currentResultToString() {
        return "With n=" + String.format("%9d", n[i - 1]) + " the algorithm spent " + String.format("%6d", ms[i - 1]) + " milliseconds";
    }
}
