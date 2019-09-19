import com.toberge.timing.Algorithmic;
import com.toberge.data.IntegerArray;

public class WorseStocks extends IntegerArray implements Algorithmic {

    // only made to be members for the purpose of printing the result *after* the calculation
    private int profit;
    private int buy;
    private int sell;

    public WorseStocks(int n) {
        super(n, 30, true);
    }

    public WorseStocks(int[] array) {
        super(array);
    }

    @Override
    public void execute() {

        // this should be of complexity O(n^2) which is kinda bad
        // also, this should have its parameters passed into its method, but atm I've done it like this.

        int n = getArray().length; // .length is supposedly less efficient (but it is a final field among the array's members anyway... somehow)
        int[] accumulated = new int[n];
        int total = 0;

        profit = 0;
        buy = -1;
        sell = -1;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) { // sell point
                for (int k = 0; k < j; k++) { // buy point
                    // uhhh wait what did I mean?
                }
            }
        }

    }

    public String resultToString() {
        return "Solution: buy at " + (buy+1) + " sell at " + (sell+1) + " for a profit of " + profit;
    }
}
