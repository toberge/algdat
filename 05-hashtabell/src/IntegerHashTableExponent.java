public class IntegerHashTableExponent {

    private final int[] array;
    private int n = 0;
    private int collisions = 0;
    private final int m;
    private final static int A = 1327217885;
    private final byte x; // exponent

    public IntegerHashTableExponent(int capacity) {
        capacity += capacity / 4;
        int nextPowOfTwo = 1;
        byte exponent = 0;
        while (nextPowOfTwo < capacity) // while less than intended lower limit of array length
        {
            nextPowOfTwo = nextPowOfTwo << 1; // do a left shift, leaving a trail of 0s
            exponent++; // counting up exponent
        }
        //System.out.println(nextPowOfTwo + " " + Math.log(nextPowOfTwo) / Math.log(2) + " " + exponent);
        m = nextPowOfTwo;
        //x = (byte) (Math.log(nextPowOfTwo) / Math.log(2)); // exponent should not exceed 127
        x = exponent;
        array = new int[m]; // 25% mer
    }

    public int add(int key) {
        int index = hashValue(key);
        if (array[index] == 0) {
            array[index] = key;
            n++;
            return index;
        } else { // collision
            //System.out.println("COLLISION");
            collisions++;
            for (int i = 0; i < m; i++) {
                if (array[index] == 0) { // found spot
                    array[index] = key;
                    n++;
                    return index;
                } else { // else move on using second hash function
                    index = (index + hashInterval(key)) % m; // modulo to make sure we stay within limits and don't exceed Integer.MAX_VALUE
                }
            }
            System.out.println("WTF IT DIED");
            return -1;
        }
    }

    private int hashValue(int key) {
        return (int) (key*A >>> (31-x)) & (m - 1); // multiplikativ hashfunksjon med heltallsmultiplikasjon, kjapp versjon fra forelesningen
    }

    private int hashInterval(int key) {
        return (2 * key /*Math.abs(key)*/ + 1) % m; // always returns uneven numbers
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
