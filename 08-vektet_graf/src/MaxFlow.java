import com.toberge.data.flowgraph.FlowGraph;

import java.io.*;

public class MaxFlow {

    public static void main(String[] args) {
        final String syntax = "args should be: source sink file";
        if (args.length != 3) {
            System.err.println(syntax);
            System.exit(1);
        }
        int source = -1, sink = -1;
        File file = new File(args[2].trim());
        try {
            source = Integer.parseInt(args[0]);
            sink = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("source and/or sink are invalid as numbers");
            System.err.println(syntax);
            System.exit(1);
        }

        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {
            FlowGraph graph = new FlowGraph(br);
            System.out.print(graph.edmondsKarp(source, sink));
        } catch (FileNotFoundException e) {
            System.err.println("No such file: " + args[2]);
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
