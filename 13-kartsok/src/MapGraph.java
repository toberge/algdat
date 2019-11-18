import com.toberge.util.StringStuff;

import java.io.*;
import java.util.PriorityQueue;

public class MapGraph {

    private final MapNode[] nodes;
    final int N, E;

    private int nodesInspected = 0;
    private MapNode start;
    private MapNode destination;

    public MapGraph(File nodeFile, File edgeFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nodeFile))) {
            this.nodes = this.readNodes(reader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Could not read node file");
        }

        this.N = nodes.length;

        try (BufferedReader reader = new BufferedReader(new FileReader(edgeFile))) {
            this.E = this.readEdges(reader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Could not read edge file");
        }
    }

    private MapNode[] readNodes(BufferedReader reader) throws IOException {
        StringStuff.split(reader.readLine(), 1);
        MapNode[] nodes = new MapNode[Integer.parseInt(StringStuff.fields[0])];

        for (int i = 0; i < nodes.length; i++) {
            StringStuff.split(reader.readLine(), 3);
            nodes[i] = new MapNode(Double.parseDouble(StringStuff.fields[1]), Double.parseDouble(StringStuff.fields[2]));
        }

        return nodes;
    }

    private int readEdges(BufferedReader reader) throws IOException {
        StringStuff.split(reader.readLine(), 1);
        int edgeCount = Integer.parseInt(StringStuff.fields[0]);

        for (int i = 0; i < edgeCount; i++) {
            StringStuff.split(reader.readLine(), 5);
            MapNode fromNode = this.nodes[Integer.parseInt(StringStuff.fields[0])];
            MapEdge edge = new MapEdge(
                    Integer.parseInt(StringStuff.fields[2]),
                    Integer.parseInt(StringStuff.fields[3]),
                    Short.parseShort(StringStuff.fields[4]),
                    nodes[Integer.parseInt(StringStuff.fields[1])]
            );

            if (fromNode.firstEdge == null) {
                fromNode.firstEdge = edge;
            } else {
                edge.nextEdge = fromNode.firstEdge;
                fromNode.firstEdge = edge;
            }
        }

        return edgeCount;
    }

    public MapNode[] getNodes() {
        return nodes;
    }

    void initializeStarData(MapNode goal) {
        nodesInspected = 0;
        for (MapNode node : nodes) {
            node.distance = MapNode.INFINITY;
            node.estimate = node.estimateDistanceTo(goal);
            node.found = false;
        }
    }

    void initializeDijkstraData() {
        nodesInspected = 0;
        for (MapNode node : nodes) {
            node.distance = MapNode.INFINITY;
            node.estimate = 0;
            node.found = false;
        }
    }

    public void aSTAR(int from, int to) {
        initializeStarData(nodes[to]);
        performSearch(from, to);
    }

    public void dijkstra(int from, int to) {
        initializeDijkstraData();
        performSearch(from, to);
    }

    void performSearch(int from, int to) {
        start = nodes[from];
        destination = nodes[to];
        PriorityQueue<MapNode> heap = new PriorityQueue<>();
        start.distance = 0;
        heap.add(start);

        while (!heap.isEmpty()) { // just to have a condition here
            MapNode node = heap.poll();
            nodesInspected++; // thus including taking the goal node out
            node.found = true; // MARK AS FOUND
            if (node == destination) break; // geddout when done

            // loop through edges to get neighbours
            for (MapEdge edge = node.firstEdge; edge != null; edge = edge.nextEdge) {
                MapNode neighbour = edge.to;
                // check what happens if this neighbour's path passes through current node
                if (neighbour.distance > node.distance + edge.driveTime) {
                    // if it's a better way, take it!
                    neighbour.distance = node.distance + edge.driveTime;
                    neighbour.parent = node;
                }
                // now has a proper distance + estimate, will be properly placed in heap
                if (neighbour.found) continue; // already processed TODO before or after if
                heap.remove(neighbour); // TODO is this enoguh?
                heap.add(neighbour);
            }
        }
    }

    public String searchToString(int from, int to) {
        int time = nodes[to].distance;
        int hours = time / (3600*100);
        int minutes = (time / (60*100)) % 60;
        int seconds = (time / 100) % 60;
        String res = "Nodes inspected: " + nodesInspected + System.lineSeparator();
        res += "Time in centiseconds: " + time + System.lineSeparator();
        res += "Time: " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds";

        return res;
    }

    public void writePathToFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (MapNode current = destination; current != start; current = current.parent) {
                writer.write(Double.toString(current.latitude));
                writer.write(",");
                writer.write(Double.toString(current.longtitude));
                writer.newLine();
                // TODo idk if anything'll be at the end
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeSearchAreaToFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (MapNode current : nodes) {
                if (current.found) {
                    writer.write(Double.toString(current.latitude));
                    writer.write(",");
                    writer.write(Double.toString(current.longtitude));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
