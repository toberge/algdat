import com.toberge.timing.Tester;

import java.io.File;

public class MapSearch {
    public static void main(String[] args) {
        if (args.length != 4 ) {
            System.out.println("Syntax: nodeFile edgeFile fromNode toNode");
            System.exit(1);
        }
        File nodeFile, edgeFile;
        nodeFile = new File(args[0]);
        if (!nodeFile.exists()) {
            System.err.println("No such file: " + nodeFile.toString());
            System.exit(1);
        }
        edgeFile = new File(args[1]);
        if (!edgeFile.exists()) {
            System.err.println("No such file: " + nodeFile.toString());
            System.exit(1);
        }
        int from, to;
        from = Integer.parseInt(args[2]);
        to = Integer.parseInt(args[3]);


        MapGraph graph = new MapGraph(nodeFile, edgeFile);
        Tester.setVerbose(true);

        System.out.println("------ A* ------");
        Tester.test(() -> graph.aSTAR(from, to));
        System.out.println(graph.searchToString(from, to));

        System.out.println("------ Dijkstra ------");
        Tester.test(() -> graph.dijkstra(from, to));
        System.out.println(graph.searchToString(from, to));
    }
}
