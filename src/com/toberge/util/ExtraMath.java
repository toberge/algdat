package com.toberge.util;

import java.math.BigInteger;

public class ExtraMath {

    public static int nextPrime(int start) {
        BigInteger big = new BigInteger(String.valueOf(start));
        return Integer.parseInt(big.nextProbablePrime().toString()); // next prime
    }

    public static long nextPrime(long start) {
        BigInteger big = new BigInteger(String.valueOf(start));
        return Long.parseLong(big.nextProbablePrime().toString()); // next prime
    }
}
