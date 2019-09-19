import com.toberge.timing.Tester;
import com.toberge.util.ArrayFactory;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class HashRunner {

    private static IntegerHashTablePrime integerHashTablePrime;
    private static IntegerHashTableExponent integerHashTableExponent;
    private static HashMap<Integer, Integer> hashMap;

    public static void main(String[] args) {
        LinkedList<String> strings = new LinkedList<>();
        try (FileReader fr = new FileReader(new File("./05-hashtabell/src/navn")); // project root is working directory
             BufferedReader br = new BufferedReader(fr)) {
            while (br.ready()) { // FUCK YOU it's not hasNext() or something
                strings.add(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Read " + strings.size() + " strings");
        StringHashTable table = new StringHashTable(strings.size());

        for (String string : strings) {
            table.add(string);
        }

        System.out.println("n=" + table.getN()  + ", m=" + table.getM());
        System.out.println("Lastfaktor " + ((double)table.getN() / table.getM()));
        System.out.println("Collisions: " + table.getCollisions());
        System.out.println("Avg collisions per name: " + table.getAverageCollisions());
        System.out.println("Fill rate: " + table.getFillRate());
        System.out.println("Contains Åge Lunderud (nope): " + table.contains("Lunderud,Åge"));
        System.out.println("Contains Jon: " + table.contains("Bergquist,Jon Åby"));
        System.out.println("Contains me: " + table.contains("Bergebakken,Tore"));

        System.out.println();
        System.out.println("--------- INTEGERS x 5 million using 2^x ---------");
        System.out.println("     aka excessive capacity that somehow works    ");
        System.out.println();
        final int NUMBER = 5000000;
        final int MS_LIMIT = 10000;

        int[] array = ArrayFactory.generateNonzeroIntegerArray(NUMBER, 900000000); // vast range of numberkind


        Tester.setMode(true);
        double expMS = Tester.test(() -> {
            HashRunner.integerHashTableExponent = new IntegerHashTableExponent(NUMBER);
            for (int key : array) {
                integerHashTableExponent.add(key);
            }
        }, MS_LIMIT);

        System.out.println("n=" + integerHashTableExponent.getN()  + ", m=" + integerHashTableExponent.getM());
        System.out.println("Lastfaktor " + ((double) integerHashTableExponent.getN() / integerHashTableExponent.getM()));
        System.out.println("Collisions: " + integerHashTableExponent.getCollisions());
        System.out.println("Avg collisions per int: " + integerHashTableExponent.getAverageCollisions());
        System.out.println("Fill rate: " + integerHashTableExponent.getFillRate());

        System.out.println("--------- INTEGERS x 5 million using PRIME ---------");
        System.out.println("     aka just 20% more than 5M but somehow worse    ");

        double primeMS = Tester.test(() -> {
            HashRunner.integerHashTablePrime = new IntegerHashTablePrime(NUMBER);
            for (int key : array) {
                integerHashTablePrime.add(key);
            }
        }, MS_LIMIT);

        System.out.println("n=" + integerHashTablePrime.getN()  + ", m=" + integerHashTablePrime.getM());
        System.out.println("Lastfaktor " + ((double)integerHashTablePrime.getN() / integerHashTablePrime.getM()));
        System.out.println("Collisions: " + integerHashTablePrime.getCollisions());
        System.out.println("Avg collisions per int: " + integerHashTablePrime.getAverageCollisions());
        System.out.println("Fill rate: " + integerHashTablePrime.getFillRate());

        System.out.println("--------- INTEGERS x 5 million using HashMap ---------");

        double hashMapMS = Tester.test(() -> {
            HashRunner.hashMap = new HashMap<>(NUMBER + NUMBER / 5); // 20% more than required
            for (int key : array) {
                hashMap.put(key, key);
            }
        }, MS_LIMIT);

        System.out.println("Size: " + hashMap.size());

        double[] contestants = new double[]{hashMapMS, expMS, primeMS};
        Arrays.sort(contestants);
        System.out.println("Winner spent " + contestants[2] + " ms");

    }
}
