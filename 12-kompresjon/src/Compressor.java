import sun.awt.image.ImageWatched;

import java.io.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

public class Compressor {

    public static final short BLOCK_SIZE = 256;
    public static final short JUMP_LENGTH = 32;

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
                System.out.println(((char) array[value]) + "|" + array[value] + " to " + Byte.toUnsignedInt(array[value]));
                frequencyTable[Byte.toUnsignedInt(array[value])]++;
            }
            System.out.println(offset + " " + length);
            offset = offset + length;
        }
        return frequencyTable;
    }

    private static byte toSigned(int unsigned) {
        if ((unsigned & 0b10000000) > 0) {
            System.out.println(Integer.toBinaryString(unsigned) + " " + (byte)unsigned);
            unsigned = ~unsigned;
//            unsigned += 0b00000001;
            unsigned++;
            // actually necessary for converting back.
            unsigned -= 2*unsigned;
//            System.out.println(Integer.toBinaryString(unsigned).substring(24, 32) + " " + (byte)unsigned);
            return (byte) unsigned;
        } else {
            // positive
            return (byte) unsigned;
        }
    }

    // this thing should work. only problem is TODO this is the decoding!!!!!!!!!!!!!!!!!!!!!!!! except it's not... quite... right...
    private static LinkedList<BitString> huffMyPuff(HuffmanTree tree, DataInputStream stream) throws IOException {
        LinkedList<BitString> list = new LinkedList<>();
        BitString[] valueToBitString = new BitString[BLOCK_SIZE];

        while (stream.available() > 0) { // useless outer while loop, much sorry
            byte[] bytes = new byte[stream.available()];
            stream.read(bytes);
            HuffmanNode currentNode = tree.getRoot();

            long code = 0;
            byte length = 0;
            for (byte b : bytes) {
                for (int i = 0; i < 8; i++) {
                    b >>= 1; // shift byte one down
                    code <<= 1; // make space for one more bit
                    if ((b & 1) == 1) { // we have a 1
                        currentNode = currentNode.rightChild;
                        code |= 1; // add 1 to code
                    } else { // we have a 0
                        currentNode = currentNode.leftChild;
                        // 0 in code already
                    }
                    length++; // add to length before potential bitstring creation

                    if (currentNode.leaf) { // found value
                        int value = currentNode.value;
//                        System.out.println("code: " + new BitString(code, length) + "length: " + length);
                        // TODO this is the value we should ADDDDDDDDDDDDDD after reading, you absolute FOOOOOOOOL
                        System.out.print((char) value);
                        if (valueToBitString[value] == null) { // if no cached value
                            valueToBitString[value] = new BitString(code, length); // set new bitstring there
                        }
                        list.add(valueToBitString[value]); // add that BitString to list
                        // reset values
                        code = 0;
                        length = 0;
                        currentNode = tree.getRoot();
                    }
                }
            }
        }

        return list;
    }

    private static LinkedList<BitString> encodeToList(DataInputStream stream, HuffmanTree tree) throws IOException {
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

    @Deprecated
    private static byte[] toNoByteArray(List<BitString> list) {
        ArrayList<Byte> bytes = new ArrayList<>();
        byte toFill = 0; // to fill in most recent byte

        for (BitString bitString : list) {
            byte position = 0; // bits from bitstring left from last time
            if (toFill > 0) {
                int index = bytes.size() - 1; // last index
                byte replacing = bitString.appendToByte(bytes.get(index), toFill); // fill those
                bytes.remove(index);
                bytes.add(replacing); // doing the swap
                System.out.println("hie" + toFill);
                if (toFill - bitString.length > 0) { // more to fill?
                    toFill = (byte) (toFill - bitString.length); // then tell us
                    continue; // and MOVE ON because we are done with this bitstring
                } else {
                    // set position in bitstring + indicate no more is to be filled
                    position = (byte) (toFill - 1);
                    toFill = 0;
                }
            }
            // no (more) bit to fill, go on
            while (position < bitString.length) {
                if (position < bitString.length - 8) { // not at the end yet
                    bytes.add(bitString.splitByte(position));
                } else { // whoops, I might be missing a method for that...
//                    System.out.println(new BitString(bitString.splitByte(position) << (position % 8 + 1), (byte) 8));
                    System.out.println(bitString + "at " + position);
                    bytes.add((byte) (bitString.splitByte(position) << (position % 8 + 1))); // holy shit wtf
                    toFill = (byte) (bitString.length - position); // set toFill
                }
                position += 8;
            }
        }

        byte[] result = new byte[bytes.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = bytes.get(i);
        }

        return result;
    }

    private static byte[] toByteArray(LinkedList<BitString> list) {
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

    private static void write(OutputStream stream, byte[] bytes) throws IOException {
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
        System.out.println(tree.getRoot().frequency);
        System.out.println(tree.getRoot());
        System.out.println(tree.encode(32)); // space, 11 of them --> short code
        System.out.println(tree.encode(195));
        System.out.println(tree.encode(101)); // e
        System.out.println(tree.encode(184));
        System.out.println(tree.encode(105));


        // TODO move dis part 2 dat decompressor u know & love

        // get compressed values
        LinkedList<BitString> list = null;
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File("12-kompresjon/sample.txt")));
             DataInputStream stream = new DataInputStream(bufferedInputStream)) {
            list = encodeToList(stream, tree);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Compressed: ");
        for (BitString bs : list) {
            System.out.print(bs + " ");
        }
        System.out.println();

        // convert to array
        byte[] toWrite = toByteArray(list);
        System.out.print("To bytes: ");
        for (byte b : toWrite) {
            System.out.print(new BitString(Byte.toUnsignedInt(b), (byte) 8) + " ");
        }
        System.out.println();

        // r4nd0m debugging

        BitString bs = new BitString(0b101010, (byte)6);
        System.out.println(bs + " and then " + bs.appendToByte((byte)0b11000000, (byte)2) + Integer.toBinaryString(Byte.toUnsignedInt((new BitString(0b101111110101L, (byte)12)).splitByte((byte) 2))));

        // use tree to write compressed file
        try (DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("12-kompresjon/sample.huff"))))) {
            outputStream.write(toWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // decompression test - TODO fix & move
        System.out.println("decompressed shit:");
        LinkedList<BitString> dlist = null;
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File("12-kompresjon/sample.huff")));
             DataInputStream stream = new DataInputStream(bufferedInputStream)) {
            dlist = huffMyPuff(tree, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (BitString bss : dlist) {
            System.out.print(bss + " ");
        }
        System.out.println();

        toWrite = toByteArray(dlist);

        try (DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("12-kompresjon/sample.huff.unhuff"))))) {
            outputStream.write(toWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
