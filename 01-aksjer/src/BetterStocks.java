import com.toberge.timing.Algorithmic;
import com.toberge.data.IntegerArray;

public class BetterStocks extends IntegerArray implements Algorithmic {

    private int profit = 0;
    private int buy = -1;
    private int sell = -1;

    public BetterStocks(int n) {
        super(n, 30, true);
    }

    public BetterStocks(int[] array) {
        super(array);
    }

    @Override
    public void execute() {

        /*int[] accumulated = new int[getArray().length];
        int total = 0;

        // registering accumulated cost/price at buy/sale
        for (int i = 0; i < getArray().length; i++) {
            total += getArray()[i];
            accumulated[i] = total;
        }

        buy = 0;
        sell = 0;
        int buyValue = accumulated[0];
        int sellValue = accumulated[0];
        int possible

        for (int i = 0; i < accumulated.length; i++) {
            int currentValue = accumulated[i];
            if (currentValue < buyValue) {
                buyValue = currentValue;
                buy = i;
            } else if (currentValue > sellValue) {
                sellValue = currentValue;
                sell = i;
            }
        }

        for (int i = 0; i < accumulated.length; i++) {
            int currentProfit = accumulated[i];
            if (currentProfit < buy) {

            } else if (currentProfit > sell) {

            }
        }*/

    }

    public String resultToString() {
        return "Solution: buy at " + (buy+1) + " sell at " + (sell+1) + " for a profit of " + profit;
    }
}
