import com.toberge.data.DirectedNode;

/**
 * Solving Josephus' Problem using a
 *      singly linked circular list
 *      that does not keep track of
 *      the number of items it contains.
 *
 * Here, the complexity becomes O(n^2)
 * since the size() method is O(n) and
 * runs at every iteration of the loop
 * and the for loop does not exceed THINGS
 */
public class WorseCircularList {

    private DirectedNode<Integer> head;

    public WorseCircularList(int n) {
        this.head = new DirectedNode<>(1);
        DirectedNode<Integer> current = this.head;

        for (int i = 2; i <= n; i++) {
            current.setNext(new DirectedNode<>(i));
            current = current.getNext();
        }
        current.setNext(this.head);

    }

    public boolean hasNext() {
        return head.getNext() != null;
    }

    public DirectedNode<Integer> getHead() {
        return head;
    }

    public void next() {
        head = head.getNext();
    }

    public void remove() {
        DirectedNode<Integer> current = head;
        while (current.getNext() != head) {
            current = current.getNext();
        }
        current.setNext(head.getNext());
        head = head.getNext();
    }

    public int size() {
        DirectedNode<Integer> current = head;
        int count = 1;
        while (current.getNext() != null && current.getNext() != head) {
            current = current.getNext();
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        WorseCircularList circle = new WorseCircularList(10);
        int pos = 1;
        for (int i = 1; circle.size() > 1; i++) {
            if (i % 4 == 0) {
                System.out.println("KILLING " + circle.getHead().getValue());
                circle.remove();
            } else {
                pos = circle.getHead().getValue();
                System.out.println(pos);
                circle.next();
            }
        }
        System.out.println("Safe position: " + pos);
    }
}