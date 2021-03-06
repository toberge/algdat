import java.io.File;

public class Decompressor {

    public static void main(String[] args) {
        // read freq table
        // generate Huffman tree
        // interpret values using tree, write out
        if (args.length != 2) System.exit(1);
        File inFile, outFile;
        inFile = new File(args[0]);
        if (!inFile.exists()) {
            System.err.println("No such file: " + inFile.toString());
            System.exit(1);
        }
        outFile = new File(args[1]);
        if (outFile.exists()) {
            System.err.println("Warning: Overwriting " + outFile.toString());
        }
        if (HuffmanToolkit.decompress(inFile, outFile)) {
            System.out.println("Decompressed " + inFile.toString() + " to " + outFile.toString());
        } else {
            System.err.println("Decompression failed!");
            System.exit(1);
        }
    }
}
