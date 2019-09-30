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

    public static void main(String[] args) {
        // load graphs
        // run BFS (on copy)
        // run topo sort (on copy)

        bfsTree = new BFSTree(new File("07-uvektet_graf/L7g1"), 5);
        System.out.println(bfsTree);

        // Kalvskinnet @ 37774 --> Moholt @ 18058
        // 1979 ms, 2 secs:
        Tester.test(() -> {
            bfsTree = new BFSTree(new File("07-uvektet_graf/L7Skandinavia"), KALVSKINNET);
        });
        // 2154 ms, still 2 secs:
        System.out.println("Veikryss mellom Kalvskinnet og Moholt: " + ((BFSNode)bfsTree.getGraph().getNodes().get(MOHOLT)).getDistance());
        /*Tester.test(() -> {
            try {
                Files.write(Paths.get(System.getProperty("user.home") + "/.thingything"), bfsTree.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/


    }
}
