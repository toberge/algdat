import com.toberge.data.IntegerArray;
import java.util.Date;

/**
 * QuicksortedArray.java
 */
public class QuicksortedArray extends IntegerArray {

    // ----------------------- SECTION OF POSSIBLE REGRET -----------------------

    public enum Algo {
        BOOK, SAMEY
    }

    private Algo algo;

    public QuicksortedArray(int n, String algo) {
        super(n, n / 2);
        this.algo = Algo.valueOf(algo);
    }
    public QuicksortedArray(int n, Algo algo) {
        super(n, n / 2);
        this.algo = algo;
    }

    public void sort() {
        switch (algo) {
            case BOOK:
                quicksortByTheBook(getArray());
                break;
            case SAMEY:
                quicksortOptimizedForSameyness(getArray());
                break;
            default:
                throw new IllegalStateException("Should have an algo");
        }
    }

    public double timing() {
        Date start = new Date();
        int rounds = 0;
        Date end, genStart, genEnd;
        long genTime = 0;
        do {
            //genStart = new Date();
            recreate();
            //genEnd = new Date();
            //genTime += genEnd.getTime()-genStart.getTime();
            // TODO this probably breaks things

            this.sort();
            end = new Date();
            ++rounds;
        } while (end.getTime()-start.getTime()-genTime < 6000);
        double time = (double)
                (end.getTime()-start.getTime()-genTime) / rounds;
        System.out.printf("Milliseconds per round: %10e | (%d rounds total)%n", time, rounds);

        return time;
    }



    // ----------------------- OPPGAVE 3 -----------------------

    public static void quicksortOptimizedForSameyness(int[] arr) {
        quicksortOptimizedForSameyness(arr, 0, arr.length - 1);
    }

    public static void quicksortOptimizedForSameyness(int[] arr, int left, int right) {
        // SO: thing is, when pivoty numbers on each side of a sub-array are equal,
        // all elements of the sub-array *must themselves be equal*
        // because of the way we find, swap and place the pivots.
        if(left > 0 && right < arr.length - 1 && arr[left-1] == arr[right+1]) return;
        // FOUND BUG must be       lenght - 1    we check against -> todo tell teacher
        if (right - left > 2) {
            int pivot = split(arr, left, right); // O(n), pivot INDEX
            quicksortOptimizedForSameyness(arr, left, pivot - 1);
            quicksortOptimizedForSameyness(arr, pivot + 1, right);
        } else median3sort(arr, left, right); // O(1)
    }

    // ----------------------- QUICKSORT AS IMPLEMENTED IN THE BOOK -----------------------

    public static void quicksortByTheBook(int[] arr) {
        quicksortByTheBook(arr, 0, arr.length - 1);
    }

    public static void quicksortByTheBook(int[] arr, int left, int right) {
        if (right - left > 2) {
            int pivot = split(arr, left, right); // O(n), pivot INDEX
            quicksortByTheBook(arr, left, pivot - 1);
            quicksortByTheBook(arr, pivot + 1, right);
        } else median3sort(arr, left, right); // O(1)
    }

    private static int median3sort(int[] arr, int left, int right) {
        int mid = (left + right) / 2;
        if (arr[left] > arr[mid]) swap(arr, left, mid); // we go from left
        if (arr[mid] > arr[right]) {
            swap(arr, mid, right);
            if (arr[left] > arr[mid]) swap(arr, left, mid);
        }
        return mid;
    }

    private static int split(int[] arr, int left, int right) {
        int moveInFromLeft, moveInFromRight;
        int mid = median3sort(arr, left, right);
        int pivot = arr[mid]; // pivot VALUE
        swap(arr, mid, right - 1); // to the right of here we have a median3sort-ed value
        for (moveInFromLeft = left, moveInFromRight = right - 1;;) {
            while (arr[++moveInFromLeft] < pivot) // ++var gives var+1 while var++ gives var *before* incrementing.
                //closing in from the left (safe since rightmost value is >= pivot)
                ;
            while (arr[--moveInFromRight] > pivot)
                // closing in from the right (safe since leftmost value is <= pivot)
                ;
            if (moveInFromLeft >= moveInFromRight) break; // if the indexes passed each other
            swap(arr, moveInFromLeft, moveInFromRight); // if they didn't pass, swap the values (TODo whY?)
        }
        swap(arr, moveInFromLeft, right - 1); // put pivot in its correct position
        return moveInFromLeft; // return pos of pivot
    }

    // ----------------------- UTILITY METHODS -----------------------

    /**
     * Since I don't wanna bother write those three lines all the damn time
     */
    private static void swap(int[] arr, int from, int to) {
        int temp = arr[from];
        arr[from] = arr[to];
        arr[to] = temp;
    }

    public static boolean isSorted(int[] arr) {
        /*int[] test = Arrays.copyOf(arr, arr.length);
        Arrays.sort(test);
        return Arrays.equals(test, arr);*/
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i+1] < arr[i]) return false;
        }
        return true;
    }

    public static long findChecksum(int[] arr) {
        int checksum = 0;
        for (int value : arr) {
            checksum += value;
        }
        return checksum;
    }

    public static boolean isIntact(int[] arr1, int[] arr2) {
        return findChecksum(arr1) == findChecksum(arr2);
    }
}
