import java.util.ArrayList;

public class HuffmanTree {

    public static final int END_OF_BLOCK_VALUE = Integer.MAX_VALUE;
    static final HuffmanNode END_OF_BLOCK_NODE = new HuffmanNode(1, END_OF_BLOCK_VALUE);

    private final HuffmanNode root;
    private final HuffmanNode[] valueToNode; // index is unsigned value

    public HuffmanTree(int[] frequencyTable) {
        ArrayList<HuffmanNode> nodes = new ArrayList<>(frequencyTable.length);
        valueToNode = new HuffmanNode[frequencyTable.length];
        for (int i = 0; i < frequencyTable.length; i++) {
            if (frequencyTable[i] > 0) {
                valueToNode[i] = new HuffmanNode(frequencyTable[i], i);
                nodes.add(valueToNode[i]);
            } else {
                valueToNode[i] = null;
            }
        }
        // special node for EOF handling
        nodes.add(END_OF_BLOCK_NODE);

        while (nodes.size() > 1) {
            // sort
            nodes.sort(HuffmanNode::compareTo);
            int last = nodes.size() - 1;

            // make new node
            HuffmanNode lastNode = nodes.get(last);
            HuffmanNode secondLastNode = nodes.get(last - 1);
            HuffmanNode assembled = new HuffmanNode(lastNode.frequency + secondLastNode.frequency);

            // stick together with children
            lastNode.parent = assembled;
            secondLastNode.parent = assembled;
            if (lastNode.frequency < secondLastNode.frequency) {
                // last is least
                assembled.leftChild = lastNode;
                assembled.rightChild = secondLastNode;
            } else {
                // last is not least (maybe equal)
                assembled.leftChild = secondLastNode;
                assembled.rightChild = lastNode;
            }

            // replace on list
            nodes.add(assembled);
            nodes.remove(last);
            nodes.remove(last - 1);
        }

        root = nodes.get(0);
    }

    public HuffmanNode getRoot() {
        return root;
    }

    public HuffmanNode[] getValueToNode() {
        return valueToNode;
    }

    public BitString encode(int value) {
        HuffmanNode node;
        if (value == END_OF_BLOCK_VALUE) {
            node = END_OF_BLOCK_NODE; // special handling for end value
        } else if (value > valueToNode.length || valueToNode[value] == null) {
            return null;
        } else {
            node = valueToNode[value]; // created in constructor
        }

        long code = 0;
        byte bitCount = 0;
        while (node.parent != null) { // start walking tree
            if (node.parent.leftChild == node) {
                // we're a 0
                // leaving a 0, no need to do more
            } else {
                // we're a 1
                code |= (1 << bitCount);
            }
            bitCount++;
            node = node.parent;
        }
        return new BitString(code, bitCount);
    }
}

class HuffmanNode implements Comparable<HuffmanNode> {
    HuffmanNode leftChild = null; // 0
    HuffmanNode rightChild = null; // 1
    HuffmanNode parent = null;

    int frequency;
//    BitString value = null;
    boolean leaf;
    int value;

    public HuffmanNode(int frequency, int value) {
        this.frequency = frequency;
        this.value = value;
        leaf = true;
    }

    public HuffmanNode(int frequency) {
        this.frequency = frequency;
        this.leaf = false;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        if (o.frequency < this.frequency) {
            return -1;
        } else if (o.frequency > this.frequency) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder top = new StringBuilder();
//        StringBuilder bottom = new StringBuilder();

        if (leftChild != null) {
            top.append(leftChild.toString());
            top.append(" ");
        }
        top.append(frequency);
        if (leaf) top.append("{" + ((char) value) + "}");
        if (rightChild != null) {
            top.append(" ");
            top.append(rightChild.toString());
        }


        return top.toString();
    }
}
