import com.toberge.timing.Tester;

import java.io.File;

public class MapTester {

    private static final int REYKJAVÍK = 30236;
//    private static final int SKÓGAR = 36808;
    private static final int SKÓGAR = 25378; // Skógar museum...
    private static final int AKUREYRI = 8136;

    private static void doShit(MapGraph graph, int from, int to) {
        System.out.println("------ A* ------");
        Tester.test(() -> graph.aSTAR(from, to));
        System.out.println(graph.searchToString(from, to));

        try {
            graph.writePathToFile(new File("13-kartsok/data/path-" + from + "-" + to + ".csv"));
        } catch (Exception e ) {
            System.err.println("YEah, it go wronk");
        }

        System.out.println("------ Dijkstra ------");
        Tester.test(() -> graph.dijkstra(from, to));
        System.out.println(graph.searchToString(from, to));
    }

    public static void main(String[] args) {
//        MapGraph graph = new MapGraph(new File("13-kartsok/data/island/noder.txt"), new File("13-kartsok/data/island/kanter.txt"));
        MapGraph graph = new MapGraph(new File("13-kartsok/data/norden/noder.txt"), new File("13-kartsok/data/norden/kanter.txt"));
        System.out.println(graph.N + " nodes and " + graph.E + " edges read.");

        Tester.setVerbose(true);
//        doShit(graph, REYKJAVÍK, AKUREYRI);

        System.out.println("Should be 0:40:41");
        doShit(graph, 5709083, 5108028);
        doShit(graph, 5108028,  5709083);
        System.out.println();

        System.out.println("test");
        doShit(graph, 1892102, 542396);
        doShit(graph, 30236, 14416);
        System.out.println();

        System.out.println("trondheim - oslo");
        doShit(graph, 2460904, 2419175);
        System.out.println();

//        System.out.println("---- THESE DON'T WORK ----");
//        System.out.println("bergen - stockholm");
//        doShit(graph, 4687831, 2847023);
//        System.out.println();
//
//        System.out.println("stavanger - trondheim");
//        doShit(graph, 1907153, 2460904);
//        System.out.println();
//
//        System.out.println("bergen - oslo");
//        doShit(graph, 4687831, 2419175);
//        System.out.println();
    }
}
