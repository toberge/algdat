import com.toberge.data.graph.BFSNode;
import com.toberge.data.graph.BFSTree;
import com.toberge.timing.Tester;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Runner {

    private static BFSTree bfsTree;
    private static final int KALVSKINNET = 37774;
    private static final int MOHOLT = 18058;

    private static final int DRAMMEN = 65205;
    private static final int HELSINKI = 3378527;

    private static int distanceBetween(int start, int end) {
        bfsTree = null;
        Tester.test(() ->
            bfsTree = new BFSTree(new File("07-uvektet_graf/L7Skandinavia"), start)
        );
        return ((BFSNode)bfsTree.getGraph().getNodes().get(end)).getDistance();
    }

    public static void main(String[] args) {
        // load graphs
        // run BFS (on copy)
        // run topo sort (on copy)

        bfsTree = new BFSTree(new File("07-uvektet_graf/L7g1"), 5);
        System.out.println(bfsTree);


        System.out.println("Veikryss mellom Drammen og helsinki (distance - 1): " +
                (distanceBetween(DRAMMEN, HELSINKI) - 1));

        System.out.println("Veikryss mellom Helsinki og Drammen (distance - 1): " +
                (distanceBetween(HELSINKI, DRAMMEN) - 1));

        // Kalvskinnet @ 37774 --> Moholt @ 18058
        // 1979 ms, 2 secs:
        // 2154 ms, still 2 secs:
        System.out.println("Veikryss mellom Kalvskinnet og Moholt (distance - 1): " +
                (distanceBetween(KALVSKINNET, MOHOLT) - 1));


        /*Tester.test(() -> {
            try {
                Files.write(Paths.get(System.getProperty("user.home") + "/.thingything"), bfsTree.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/


    }
}
