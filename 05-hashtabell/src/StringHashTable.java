import com.toberge.data.DirectedNode;
import com.toberge.data.LinkedList;
import com.toberge.util.ExtraMath;

public class StringHashTable {

    private final LinkedList<String>[] array;
    private final int m;
    private int n = 0;
    private int collisions = 0;

    public StringHashTable(int capacity) {
//        BigInteger big = new BigInteger(String.valueOf(capacity /*+ capacity / 6*/)); // 1/6 more, but I should refrain according to oppgavetekst
//        m = Integer.parseInt(big.nextProbablePrime().toString()); // next prime
        m = ExtraMath.nextPrime(capacity);
        array = new LinkedList[m];
    }

    private int stringValue(String string) {
        char[] chars = string.toCharArray();
        int value = 0;
        int multiplier = 1;

        // weighting the characters with 3^i
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
                /*DirectedNode<String> current = list.getHead();
                builder.append(current.getValue());
                while (current.getNext() != null) {
                    builder.append(System.lineSeparator());
                    builder.append(current.getValue());
                    current = current.getNext();
                }*/
                builder.append(list);
            } else {
                builder.append("NO ITEM");
            }
            builder.append(System.lineSeparator());
//            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
}
