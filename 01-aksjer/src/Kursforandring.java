import com.toberge.timing.Algorithmic;
import com.toberge.data.IntegerArray;

public class Kursforandring extends IntegerArray implements Algorithmic {

    // only made to be members for the purpose of printing the result *after* the calculation
    private int profit;
    private int buy;
    private int sell;

    public Kursforandring(int n) {
        super(n, 30, true);
    }

    public Kursforandring(int[] array) {
        super(array);
    }

    @Override
    public void execute() {

        // this should be of complexity O(n^2) which is kinda bad
        // also, this should have its parameters passed into its method, but atm I've done it like this.

        int n = getArray().length; // .length is supposedly less efficient (but it is a final field among the array's members anyway... somehow)
        int[] accumulated = new int[n];
        int total = 0;

        // registering accumulated cost/price at buy/sale
        for (int i = 0; i < n; i++) {
            total += getArray()[i];
            accumulated[i] = total;
        }

        // find highest difference
        profit = 0;
        buy = -1;
        sell = -1;
        for (int i = 0; i < n; i++) { // buy first
            for (int j = i; j < n; j++) { // sell later
                if (accumulated[j] - accumulated[i] > profit) {
                    profit = accumulated[j] - accumulated[i];
                    buy = i;
                    sell = j;
                }
            }
        }
    }

    public String resultToString() {
        return "Solution: buy at " + (buy+1) + " sell at " + (sell+1) + " for a profit of " + profit;
    }

    /*
    public void execute() {
        int total = 0;
        int lowPos = -1;
        int highPos = -1;
        int[][] possible = new int[getArray().length][2];
        int[] accumulated = new int[getArray().length];

        for (int i = 0; i < getArray().length; i++) {
            total += getArray()[i];
            accumulated[i] = total;

        }
    }
    */

    /*private int bsearch(int search, int[] arr, int l, int r) {
        int mid = arr.length / 2;
        if (search == arr[mid]) return mid;
        else if(r - l == 1) return -1;
        else {
            if (search < arr[mid]) {
                return bsearch(search, arr, l, mid);
            } else {
                return bsearch(search, arr, mid + 1, r);
            }
        }
    }*/
}
