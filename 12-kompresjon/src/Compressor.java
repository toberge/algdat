import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Compressor {

    private static final short BLOCK_SIZE = 256;
    private static final short JUMP_LENGTH = 32;

    private static int[] generateFrequencyTable(DataInputStream stream) throws IOException {
        int[] frequencyTable = new int[BLOCK_SIZE];
        for (int i = 0; i < BLOCK_SIZE; i++) {
            frequencyTable[i] = 0;
        }
        byte[] array = new byte[stream.available()];
        int offset = 0;
        while (stream.available() > 0) {
            int avail = stream.available();
            int length = stream.read(array, offset, (JUMP_LENGTH <= avail? JUMP_LENGTH : avail));
            for (int value = offset; value < offset + length; value++) {
                frequencyTable[Byte.toUnsignedInt(array[value])]++;
            }
            offset = offset + length;
        }
        return frequencyTable;
    }

    // eh.
    private static byte toSigned(int unsigned) {
        if ((unsigned & 0b10000000) > 0) {
            unsigned = ~unsigned;
            unsigned++;
            // actually necessary for converting back.
            unsigned -= 2*unsigned;
            return (byte) unsigned;
        } else {
            // positive
            return (byte) unsigned;
        }
    }

    private static byte[] decodeToBytes(HuffmanTree tree, DataInputStream stream) throws IOException {
        ArrayList<Byte> bytes = new ArrayList<>();

        byte[] input = new byte[stream.available()];
        stream.read(input);

        HuffmanNode currentNode = tree.getRoot();

        for (byte b : input) {
            for (int i = 0; i < 8; i++) { // for each bit
                if ((b & (1 << (8-i-1))) != 0) { // is a 1
                    currentNode = currentNode.rightChild; // go right in tree
                } else { // is a 0
                    currentNode = currentNode.leftChild; // go left
                }

                if (currentNode.leaf) { // found value
                    int value = currentNode.value;
                    bytes.add((byte) value); // then we shall write that value
                    currentNode = tree.getRoot(); // and reset
                }
            }
        }

        byte[] result = new byte[bytes.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = bytes.get(i);
        }

        return result;
    }

    // using tree to get zem codes
    private static LinkedList<BitString> encodeToBitStrings(DataInputStream stream, HuffmanTree tree) throws IOException {
        BitString[] bsMap = new BitString[BLOCK_SIZE];
        LinkedList<BitString> list = new LinkedList<>();

        byte[] bytes = new byte[stream.available()];
        stream.read(bytes);

        for (byte b : bytes) {
            // nonneg value
            int value = Byte.toUnsignedInt(b);
            if (bsMap[value] == null) { // no cached value
                bsMap[value] = tree.encode(value); // use tree to calc value
            }
            list.add(bsMap[value]);
        }

        return list;
    }

    private static byte[] bitStringsToBytes(LinkedList<BitString> list) {
        ArrayList<Byte> bytes = new ArrayList<>();

        byte progress = 0;
        while (list.size() > 0) {
            byte b = 0; // let's get ourselves a byte
            // all 8 bits of the byte
            for (byte i = 0; i < 8; i++) { // dumb me did i < 7 for a while
                BitString bitString = list.getFirst(); // get bitstring
                /* Example:
                000000...101101
                         ^
                get it by & (1 << length - index)
                 */
                if ((list.getFirst().bits & (1 << (bitString.length - progress - 1))) != 0) {
                    // is a 1
                    b |= (1 << (8-i-1));
                } // else 0, no need to do anything
                progress++; // move rightwards in bits
                if (progress >= bitString.length) { // if empty
                    list.removeFirst(); // remove 1st so we get 2nd next time
                    if (list.size() == 0) break; // leave for loop if list is empty
                    progress = 0; // reset progress
                }
            }
            bytes.add(b);
        }

        byte[] result = new byte[bytes.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = bytes.get(i);
        }

        System.out.println(bytes.size());

        return result;
    }

    public static void main(String[] args) {
        // generate freq table
        int[] frequencyTable = null;
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File("12-kompresjon/sample.txt")));
             DataInputStream stream = new DataInputStream(bufferedInputStream)) {
            frequencyTable = generateFrequencyTable(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < frequencyTable.length; i++) {
            if (frequencyTable[i] > 0)
                System.out.println(((char) toSigned(i)) + "|" + toSigned(i) + "|" + i + ": " + frequencyTable[i]);
        }


        // generate Huffman tree
        HuffmanTree tree = new HuffmanTree(frequencyTable);


        // TODO move dis part 2 dat decompressor u know & love

        // get compressed values
        LinkedList<BitString> list = null;
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File("12-kompresjon/sample.txt")));
             DataInputStream stream = new DataInputStream(bufferedInputStream)) {
            list = encodeToBitStrings(stream, tree);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // convert to array
        byte[] toWrite = bitStringsToBytes(list);

        // use tree to write compressed file
        try (DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("12-kompresjon/sample.huff"))))) {
            outputStream.write(toWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // decompression test - TODO fix & move
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File("12-kompresjon/sample.huff")));
             DataInputStream stream = new DataInputStream(bufferedInputStream)) {
            toWrite = decodeToBytes(tree, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (byte b : toWrite) {
            System.out.print(new BitString(Byte.toUnsignedInt(b), (byte) 8) + " ");
        }
        System.out.println();

        try (DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("12-kompresjon/sample.huff.unhuff"))))) {
            outputStream.write(toWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
