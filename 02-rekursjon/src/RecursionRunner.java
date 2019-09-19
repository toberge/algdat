import com.toberge.timing.Recorder;
import com.toberge.timing.Tester;

public class RecursionRunner {

    public static void main(String[] args) {

        final double TOLERANCE = 0.0000001;

        final int[] exponents = new int[]{10, 100, 1000, 10000};
        final double x = -1.05;

        final Recorder firstRecorder = new Recorder(exponents);
        final Recorder secondRecorder = new Recorder(exponents);
        final Recorder theirRecorder = new Recorder(exponents);

        double firstRes, secondRes, theirRes;

        for (int n : exponents) {

            // TODO the test method should not need to return anything else than the avg time spent...
            //      Since the time the operations take is so short anyway,
            //      the result can simply be computed and checked **elsewhere**
            // -->  it's done now.
            /*Double firstRes = (Double) Tester.testRet(() -> Power.simplePow(x, n), firstRecorder);
            Double secondRes = (Double) Tester.testRet(() -> Power.splitPow(x, n), secondRecorder);
            Double theirRes = (Double) Tester.testRet(() -> Math.pow(x, n), theirRecorder);*/
            firstRecorder.record(Tester.test(() -> Power.simplePow(x, n)));
            secondRecorder.record(Tester.test(() -> Power.splitPow(x, n)));
            theirRecorder.record(Tester.test(() -> Math.pow(x, n)));

            firstRes = Power.simplePow(x, n);
            secondRes = Power.splitPow(x, n);
            theirRes = Math.pow(x, n);

            if (Math.abs(firstRes - theirRes) < TOLERANCE &&
                    Math.abs(secondRes - theirRes) < TOLERANCE) {
                System.out.println("All is well");
            } else {
                System.err.println("Potential problem: ");
                System.err.printf("First\t%e%n", firstRes);
                System.err.printf("Second\t%e%n", secondRes);
                System.err.printf("Their\t%e%n", theirRes);
                System.out.println("(there might not even be a problem, however)");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println();
        System.out.println("Linear recursive pow():");
        System.out.println(firstRecorder);
        System.out.println();
        System.out.println("Better recursive pow():");
        System.out.println(secondRecorder);
        System.out.println();
        System.out.println("Math.pow():");
        System.out.println(theirRecorder);

    }
}
