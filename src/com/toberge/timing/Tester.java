package com.toberge.timing;

import com.toberge.data.DataSet;

import java.util.Date;

/**
 *
 *
 * @author Tore Bergebakken
 * @author Helge Hafting
 */

public class Tester {

    private static final int DEFAULT_MSLIMIT = 1000;
    private static boolean verbose = true;

    public static void setMode(boolean verbose) {
        Tester.verbose = verbose;
    }

    public static double test(Algorithmic algorithm, int msLimit) { // was 1000 in example

        Date start = new Date();
        int rounds = 0;
        Date end;
        do {
            algorithm.execute();
            end = new Date();
            ++rounds;
        } while (end.getTime()-start.getTime() < msLimit);
        double time = (double)
                (end.getTime()-start.getTime()) / rounds;
        if (verbose) System.out.printf("Milliseconds per round: %10e | (%d rounds total)%n", time, rounds);

        return time;

    }

    public static double test(Algorithmic algorithm) {
        return test(algorithm, DEFAULT_MSLIMIT);
    }

    public static double test(Algorithmic algorithm, DataSet data, int msLimit) {

        Date start = new Date();
        int rounds = 0;
        Date end;
        //Date end, genStart, genEnd;
        //long genTime = 0;
        do {
            //genStart = new Date();
            data.recreate();
            //genEnd = new Date();
            //genTime += genEnd.getTime()-genStart.getTime();
            // this probably poses more of a risk so i decided to just run the copying w/o calculating time spent doing so (less that a millisecond)

            algorithm.execute();
            end = new Date();
            ++rounds;
        } while (end.getTime()-start.getTime()/*-genTime*/ < msLimit);
        double time = (double)
                (end.getTime()-start.getTime()/*-genTime*/) / rounds;
        if (verbose) System.out.printf("Milliseconds per round: %10e | (%d rounds total)%n", time, rounds);

        return time;

    }

    public static double test(Algorithmic algorithm, DataSet data) {
        return test(algorithm, data, DEFAULT_MSLIMIT);
    }

    public static Object testRet(ReturningAlgorithm algorithm, int msLimit, Recorder recorder) { // was 1000 in example

        Object result;

        // pulled from teacher
        Date start = new Date();
        int rounds = 0;
        Date end;
        do {
            result = algorithm.execute();
            end = new Date();
            ++rounds;
        } while (end.getTime()-start.getTime() < msLimit);
        double time = (double)
                (end.getTime()-start.getTime()) / rounds;
        if (verbose) System.out.printf("Milliseconds per round: %10e | (%d rounds total)%n", time, rounds);
        recorder.record(time);

        return result;

    }
    public static Object testRet(ReturningAlgorithm algorithm, Recorder recorder) {
        return testRet(algorithm, DEFAULT_MSLIMIT, recorder);
    }


    public static double findOverhead() {
        return findOverhead(DEFAULT_MSLIMIT);
    }

    public static double findOverhead(int msLimit) {
        Date start = new Date();
        int rounds = 0;
        Date end;
        do {
            // nothing
            end = new Date();
            ++rounds;
        } while (end.getTime()-start.getTime() < msLimit);
        double time = (double)
                (end.getTime()-start.getTime()) / rounds;
        if (verbose) System.out.printf("Milliseconds per round: %10e | (%d rounds total)%n", time, rounds);

        return time;
    }
}
