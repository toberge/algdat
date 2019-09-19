import com.toberge.timing.Result;

import java.util.Date;

public class RunMe {

    public static void main(String[] args) {

        Kursforandring toTest = new Kursforandring(new int[] {-1,3,-9,2,2,-1,2,-1,-5});

        toTest.execute();
        System.out.println(toTest.resultToString());

        int iterations = 6;
        int base = 10000;
        int factor = 2;

        Result result = new Result(iterations);

        for (int i = 0, n = base; i < iterations ; i++, n *= factor) { // n=one million makes it go on for like, uh, 600 seconds, way too long
            toTest = new Kursforandring(n);

            Date start = new Date();
            toTest.execute();
            Date end = new Date();

            result.record(n, start, end);

            System.out.println(toTest.resultToString());
            System.out.println(result.currentResultToString());
        }

        System.out.println(result);

    }
}
