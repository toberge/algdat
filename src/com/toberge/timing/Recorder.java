package com.toberge.timing;

public class Recorder {

    private final int[] n;
    private final double[] ms;
    private int i = 0;

    public Recorder(int[] n) {
        this.n = n;
        ms = new double[n.length];
    }

    public void record(double ms) {
        if (i < this.ms.length) {
            this.ms[i] = ms;
            i++;
        } else {
            System.err.println("Recorder::record() called more than it should be");
        }
    }

    @Override
    public String toString() {

        String NL = System.lineSeparator();
        StringBuilder builder = new StringBuilder("Test results after " + i + " iterations" + NL);
        builder.append("    n    \t\t  avg ms  ");

        for (int j = 0; j < n.length; j++) {
            builder.append(NL);
            builder.append(String.format("%9d", n[j]));
            builder.append("\t\t");
            builder.append(String.format("%10e", ms[j]));
        }

        return builder.toString();
    }

    public String currentResultToString() {
        return "With n=" + String.format("%9d", n[i - 1]) + " the algorithm spent " + String.format("%10e", ms[i - 1]) + " milliseconds";
    }
}
