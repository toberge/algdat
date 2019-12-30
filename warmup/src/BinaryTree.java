/**
 * Basic binary tree, mostly taken from the book.
 *
 * Invariant: value in left child is >= this node's value,
 *            value in right child is < this node's value.
 *
 * No exceptions. Makes searching easy but hampers modification.
 *
 * Tried making a version as a map with generic types,
 * ran into trouble when I realized it required compareTo()
 * and therefore a key that <i>must</i> implement Comparable.
 */
public class BinaryTree {

    private Limb root = null;

    public boolean contains(int value) {
        if (root == null) {
            return false; // nothing to contain it in
        } else {
            return root.contains(value);
        }
    }

    public void insert(int value) {
       if (root == null) {
           root = new Limb(value);
       } else {
           root.insert(value);
       }
    }

    @Override
    public String toString() {
        return root.toString();
    }

    private class Limb {

        private int value;

        private Limb parent;
        private Limb leftChild;
        private Limb rightChild;

        public Limb(int value) {
            this.value = value;
        }

        public Limb(int value, Limb parent) {
            this.value = value;
            this.parent = parent;
        }

        public int getValue() {
            return value;
        }

        public boolean contains(int value) {
            if (value == getValue()) {
                return true;
            } else if (value >= getValue()) {
                if (rightChild == null) {
                    return false; // having searched to this point we must conclude there is none
                } else {
                    return rightChild.contains(value);
                }
            } else {
                if (leftChild == null) {
                    return false;
                } else {
                    return leftChild.contains(value);
                }
            }
        }

        public void insert(int value) {
            if (value >= getValue()) {
                if (rightChild == null) {
                    rightChild = new Limb(value, this);
                } else {
                    rightChild.insert(value);
                }
            } else {
                if (leftChild == null) {
                    leftChild = new Limb(value, this);
                } else {
                    leftChild.insert(value);
                }
            }
        }

        /**
         * Called from remove() on the limb where the value is found
         * --> we need to search down the tree 'till we find a node with 0-1 children...
         * @return  retrofitted version of 'this' with whatever must be the top of the tree.
         */
        private Limb remake() {
            if (rightChild != null) {

            }
            return null; // for now
        }

        public void remove(int value) {
            // value != this.value MUST be the case
            // otherwise there'd be no way to handle that from here
            if (value > getValue()) {
                if (rightChild != null) { // nothing happens if it is NULL
                    if (rightChild.getValue() == value) {
                        rightChild = rightChild.remake();
                    } else {
                        rightChild.remove(value);
                    }
                }
            } else {
                if (leftChild != null) {
                    if (leftChild.getValue() == value) {
                        leftChild = leftChild.remake();
                    } else {
                        leftChild.remove(value);
                    }
                }
            }
        }

        @Override
        public String toString() { // not using StringBuilder since that's be a *bad* idea with recursion (one SB for each node)

            String res = "";

            if (leftChild != null) {
                res = leftChild.toString();
            }

            res += getValue() + " ";

            if (rightChild != null) {
                res += rightChild.toString();
            }

            return res;
        }
    }
}

class BTreeTester {

    private static BinaryTree tree = new BinaryTree();

    private static void printExistance(int value) {
        System.out.println("Le value " + value + " exists? " + (tree.contains(value)? "Yeees" : "Nay"));
    }

    public static void main(String[] args) {

        tree.insert(3);
        tree.insert(4);
        tree.insert(-23);
        tree.insert(40);
        tree.insert(14);

        System.out.println(tree);

        printExistance(3);
        printExistance(-23);
        printExistance(1);

    }
}

/*
public class BinaryTree<K, T> implements Map<K, T> {

    private Limb root = null;



    private class Limb {

        private Comparable<K> key;
        private T value;

        private Limb parent;
        private Limb leftChild;
        private Limb rightChild;

        public Limb(Comparable<K> key, T value) {
            this.key = key;
            this.value = value;
        }

        public Limb(Comparable<K> key, T value, Limb parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public Comparable<K> getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }

        public boolean containsKey(Comparable<K> key) {
            if (this.key.equals(key)) {
                return true;
            } else if (key.compareTo(this.key))

        }
    }
}
*/
