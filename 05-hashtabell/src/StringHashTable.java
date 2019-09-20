import com.toberge.data.LinkedList;
import com.toberge.util.ExtraMath;

public class StringHashTable {

    private final LinkedList<String>[] array;
    private final int m;
    private int n = 0;
    private int collisions = 0;

    public StringHashTable(int capacity) {
        m = ExtraMath.nextPrime(capacity);
        array = new LinkedList[m];
    }

    /*
SIMPLY MULTIPLYING BY INDEX
n=96, m=97
Lastfaktor 0.9896907216494846
Collisions: 33
Avg collisions per name: 0.34375
Fill rate: 0.6494845360824743

MULTIPLYING BY 3 to the index-th
n=96, m=97
Lastfaktor 0.9896907216494846
Collisions: 35
Avg collisions per name: 0.3645833333333333
Fill rate: 0.6288659793814433

MULTIPLYING BY 2 to the index-th
n=96, m=97
Lastfaktor 0.9896907216494846
Collisions: 42
Avg collisions per name: 0.4375
Fill rate: 0.5567010309278351
     */

    private int stringValue(String string) {
        char[] chars = string.toCharArray();
        int value = 0;
        int multiplier = 1;

        // weighting the characters with 3^i
        // no, actually just their position seems to work marginally better in this case
        for (char c : chars) {
            //value += 3 * multiplier * Character.getNumericValue(c); WHOOPS
            //multiplier *= 3; // no multiplier makes the avg collision rate *slightly* worse,
            // incremented makes it *better* in *this* case
            // and multiplying by 3^i requires casting to long or handling int overflow otherwise
            multiplier++;
            value += multiplier * Character.getNumericValue(c);
        }

        return value;
    }

    private int hash(String string) {
        return stringValue(string) % m; // should be safe enough when modulo is performed before casting to int
    }

    public void add(String string) {
        int hash = hash(string);
        if (array[hash] == null) {
            // no collision
            array[hash] = new LinkedList<>(string);
        } else { // collision!
            System.out.println("COLLISION for " + string + "\t with \t" + array[hash].getHead().getValue() + (array[hash].size() > 1? " AND MORE " : "") + " at " + hash);
            System.out.println();
            collisions++;
            array[hash].insert(string);
        }
        n++;
    }

    public boolean contains(String string) {
        int hash = hash(string);
        if (array[hash] == null) {
            return false;
        } else {
            return array[hash].contains(string);
        }
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (LinkedList<String> list : array) {
            if (list != null) {
                builder.append(list);
            } else {
                builder.append("NO ITEM");
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
}
