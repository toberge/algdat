import com.toberge.util.Printer;

@Deprecated
public class BadSort {

    /**
     * Mergesort:
     *
     * &Theta;(n&middot;log(n))
     *
     * @param arr
     */
    public static void mergeSort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int middle = (right + left) / 2; // NOT right - left
                System.out.println("Called with " + left + " | " + right);
                Printer.out(arr);
            mergeSort(arr, left, middle);
                System.out.println("Returned with " + left + " | " + right);
                Printer.out(arr);
            mergeSort(arr, middle + 1, right);
                System.out.println("About to merge " + left + " | " + right);
                Printer.out(arr);
            merge(arr, left, middle, right);
        }
    }

    // TODO fix some sort of weird bug that is present here...
    private static void merge(int[] arr, int left, int middle, int right) {
        int[] helper = new int[right - left + 1]; // +1 cuz length not index
        int i = 0, leftCounter = left, rightCounter = middle + 1;
        // starting from beginning and after middle
        // the sub-arrays we merge *are sorted already OR of length 1*
        // that's why we can just pick the lowest number from the current indexes
        while (leftCounter <= middle && rightCounter <= right) {
            helper[i++] = (arr[leftCounter] <= arr[rightCounter]) ?
                           arr[leftCounter++] : arr[rightCounter++];
        }
        // then fill in the rest *from the left sub-array only*
        // because the numbers to the right should already be in their correct position if left untouched
        while (leftCounter <= middle) helper[i++] = arr[leftCounter++];
        for (i = left; i < right; ++i) arr[i] = helper[i - left]; // check if right should be replaced
    }
}
