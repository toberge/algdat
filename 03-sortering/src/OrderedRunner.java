import com.toberge.data.IntegerArray;
import com.toberge.timing.Recorder;
import com.toberge.timing.Tester;

import java.util.Scanner;

public class OrderedRunner {

    private static void testAll() {
        QuicksortedArray.Algo[] algos = QuicksortedArray.Algo.values();

        for (QuicksortedArray.Algo algo: algos) {
            QuicksortedArray arr = new QuicksortedArray(1000000, algo);
            int[] before = arr.getArray();
            arr.sort();
            System.out.println(algo.name() + " " +
                    (QuicksortedArray.isSorted(arr.getArray()) &&
                            QuicksortedArray.isIntact(before, arr.getArray())
                            ? "succeeded"
                            : "failed"));
            // sortere samme igjen, sjekke med opprinnelig array
            arr.sort();
            System.out.println(algo.name() + " " +
                    (QuicksortedArray.isSorted(arr.getArray()) &&
                            QuicksortedArray.isIntact(before, arr.getArray())
                            ? "succeeded twice"
                            : "**FAILED**"));
        }
    }

    private static void measureAll() {
        int[] sizes = new int[]{1000, 10000, 100000, 1000000};
        QuicksortedArray.Algo[] algos = QuicksortedArray.Algo.values();
        Recorder[] recorders = new Recorder[algos.length];

        for (int i = 0; i < recorders.length; i++) {
            recorders[i] = new Recorder(sizes);
        }

        for (int i = 0; i < algos.length; i++) {
            for (int n : sizes) {
                System.out.println(algos[i].name() + " with n=" + n);
                recorders[i].record(new QuicksortedArray(n, algos[i]).timing());
                recorders[i].currentResultToString();
            }
        }

        for (Recorder recorder : recorders) {
            System.out.println();
            System.out.println(recorder.toString());
        }
    }

    private static void oppgave3() {
        System.out.println("----- OPPGAVE 3 -----");

//        int[] sizes = new int[]{10000, 20000, 40000, 80000, 160000};
        int[] sizes = new int[]{10000, 100000, 1000000};
        Recorder normal;
        Recorder optimSamey;

        for (int range :
                new int[]{1, /*2, 3, 4, 5,*/ 6, 10, 100, 1000, 1000000}) { // same, samey, kinda samey, diverse
            System.out.println();
            System.out.println("------- WITH RANGE " + range + " -------");

            normal = new Recorder(sizes);
            optimSamey = new Recorder(sizes);

            for (int n : sizes) {
                IntegerArray data = new IntegerArray(n, range, false);
//                System.out.print("Normal: ");
                normal.record(Tester.test(() -> QuicksortedArray.quicksortByTheBook(data.getArray()), data));
//                System.out.print("Samey: ");
                optimSamey.record(Tester.test(() -> QuicksortedArray.quicksortOptimizedForSameyness(data.getArray()), data));
            }

            System.out.println();
            System.out.println("Normal: ");
            System.out.println(normal);
            System.out.println();
            System.out.println("Samey: ");
            System.out.println(optimSamey);
        }



    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("0 - Test all algorithms");
        System.out.println("-1 - Measure all algorithms");
        System.out.println("3 - Oppgave 3: Optimizing for likeness");
        System.out.print("Pick your poision: ");
        String line = scanner.nextLine();
        int choice = Integer.parseInt(line);
        // ignoring errors
        switch (choice) {
            case 0:
                testAll();
                break;
            case -1:
                measureAll();
                break;
            case 3:
                oppgave3();
                break;
            default:
                System.err.println("No valid option");
                System.exit(1);
        }

    }
}

/*
WITH SIMPLY SUBTRACTED TIME:
Test results after 3 iterations
    n    		  avg ms
     1000		3.269007e-02
    10000		6.439841e-01
   100000		8.063087e+00

Test results after 3 iterations
    n    		  avg ms
     1000		3.240213e-02
    10000		7.121662e-01
   100000		8.952310e+00
WITH RECREATION LEFT ALONE:
Test results after 3 iterations
    n    		  avg ms
     1000		2.870113e-02
    10000		6.473889e-01
   100000		8.172789e+00

Test results after 3 iterations
    n    		  avg ms
     1000		3.323971e-02
    10000		7.411067e-01
   100000		9.113809e+00
CONCLUSION: methinks difference is not large enough to facilitate caring about this awfully much.
            get on with solving the actual task now.


DIFFERENT TEST, DIFFERENT COMPUTER:
(worse CPU, better ram&disk)
Normal:
Test results after 5 iterations
    n    		  avg ms
    10000		2.689618e-01
    20000		5.549390e-01
    40000		1.118568e+00
    80000		2.242152e+00
   160000		4.882927e+00

Samey:
Test results after 5 iterations
    n    		  avg ms
    10000		2.094680e-01
    20000		4.353505e-01
    40000		8.517888e-01
    80000		1.636661e+00
   160000		3.448276e+00
W/O CARING FOR TIME SPENT COPYING:
Normal:
Test results after 5 iterations
    n    		  avg ms
    10000		2.677376e-01
    20000		5.583473e-01
    40000		1.152074e+00
    80000		2.283105e+00
   160000		4.761905e+00

Samey:
Test results after 5 iterations
    n    		  avg ms
    10000		2.111486e-01
    20000		4.340278e-01
    40000		8.688097e-01
    80000		1.618123e+00
   160000		3.479167e+00

CONCLUSION: I hold it more likely that the low granularity of Date::getTime()
will make it problematic to just subtract the approximate time spent copying
- especially since the test I just did to measure copy time clocked in at 6.922744e-05 ms
 */