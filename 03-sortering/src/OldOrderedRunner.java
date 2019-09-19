import com.toberge.data.IntegerArray;
import com.toberge.timing.Recorder;
import com.toberge.timing.Tester;
import com.toberge.util.ArrayFactory;

@Deprecated
public class OldOrderedRunner {

    public static void main(String[] args) {
//        int[] arr = new int[]{8, 5, 0, 3, 4, 0};
        int[] arr = new int[]{6, 5, 4, 3, 2, 1};
        arr = ArrayFactory.generateIntegerArray(100, 1000);
        QuicksortedArray.quicksortByTheBook(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        if (QuicksortedArray.isSorted(arr)) System.out.println("SORTED CORRECTLY");
        arr = ArrayFactory.generateIntegerArray(100, 1000);
        QuicksortedArray.quicksortOptimizedForSameyness(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        if (QuicksortedArray.isSorted(arr)) System.out.println("SORTED CORRECTLY");



        int[] sizes = new int[]{1000, 10000, 100000};

        Recorder book = new Recorder(sizes);
        Recorder samey = new Recorder(sizes);
        Recorder java = new Recorder(sizes);



        for (int n : sizes) {
            // TODO remake the test method so that it actually copies arrays, you dumbass
            // THIS IS KINDA FREAKING IMPORTANT
        }
    }
}

@Deprecated
class Heck {
    public static void main(String[] args) {
        IntegerArray data = new IntegerArray(100, 30);
        Tester.test(() -> {
            int tall = data.getArray()[0];
            System.out.println("before increment: " + tall);
            data.getArray()[0]++;
            System.out.println("after increment: " + tall);
            data.recreate();
            System.out.println("before sorting: " + data.getArray()[0]);
            QuicksortedArray.quicksortByTheBook(data.getArray());
            System.out.println("after sorting: " + data.getArray()[0]);
        } , data);
        System.out.println("Timing recreation---");
        Tester.test(() -> {
            data.recreate();
        });
        // konklusjon: fuck lambda expressions
        // WAIT NOPE: they are good. it works. you just remembered THE ARRAY IS COPIED IN recreate()
    }
}