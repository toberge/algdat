import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class HuffmanToolkit {

    private static final short VALUE_RANGE = 256;
    private static final short JUMP_LENGTH = 32;
    private static final byte VERSION_BYTE = 22;

    private static int[] generateFrequencyTable(DataInputStream stream) throws IOException {
        int[] frequencyTable = new int[VALUE_RANGE];
        for (int i = 0; i < VALUE_RANGE; i++) {
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

    private static byte[] decodeToBytes(HuffmanTree tree, byte[] input) {
        ArrayList<Byte> bytes = new ArrayList<>();
        HuffmanNode currentNode = tree.getRoot();

        out:
        for (byte b : input) {
            for (int i = 0; i < 8; i++) { // for each bit
                if ((b & (1 << (8-i-1))) != 0) { // is a 1
                    currentNode = currentNode.rightChild; // go right in tree
                } else { // is a 0
                    currentNode = currentNode.leftChild; // go left
                }

                if (currentNode.leaf) { // found value
                    int value = currentNode.value;
                    if (value == HuffmanTree.END_OF_BLOCK_VALUE) {
                        break out; // this is the last byte
                    }
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
        BitString[] bsMap = new BitString[VALUE_RANGE];
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
        // end of "file"
        list.add(tree.encode(HuffmanTree.END_OF_BLOCK_VALUE));

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

        return result;
    }

    public static boolean compress(File inFile, File outFile) {

        // generate freq table
        int[] frequencyTable;
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(inFile));
             DataInputStream stream = new DataInputStream(bufferedInputStream)) {
            frequencyTable = generateFrequencyTable(stream);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // generate Huffman tree
        HuffmanTree tree = new HuffmanTree(frequencyTable);

        // get compressed values
        LinkedList<BitString> huffmanCodes;
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(inFile));
             DataInputStream stream = new DataInputStream(bufferedInputStream)) {
            huffmanCodes = encodeToBitStrings(stream, tree);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // convert to array
        byte[] compressedBytes = bitStringsToBytes(huffmanCodes);

        // TODO byte with num bits not written, then int with num values and the frequency table, write that before compressed data.

        // use tree to write compressed file
        try (DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outFile)))) {
            // write version byte
            outputStream.writeByte(VERSION_BYTE);
            // write freq table
            for (int e : frequencyTable) {
                outputStream.writeInt(e);
            }
            outputStream.write(compressedBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean decompress(File inFile, File outFile) {

        int[] frequencyTable = new int[VALUE_RANGE];
        byte[] input;
        try (DataInputStream stream = new DataInputStream(new BufferedInputStream(new FileInputStream(inFile)))) {
            if (stream.readByte() != VERSION_BYTE) {
                System.err.println("Invalid version byte");
                return false;
            }
            for (int i = 0; i < VALUE_RANGE; i++) {
                frequencyTable[i] = stream.readInt();
            }
            // the rest is compressed data
            input = new byte[stream.available()];
            stream.readFully(input);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        HuffmanTree tree = new HuffmanTree(frequencyTable);
        byte[] toWrite = decodeToBytes(tree, input);

        try (DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outFile)))) {
            outputStream.write(toWrite);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // test method
    public static void main(String[] args) {
        // generate freq table
        int[] frequencyTable = null;
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File("12-kompresjon/sample")));
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
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File("12-kompresjon/sample")));
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
            stream.readFully(toWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }

        toWrite = decodeToBytes(tree, toWrite);
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
