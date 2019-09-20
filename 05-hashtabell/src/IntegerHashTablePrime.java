import com.toberge.util.ExtraMath;

public class IntegerHashTablePrime {

    private final int[] array;
    private int n = 0;
    private int collisions = 0;
    private final int m;

    public IntegerHashTablePrime(int capacity) {
        m = ExtraMath.nextPrime(capacity + capacity / 4); // neste prim etter 20-25% mer enn nødvendig kapasitet
        array = new int[m];
    }

    public int add(int key) {
        int index = hashValue(key);
        if (array[index] == 0) { // using 0 because then we don't need to initialize with -1 or Integer.MIN_VALUE or something
            array[index] = key;
            n++;
            return index;
        } else { // collision
            int jumpInterval = hashInterval(key); // needing jump value
            collisions++;
            for (int i = 0; i < m; i++) {
                if (array[index] == 0) { // found spot
                    array[index] = key;
                    n++;
                    return index;
                } else { // else move on using second hash function
                    index = (index + jumpInterval) % m; // modulo to make sure we stay within limits and don't exceed Integer.MAX_VALUE
                }
            }
            System.out.println("WTF IT DED");
            return -1;
        }
    }

    // standard modulovariant
    private int hashValue(int key) {
        return key % m; // the Integer class's hashCode() simply returns the integer, I presume doing so myself should be fine
    }

    // aldri 0 pga +1, innbyrdes primisk med m, forskjellige resultater fra h1
    private int hashInterval(int key) {
        return key % (m-1) + 1;
    }

    public double getAverageCollisions() {
        return (double)collisions / n;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public int getCollisions() {
        return collisions;
    }

    public double getFillRate() {
        return (double)(n - collisions) / m;
    }
}
