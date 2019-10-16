import com.toberge.data.flowgraph.FlowGraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EdmondRunner {

    private static void findMaxFlow(int i) {
        try (FileReader fr = new FileReader(new File("08-vektet_graf/flytgraf" + i));
             BufferedReader br = new BufferedReader(fr)) {
            FlowGraph graph = new FlowGraph(br);
            System.out.println(graph.edmondsKarp(0, graph.getN() - 1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        System.out.println("Vewy gwaph");

        int graphs = 3;
        for (int i = 1; i <= graphs; i++) {
            findMaxFlow(i);
        }


    }
}
