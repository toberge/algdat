import java.io.File;

public class Compressor {
    public static void main(String[] args) {
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
        if (HuffmanToolkit.compress(inFile, outFile)) {
            System.out.println("Compressed " + inFile.toString() + " to " + outFile.toString());
        } else {
            System.err.println("Compression failed!");
            System.exit(1);
        }
    }
}
