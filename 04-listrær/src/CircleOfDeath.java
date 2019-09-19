import com.toberge.data.DoublyDirectedNode;

/**
 * Solving Josephus' Problem using a
 *      doubly linked circular list.
 *
 *
 */
public class CircleOfDeath {

    private DoublyDirectedNode<Integer> head;
    private int count;

    public CircleOfDeath(int n) {
        count = n;
        this.head = new DoublyDirectedNode<>(1);
        DoublyDirectedNode<Integer> current = this.head;

        for (int i = 2; i <= n; i++) {
            current.setNext(new DoublyDirectedNode<>(i));
            current.getNext().setPrevious(current);
            current = current.getNext();
        }
        this.head.setPrevious(current);
        current.setNext(this.head);

    }

    public DoublyDirectedNode<Integer> getHead() {
        return head;
    }

    public void next() {
        head = head.getNext();
    }

    public void remove() {
        head.getNext().setPrevious(head.getPrevious());
        head.getPrevious().setNext(head.getNext());
        head = head.getNext();
        count--;
    }

    public int size() {
        return count;
    }

    /**
     * n people in circle, every k people killed
     * (ignoring printlines)
     *
     * Complexity should be &Theta;(nk)
     * since the process must go k more times for each additional person
     */
    public static int solveJosephus(int n, int k) {
        CircleOfDeath circle = new CircleOfDeath(n); // O(n) naturally
        while (circle.size() > 1) {
            for (int i = 0; i < k - 1; i++) { // k-1 since .remove() removes head aka goes to next
                circle.next();
            }
            circle.remove();
        }
        return circle.getHead().getValue();
    }

    public static int solveJosephusFor(int n, int k) {
        CircleOfDeath circle = new CircleOfDeath(n); // O(n) naturally
        int pos = 1;
        for (int i = 1; circle.size() > 1; i++) { // until one is left, size is O(1) actually
            if (i % k == 0) {
                System.out.print("-|" + circle.getHead().getValue() + "|- ");
                circle.remove(); // O(1), kill if at interval
            } else {
                pos = circle.getHead().getValue();
                System.out.print(pos + " ");
                circle.next(); // O(1), skip if not at interval
            }
        }
        return pos;
    }


    public static void main(String[] args) {
        System.out.println("FOR: (original)");
        System.out.println("\nSafe position: " + solveJosephusFor(10, 4));
        System.out.println("\nSafe position: " + solveJosephusFor(40, 4));
        System.out.println("\nSafe position: " + solveJosephusFor(41, 3));
        System.out.println("WHILE:");
        System.out.println("\nSafe position: " + solveJosephus(10, 4));
        System.out.println("\nSafe position: " + solveJosephus(40, 4));
        System.out.println("\nSafe position: " + solveJosephus(41, 3));
    }
}
