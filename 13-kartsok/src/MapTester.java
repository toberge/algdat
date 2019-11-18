import com.toberge.timing.Tester;

import java.io.File;

public class MapTester {

    private static final int REYKJAVÍK = 30236;
//    private static final int SKÓGAR = 36808;
    private static final int SKÓGAR = 25378; // Skógar museum...
    private static final int AKUREYRI = 8136;

    public static void main(String[] args) {
        MapGraph graph = new MapGraph(new File("13-kartsok/data/island/noder.txt"), new File("13-kartsok/data/island/kanter.txt"));
        System.out.println(graph.N + " nodes and " + graph.E + " edges read.");

        Tester.setVerbose(true);
        int from = REYKJAVÍK, to = AKUREYRI;

        System.out.println("------ A* ------");
        Tester.test(() -> graph.aSTAR(from, to));
        System.out.println(graph.searchToString(from, to));

        graph.writePathToFile(new File("13-kartsok/data/island/path.txt"));
        graph.writeSearchAreaToFile(new File("13-kartsok/data/island/area.txt"));

        System.out.println("------ Dijkstra ------");
        Tester.test(() -> graph.dijkstra(from, to));
        System.out.println(graph.searchToString(from, to));
    }
}
